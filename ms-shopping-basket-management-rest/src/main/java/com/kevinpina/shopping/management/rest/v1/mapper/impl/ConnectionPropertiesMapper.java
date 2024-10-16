package com.kevinpina.shopping.management.rest.v1.mapper.impl;

import com.kevinpina.shopping.management.domain.utils.Constants;
import com.kevinpina.shopping.management.rest.v1.config.ConnectionProperties;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ConnectionPropertiesMapper {

	public com.kevinpina.shopping.management.domain.model.ConnectionProperties convertToDto (ConnectionProperties connectionProperties, String localFile, String userName) {
		return com.kevinpina.shopping.management.domain.model.ConnectionProperties.builder()
			.host(connectionProperties.getProperties().get(Constants.HOST))
			.port(Integer.parseInt(connectionProperties.getProperties().get(Constants.PORT)))
			.user(connectionProperties.getProperties().get(Constants.USER))
			.password(connectionProperties.getProperties().get(Constants.PASSWORD))
			.timeOutSession(Integer.parseInt(connectionProperties.getProperties().get(Constants.TIMEOUT_SESSION)))
			.timeOuChannel(Integer.parseInt(connectionProperties.getProperties().get(Constants.TIMEOUT_CHANNEL)))
			.localFile(localFile)
			.remoteFile(connectionProperties.getProperties().get(Constants.PATH) + userName + "_" +
					new SimpleDateFormat(Constants.FORMAT_DATE).format(new Date()) + Constants.CSV)
			.build();
	}

}
