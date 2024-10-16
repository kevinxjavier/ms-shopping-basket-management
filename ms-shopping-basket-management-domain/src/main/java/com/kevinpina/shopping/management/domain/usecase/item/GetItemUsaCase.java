package com.kevinpina.shopping.management.domain.usecase.item;

import com.kevinpina.shopping.management.domain.model.Item;

import java.util.List;

/**
 * GetItemUsaCase.
 */
public interface GetItemUsaCase {

    /**
     * Query items by userName.
     *
     * @param userName userName
     * @return list items
     */
    List<Item> getItems(final String userName);

}
