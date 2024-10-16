package com.kevinpina.shopping.management.application.config;

import com.kevinpina.shopping.management.application.usecase.csv.CsvUseCaseImpl;
import com.kevinpina.shopping.management.application.usecase.item.GetItemUseCaseImpl;
import com.kevinpina.shopping.management.application.usecase.item.SaveItemUseCaseImpl;
import com.kevinpina.shopping.management.domain.filesystem.csv.CsvFilesystem;
import com.kevinpina.shopping.management.domain.repository.item.FetchItemRepository;
import com.kevinpina.shopping.management.domain.repository.item.SaveItemRepository;
import com.kevinpina.shopping.management.domain.usecase.item.CsvUseCase;
import com.kevinpina.shopping.management.domain.usecase.item.GetItemUsaCase;
import com.kevinpina.shopping.management.domain.usecase.item.SaveItemUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class Application config.
 */
@Configuration
public class ApplicationConfig {

    /**
     * GetItemUsaCase.
     *
     * @param fetchItemRepository fetchItemRepository
     * @return implementation
     */
    @Bean
    public GetItemUsaCase getItemUsaCase(final FetchItemRepository fetchItemRepository) {
        return new GetItemUseCaseImpl(fetchItemRepository);
    }

    /**
     * SaveItemUsaCase.
     *
     * @param saveItemRepository saveItemRepository
     * @return implementation
     */
    @Bean
    public SaveItemUseCase saveItemUseCase(final SaveItemRepository saveItemRepository) {
        return new SaveItemUseCaseImpl(saveItemRepository);
    }

    /**
     * CsvUseCase.
     *
     * @param csvFilesystem csvFilesystem
     * @return implementation
     */
    @Bean
    public CsvUseCase csvFileUseCase(final CsvFilesystem csvFilesystem) {
        return new CsvUseCaseImpl(csvFilesystem);
    }

}
