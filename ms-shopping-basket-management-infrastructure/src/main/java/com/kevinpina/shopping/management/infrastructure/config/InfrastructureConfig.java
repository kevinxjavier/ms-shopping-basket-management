package com.kevinpina.shopping.management.infrastructure.config;

import com.kevinpina.shopping.management.domain.filesystem.csv.CsvFilesystem;
import com.kevinpina.shopping.management.domain.repository.item.FetchItemRepository;
import com.kevinpina.shopping.management.domain.repository.item.SaveItemRepository;
import com.kevinpina.shopping.management.infrastructure.filesystem.csv.CsvFilesystemImpl;
import com.kevinpina.shopping.management.infrastructure.repository.db.repository.ItemRepository;
import com.kevinpina.shopping.management.infrastructure.repository.item.FetchItemRepositoryImpl;
import com.kevinpina.shopping.management.infrastructure.repository.item.SaveItemRepositoryImpl;
import com.kevinpina.shopping.management.infrastructure.repository.mapper.impl.ItemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class Infrastructure config.
 */
@Configuration
public class InfrastructureConfig {

    /**
     * FetchItemRepository.
     *
     * @param itemRepository itemRepository
     * @return implementation
     */
    @Bean
    public FetchItemRepository fetchItemRepository(final ItemRepository itemRepository) {
        return new FetchItemRepositoryImpl(itemRepository);
    }

    /**
     * SaveItemRepository.
     *
     * @param itemRepository itemRepository
     * @param itemMapper itemMapper
     * @return implementation
     */
    @Bean
    public SaveItemRepository saveItemRepository(final ItemRepository itemRepository, final ItemMapper itemMapper) {
        return new SaveItemRepositoryImpl(itemRepository, itemMapper);
    }

    /**
     * CsvFilesystem.
     *
     * @return implementation
     */
    @Bean
    public CsvFilesystem csvFilesystem() {
        return new CsvFilesystemImpl();
    }

}
