package com.tuyenngoc.bookstore.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {

    UserDetails loadUserById(String id);

}
