package com.kevinpina.shopping.management.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * <p>
 * WebConfigurer class.
 * </p>
 *
 * @author Kevin Piña
 * @version 0.0.1
 */
@Configuration
public class UserManagementApplicationConfig {

    /**
     * <p>
     * objectMapper.
     * </p>
     *
     * @param builder a {@link Jackson2ObjectMapperBuilder} object.
     * @return a {@link ObjectMapper} object.
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(final Jackson2ObjectMapperBuilder builder) {
        final ObjectMapper objectMapper = builder.build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

}
