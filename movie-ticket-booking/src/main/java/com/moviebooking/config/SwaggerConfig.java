package com.moviebooking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title       = "Movie Ticket Booking System API",
        version     = "1.0.0",
        description = "REST APIs for Movie Ticket Booking System " +
                      "with JWT Authentication, Role Based Access " +
                      "Control, Real-time Seat Availability " +
                      "and Concurrent Booking Protection",
        contact = @Contact(
            name  = "Movie Booking Support",
            email = "support@moviebooking.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url  = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url         = "http://localhost:8080",
            description = "Local Development Server"
        )
    }
)
@SecurityScheme(
    name         = "bearerAuth",
    type         = SecuritySchemeType.HTTP,
    scheme       = "bearer",
    bearerFormat = "JWT",
    description  = "Enter JWT token from /api/auth/login response"
)
public class SwaggerConfig {
    
}