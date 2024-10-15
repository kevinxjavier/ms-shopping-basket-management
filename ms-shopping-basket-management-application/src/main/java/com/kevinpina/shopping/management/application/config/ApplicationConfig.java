package com.kevinpina.shopping.management.application.config;

import com.kevinpina.shopping.management.application.usecase.user.GetUserUseCaseImpl;
import com.kevinpina.shopping.management.domain.repository.user.FetchUserRepository;
import com.kevinpina.shopping.management.domain.usecase.user.GetUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class Application config.
 */
@Configuration
public class ApplicationConfig {

    /**
     * GetUserUseCase.
     *
     * @param fetchUserRepository fetchUserRepository
     * @return implementation
     */
    @Bean
    public GetUserUseCase getUserUseCase(final FetchUserRepository fetchUserRepository) {
        return new GetUserUseCaseImpl(fetchUserRepository);
    }

}
