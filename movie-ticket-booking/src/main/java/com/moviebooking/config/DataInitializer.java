package com.moviebooking.config;

import com.moviebooking.entity.User;
import com.moviebooking.enums.Role;
import com.moviebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

       
        if (!userRepository.existsByEmail("admin@moviebooking.com")) {

            User admin = User.builder()
                    .name("Super Admin")
                    .email("admin@moviebooking.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .build();

            userRepository.save(admin);

            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║   Default Admin Created!          ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  Email   : admin@moviebooking.com ║");
            System.out.println("║  Password: admin123               ║");
            System.out.println("║  Role    : ROLE_ADMIN             ║");
            System.out.println("╚══════════════════════════════════╝");
        } else {
            System.out.println("✅ Admin already exists — skipping");
        }

      
        if (!userRepository.existsByEmail("customer@moviebooking.com")) {

            User customer = User.builder()
                    .name("Test Customer")
                    .email("customer@moviebooking.com")
                    .password(passwordEncoder.encode("customer123"))
                    .role(Role.ROLE_CUSTOMER)
                    .build();

            userRepository.save(customer);

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║   Default Customer Created!           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  Email   : customer@moviebooking.com  ║");
            System.out.println("║  Password: customer123                ║");
            System.out.println("║  Role    : ROLE_CUSTOMER              ║");
            System.out.println("╚══════════════════════════════════════╝");
        } else {
            System.out.println("✅ Customer already exists — skipping");
        }
    }
}