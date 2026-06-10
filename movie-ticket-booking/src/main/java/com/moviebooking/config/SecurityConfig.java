package com.moviebooking.config;

import com.moviebooking.security.jwt.JwtAuthFilter;
import com.moviebooking.security.jwt.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    
    private static final String[] PUBLIC_URLS = {
        "/api/auth/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/api-docs/**",
        "/v3/api-docs/**"
    };

    private static final String[] ADMIN_URLS = {
        "/api/admin/**"
    };

    
    private static final String[] CUSTOMER_URLS = {
        "/api/customer/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
       
            .csrf(csrf -> csrf.disable())

           
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

         
            .authorizeHttpRequests(auth -> auth
             
                .requestMatchers(PUBLIC_URLS).permitAll()

                .requestMatchers(ADMIN_URLS).hasRole("ADMIN")

                .requestMatchers(CUSTOMER_URLS).hasRole("CUSTOMER")

                .requestMatchers("/api/seats/**").hasAnyRole("ADMIN", "CUSTOMER")

               
                .anyRequest().authenticated()
            )

            .authenticationProvider(authenticationProvider())

         
            .addFilterBefore(jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

   
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
