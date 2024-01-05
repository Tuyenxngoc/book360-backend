package com.bookstore.bookstore;

import com.bookstore.bookstore.config.properties.AdminInfo;
import com.bookstore.bookstore.constant.Gender;
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

import java.time.LocalDate;
import java.util.HashSet;

@RequiredArgsConstructor
@SpringBootApplication
@EnableConfigurationProperties({AdminInfo.class})
@EnableJpaAuditing
public class BookStoreApplication {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner init(AdminInfo adminInfo) {
        return args -> {
            //init role
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(1, RoleConstant.ADMIN, null));
                roleRepository.save(new Role(2, RoleConstant.USER, null));
            }
            //init admin
            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .id("a")
                        .username(adminInfo.getUsername())
                        .password(adminInfo.getPassword())
                        .email(adminInfo.getEmail())
                        .role(roleRepository.findByName(RoleConstant.ADMIN))
                        .customer(customerRepository.save(
                                new Customer(1L,
                                        adminInfo.getName(),
                                        LocalDate.now(),
                                        adminInfo.getPhoneNumber(),
                                        Gender.MALE,
                                        new HashSet<>(),
                                        new HashSet<>()
                                ))
                        )
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
