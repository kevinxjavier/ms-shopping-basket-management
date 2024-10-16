package com.kevinpina.shopping.management.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	public static final Integer NUMBER_DIVISOR_PERCENTAGE = 100;
	public static final String TYPE = "text/csv";

	public static final String CSV = ".csv";
	public static final String NOK = "/nok_";

	public static final String USERNAME = "username";
	public static final String THE_FILE_WAS_NOT_SAVED_REMOTELY = "The file was not saved remotely";

	public static final String HEDAER_ID = "id";
	public static final String HEADER_NAME = "name";
	public static final String HEADER_VALUE = "value";
	public static final String HEADER_TAX = "tax";
	public static final String FORMAT_DATE = "yyMMddHHmmss";
	public static final String NOK_PREFIX_FILE_NAME = "nok_";

	public static final String SAVE_REMOTELY_FILE = "save";
	public static final String HOST = "host";
	public static final String PORT = "port";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String TIMEOUT_SESSION = "timeout.session";
	public static final String TIMEOUT_CHANNEL = "timeout.channel";
	public static final String PATH = "path";

}
