package com.kevinpina.shopping.management.infrastructure.repository.item;

import com.kevinpina.shopping.management.domain.model.Item;
import com.kevinpina.shopping.management.domain.repository.item.FetchItemRepository;
import com.kevinpina.shopping.management.infrastructure.repository.db.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * The default implementation for {@link FetchItemRepository}.
 */
@RequiredArgsConstructor
public class FetchItemRepositoryImpl implements FetchItemRepository {

    private final ItemRepository itemRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> fetch(final String userName) {
        return itemRepository.findByUserName(userName).stream().map(ui -> Item.builder()
                .uuid(ui.getUuid())
                .userName(ui.getUserName())
                .items(ui.getItems())
                .build())
                .toList();
    }

}
