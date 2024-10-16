package com.kevinpina.shopping.management.domain.repository.item;

import com.kevinpina.shopping.management.domain.model.Item;

import java.util.List;

/**
 * FetchCsvRepository.
 */
public interface FetchItemRepository {

    /**
     * Query list items.
     *
     * @param userName userName
     * @return the list Items
     */
    List<Item> fetch(final String userName);

}
