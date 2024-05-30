package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.common.DataMailDto;
import com.tuyenngoc.bookstore.domain.dto.request.*;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.auth.LoginResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.auth.TokenRefreshResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Cart;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.User;
import com.tuyenngoc.bookstore.domain.mapper.UserMapper;
import com.tuyenngoc.bookstore.exception.DataIntegrityViolationException;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.exception.UnauthorizedException;
import com.tuyenngoc.bookstore.repository.CartRepository;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.repository.UserRepository;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.security.jwt.JwtTokenProvider;
import com.tuyenngoc.bookstore.service.AuthService;
import com.tuyenngoc.bookstore.service.RoleService;
import com.tuyenngoc.bookstore.util.RandomPasswordUtil;
import com.tuyenngoc.bookstore.util.SendMailUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtTokenServiceImpl jwtTokenService;

    private final MessageSource messageSource;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final SendMailUtil sendMailUtil;

    private final CustomerRepository customerRepository;

    private final CartRepository cartRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateToken(customUserDetails, Boolean.FALSE);
            String refreshToken = jwtTokenProvider.generateToken(customUserDetails, Boolean.TRUE);

            jwtTokenService.saveAccessTokenToRedis(accessToken, customUserDetails.getUsername());
            jwtTokenService.saveRefreshTokenToRedis(refreshToken, customUserDetails.getUsername());

            return new LoginResponseDto(
                    accessToken,
                    refreshToken,
                    customUserDetails.getAuthorities()
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.ERR_EXCEPTION_GENERAL);
        }
    }

    @Override
    public CommonResponseDto logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        if (authentication != null && authentication.getName() != null) {
            jwtTokenService.deleteTokenFromRedis(authentication.getName());
        }

        SecurityContextLogoutHandler logout = new SecurityContextLogoutHandler();
        logout.logout(request, response, authentication);

        String message = messageSource.getMessage(SuccessMessage.Auth.LOGOUT, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public TokenRefreshResponseDto refresh(TokenRefreshRequestDto request) {
        String refreshToken = request.getRefreshToken();

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.extractClaimUsername(refreshToken);

            if (jwtTokenService.checkRefreshTokenExistenceInRedis(refreshToken, username)) {
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new InvalidException(ErrorMessage.Auth.ERR_INVALID_REFRESH_TOKEN));
                CustomUserDetails userDetails = CustomUserDetails.create(user);

                String newAccessToken = jwtTokenProvider.generateToken(userDetails, Boolean.FALSE);
                String newRefreshToken = jwtTokenProvider.generateToken(userDetails, Boolean.TRUE);

                jwtTokenService.saveAccessTokenToRedis(newAccessToken, user.getUsername());
                jwtTokenService.saveRefreshTokenToRedis(newRefreshToken, user.getUsername());

                return new TokenRefreshResponseDto(newAccessToken, newRefreshToken);
            } else {
                throw new InvalidException(ErrorMessage.Auth.ERR_INVALID_REFRESH_TOKEN);
            }
        } else {
            throw new InvalidException(ErrorMessage.Auth.ERR_INVALID_REFRESH_TOKEN);
        }
    }

    @Override
    public User register(RegisterRequestDto requestDto) {
        if (!requestDto.getPassword().equals(requestDto.getRepeatPassword())) {
            throw new InvalidException(ErrorMessage.INVALID_REPEAT_PASSWORD);
        }
        boolean isUsernameExists = userRepository.existsByUsername(requestDto.getUsername());
        if (isUsernameExists) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_USERNAME);
        }
        boolean isEmailExists = userRepository.existsByEmail(requestDto.getEmail());
        if (isEmailExists) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_EMAIL);
        }

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", requestDto.getUsername());
        properties.put("password", requestDto.getPassword());

        DataMailDto mailDto = new DataMailDto();
        mailDto.setTo(requestDto.getEmail());
        mailDto.setSubject("Đăng ký thành công");
        mailDto.setProperties(properties);

        CompletableFuture.runAsync(() -> {
            try {
                sendMailUtil.sendEmailWithHTML(mailDto, "registerSuccess.html");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        //Create new Customer
        Customer customer = new Customer();
        customer.setFullName(requestDto.getUsername());
        Customer newCustomer = customerRepository.save(customer);
        //Create new Cart
        Cart cart = new Cart();
        cart.setCustomer(newCustomer);
        cartRepository.save(cart);
        //Create new User
        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(roleService.getRole(RoleConstant.CUSTOMER));
        user.setCustomer(newCustomer);
        return userRepository.save(user);
    }

    @Override
    public CommonResponseDto forgetPassword(ForgetPasswordRequestDto requestDto) {
        User user = userRepository.findByUsernameAndEmail(requestDto.getUsername(), requestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ACCOUNT));

        String newPassword = RandomPasswordUtil.random();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", requestDto.getUsername());
        properties.put("newPassword", newPassword);

        DataMailDto mailDto = new DataMailDto();
        mailDto.setTo(requestDto.getEmail());
        mailDto.setSubject("Lấy lại mật khẩu");
        mailDto.setProperties(properties);

        CompletableFuture.runAsync(() -> {
            try {
                sendMailUtil.sendEmailWithHTML(mailDto, "forgetPassword.html");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        String message = messageSource.getMessage(SuccessMessage.User.FORGET_PASSWORD, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto changePassword(ChangePasswordRequestDto requestDto, String username) {
        if (!requestDto.getPassword().equals(requestDto.getRepeatPassword())) {
            throw new InvalidException(ErrorMessage.INVALID_REPEAT_PASSWORD);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME, username));

        boolean isCorrectPassword = passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword());
        if (!isCorrectPassword) {
            throw new InvalidException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);

        Map<String, Object> properties = new HashMap<>();
        properties.put("currentTime", new Date());

        DataMailDto mailDto = new DataMailDto();
        mailDto.setTo(user.getEmail());
        mailDto.setSubject("Đổi mật khẩu thành công");
        mailDto.setProperties(properties);

        CompletableFuture.runAsync(() -> {
            try {
                sendMailUtil.sendEmailWithHTML(mailDto, "changePassword.html");
            } catch (MessagingException e) {
                log.error("Failed to send email {}", e.getMessage(), e);
            }
        });

        String message = messageSource.getMessage(SuccessMessage.User.CHANGE_PASSWORD, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }
}
