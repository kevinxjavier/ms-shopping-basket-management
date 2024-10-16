package com.kevinpina.shopping.management.domain.repository.item;

import com.kevinpina.shopping.management.domain.model.Item;

/**
 * FetchCsvRepository.
 */
public interface SaveItemRepository {

	/**
	 * Save items.
	 *
	 * @param item item
	 * @return items
	 */
	Item save(Item item);

}
