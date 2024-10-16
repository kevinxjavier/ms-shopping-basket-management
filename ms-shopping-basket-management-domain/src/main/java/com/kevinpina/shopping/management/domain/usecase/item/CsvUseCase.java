package com.kevinpina.shopping.management.domain.usecase.item;

import com.kevinpina.shopping.management.domain.model.ConnectionProperties;

/**
 * CsvFileUseCase.
 */
public interface CsvUseCase {

	/**
	 * Save item files remotely.
	 *
	 * @param connectionProperties connectionProperties
	 * @return true if saved
	 */
	boolean saveFileRemotely(ConnectionProperties connectionProperties);

}
