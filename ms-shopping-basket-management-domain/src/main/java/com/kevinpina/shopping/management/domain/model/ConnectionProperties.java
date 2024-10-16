package com.kevinpina.shopping.management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionProperties {

	private String host;
	private int port;
	private String user;
	private String password;
	private int timeOutSession;
	private int timeOuChannel;
	private String localFile;
	private String remoteFile;

}
