package com.kevinpina.shopping.management.application.usecase.item;

import java.util.List;

import com.kevinpina.shopping.management.domain.model.Item;
import com.kevinpina.shopping.management.domain.repository.item.FetchItemRepository;
import com.kevinpina.shopping.management.domain.usecase.item.GetItemUsaCase;

import lombok.RequiredArgsConstructor;

/**
 * The default implementation for {@link GetItemUsaCase}.
 */
@RequiredArgsConstructor
public class GetItemUseCaseImpl implements GetItemUsaCase {

	private final FetchItemRepository fetchItemRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Item> getItems(final String userId) {
		return fetchItemRepository.fetch(userId);
	}

}
