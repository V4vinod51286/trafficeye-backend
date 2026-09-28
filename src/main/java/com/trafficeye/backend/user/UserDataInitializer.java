package com.trafficeye.backend.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDataInitializer {

    @Bean
    CommandLineRunner createDevelopmentUser(
            UserRepository userRepository) {

        return args -> {

            if (userRepository.count() == 0) {

                User user = new User();

                user.setMobile("9999999999");
                user.setName("Development User");

                userRepository.save(user);

                System.out.println(
                        "Development user created."
                );
            }
        };
    }
}