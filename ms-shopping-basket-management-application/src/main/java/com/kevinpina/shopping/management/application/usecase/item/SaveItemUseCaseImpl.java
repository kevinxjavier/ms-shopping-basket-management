package com.kevinpina.shopping.management.application.usecase.item;

import com.kevinpina.shopping.management.domain.model.Item;
import com.kevinpina.shopping.management.domain.repository.item.SaveItemRepository;
import com.kevinpina.shopping.management.domain.usecase.item.GetItemUsaCase;
import com.kevinpina.shopping.management.domain.usecase.item.SaveItemUseCase;
import lombok.RequiredArgsConstructor;

/**
 * The default implementation for {@link GetItemUsaCase}.
 */
@RequiredArgsConstructor
public class SaveItemUseCaseImpl implements SaveItemUseCase {

	private final SaveItemRepository saveItemRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Item saveItem(Item item) {
		return saveItemRepository.save(item);
	}

}
