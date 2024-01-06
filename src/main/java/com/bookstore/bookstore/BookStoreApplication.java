package com.bookstore.bookstore;

import com.bookstore.bookstore.config.properties.AdminInfo;
import com.bookstore.bookstore.constant.RoleConstant;
import com.bookstore.bookstore.domain.entity.Customer;
import com.bookstore.bookstore.domain.entity.Role;
import com.bookstore.bookstore.domain.entity.User;
import com.bookstore.bookstore.repository.CustomerRepository;
import com.bookstore.bookstore.repository.RoleRepository;
import com.bookstore.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
@EnableConfigurationProperties({AdminInfo.class})
@EnableJpaAuditing
public class BookStoreApplication {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner init(AdminInfo adminInfo) {
        return args -> {
            //init role
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(1, RoleConstant.ADMINISTRATOR.getRoleName(), null));
                roleRepository.save(new Role(2, RoleConstant.CUSTOMER.getRoleName(), null));
                roleRepository.save(new Role(3, RoleConstant.SALES_STAFF.getRoleName(), null));
            }
            //init admin
            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .username(adminInfo.getUsername())
                        .password(passwordEncoder.encode(adminInfo.getPassword()))
                        .email(adminInfo.getEmail())
                        .role(roleRepository.findByName(RoleConstant.ADMINISTRATOR.getRoleName()))
                        .customer(customerRepository.save(new Customer(adminInfo.getName(), adminInfo.getPhoneNumber())))
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
