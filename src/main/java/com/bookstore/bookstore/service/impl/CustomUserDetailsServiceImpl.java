package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.domain.entity.User;
import com.bookstore.bookstore.repository.UserRepository;
import com.bookstore.bookstore.security.CustomUserDetails;
import com.bookstore.bookstore.service.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("ok"));
        return CustomUserDetails.create(user);
    }

    @Override
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("ok"));
        return CustomUserDetails.create(user);
    }
}
