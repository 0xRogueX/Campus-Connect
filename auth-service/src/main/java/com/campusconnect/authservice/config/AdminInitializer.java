package com.campusconnect.authservice.config;


import com.campusconnect.authservice.model.entity.Role;
import com.campusconnect.authservice.model.entity.User;
import com.campusconnect.authservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setEmail("princechangani.dev@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Role.valueOf("ADMIN"));
            user.setEnabled(true);
            user.setUsername("Prince Changani");
            userRepository.save(user);
            System.out.println("Default admin created with email: princechangani.dev@gmail.com and password: admin");
        }else {
            System.out.println("Admin already exists. Skipping initialization.");
        }
    }
}
