package com.kevinpina.shopping.management.domain.filesystem.csv;

import com.kevinpina.shopping.management.domain.model.ConnectionProperties;

/**
 * CsvFileSystem.
 */
public interface CsvFilesystem {

	/**
	 * Save file remotely.
	 *
	 * @param connectionProperties connectionProperties
	 * @return true if saved remotely
	 */
	boolean saveFileRemotely(ConnectionProperties connectionProperties);

}
