package com.kevinpina.shopping.management.infrastructure.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

/**
 * The class Auditor aware.
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Get current auditor.
     *
     * @return optional string
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("APIRest - Shopping Basket Management");
    }
}
