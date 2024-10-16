package com.kevinpina.shopping.management.infrastructure.repository.item;

import com.kevinpina.shopping.management.domain.model.Item;
import com.kevinpina.shopping.management.domain.repository.item.SaveItemRepository;
import com.kevinpina.shopping.management.infrastructure.repository.db.repository.ItemRepository;
import com.kevinpina.shopping.management.infrastructure.repository.mapper.impl.ItemMapper;
import lombok.RequiredArgsConstructor;

/**
 * The default implementation for {@link SaveItemRepository}.
 */
@RequiredArgsConstructor
public class SaveItemRepositoryImpl implements SaveItemRepository {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item save(Item item) {
		return itemMapper.convertToDto(itemRepository.save(itemMapper.convertToEntity(item)));
	}

}
