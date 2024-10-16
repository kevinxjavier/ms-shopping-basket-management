package com.kevinpina.shopping.management.domain.usecase.item;

import com.kevinpina.shopping.management.domain.model.Item;

/**
 * SaveItemUseCase.
 */
public interface SaveItemUseCase {

	/**
	 * Save items.
	 *
	 * @param item item
	 * @return list items
	 */
	Item saveItem(Item item);

}
