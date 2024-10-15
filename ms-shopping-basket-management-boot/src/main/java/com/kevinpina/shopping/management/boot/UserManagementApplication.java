package com.kevinpina.shopping.management.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * The class Application.
 */
@SpringBootApplication(scanBasePackages = {"com.kevinpina"})
@EnableCaching
public class UserManagementApplication {
    
    /**
     * Main.
     *
     * @param args the args
     * 
     */
    public static void main(final String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }
}
