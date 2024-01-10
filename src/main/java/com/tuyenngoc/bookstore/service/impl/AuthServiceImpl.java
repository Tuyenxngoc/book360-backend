package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.RoleConstant;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.AddressDto;
import com.tuyenngoc.bookstore.domain.dto.request.LoginRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.RegisterRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.TokenRefreshRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.LoginResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.TokenRefreshResponseDto;
import com.tuyenngoc.bookstore.domain.entity.User;
import com.tuyenngoc.bookstore.domain.mapper.UserMapper;
import com.tuyenngoc.bookstore.exception.DataIntegrityViolationException;
import com.tuyenngoc.bookstore.exception.InvalidException;
import com.tuyenngoc.bookstore.exception.UnauthorizedException;
import com.tuyenngoc.bookstore.repository.RoleRepository;
import com.tuyenngoc.bookstore.repository.UserRepository;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.security.jwt.JwtTokenProvider;
import com.tuyenngoc.bookstore.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    private final RoleRepository roleRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateToken(customUserDetails, Boolean.FALSE);
            String refreshToken = jwtTokenProvider.generateToken(customUserDetails, Boolean.TRUE);

            jwtTokenService.saveAccessTokenToRedis(accessToken, customUserDetails.getUsername());
            jwtTokenService.saveRefreshTokenToRedis(refreshToken, customUserDetails.getUsername());

            return new LoginResponseDto(
                    customUserDetails.getId(),
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
    public User register(RegisterRequestDto requestDto, AddressDto addressDto) {
        if (!requestDto.isPasswordMatch()) {
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
        //Create a new user
        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(roleRepository.findByName(RoleConstant.CUSTOMER.getRoleName()));
        return userRepository.save(user);
    }

}
