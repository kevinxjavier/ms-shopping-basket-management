package com.kevinpina.shopping.management.application.usecase.csv;

import com.kevinpina.shopping.management.domain.filesystem.csv.CsvFilesystem;
import com.kevinpina.shopping.management.domain.model.ConnectionProperties;
import com.kevinpina.shopping.management.domain.usecase.item.CsvUseCase;
import lombok.RequiredArgsConstructor;

/**
 * The default implementation for {@link CsvUseCase}.
 */
@RequiredArgsConstructor
public class CsvUseCaseImpl implements CsvUseCase {

	private final CsvFilesystem csvFilesystem;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean saveFileRemotely(ConnectionProperties connectionProperties) {
		return csvFilesystem.saveFileRemotely(connectionProperties);
	}

}
