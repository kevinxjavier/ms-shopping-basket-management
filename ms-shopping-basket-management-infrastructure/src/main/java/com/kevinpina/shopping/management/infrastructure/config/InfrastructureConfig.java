package com.kevinpina.shopping.management.infrastructure.config;

import com.kevinpina.shopping.management.domain.repository.user.FetchUserRepository;
import com.kevinpina.shopping.management.infrastructure.repository.db.repository.UserRepository;
import com.kevinpina.shopping.management.infrastructure.repository.user.FetchUserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class Infrastructure config.
 */
@Configuration
public class InfrastructureConfig {

    /**
     * FetchUserRepository.
     *
     * @param userRepository userRepository
     * @return implementation
     */
    @Bean
    public FetchUserRepository fetchUserRepository(final UserRepository userRepository) {
        return new FetchUserRepositoryImpl(userRepository);
    }

}
