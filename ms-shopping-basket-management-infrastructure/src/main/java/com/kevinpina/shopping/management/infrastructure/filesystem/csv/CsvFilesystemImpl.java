package com.kevinpina.shopping.management.infrastructure.filesystem.csv;

import com.jcraft.jsch.*;
import com.kevinpina.shopping.management.domain.filesystem.csv.CsvFilesystem;
import com.kevinpina.shopping.management.domain.model.ConnectionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation for {@link CsvFilesystem}.
 */
public class CsvFilesystemImpl implements CsvFilesystem {

	private static final Logger log = LoggerFactory.getLogger(CsvFilesystemImpl.class);

	/**
	 * {@inheritDoc}
	 */
	public boolean saveFileRemotely(ConnectionProperties connectionProperties)  {
		Session jschSession = null;
		try {

			JSch jsch = new JSch();
			jsch.setKnownHosts(System.getProperty("user.home") + "/.ssh/known_hosts");
			jschSession = jsch.getSession(connectionProperties.getUser(), connectionProperties.getHost(), connectionProperties.getPort());

			// authenticate using private key
			// jsch.addIdentity("/home/kevin/.ssh/id_rsa");

			// authenticate using password
			jschSession.setPassword(connectionProperties.getPassword());

			// HostKeyRepository implementation to accept host unknown keys
			jschSession.setConfig("StrictHostKeyChecking", "ask");

			// O también podrías personalizar la verificación con un HostKeyRepository propio
			jschSession.setHostKeyRepository(new HostKeyRepository() {
				@Override
				public int check(String host, byte[] key) {
					return HostKeyRepository.OK;
				}

				@Override
				public void add(HostKey hostkey, UserInfo ui) {
					// Lógica para añadir host key
				}

				@Override
				public void remove(String host, String type) {}

				@Override
				public void remove(String host, String type, byte[] key) {}

				@Override
				public String getKnownHostsRepositoryID() {
					return null;
				}

				@Override
				public HostKey[] getHostKey() {
					return new HostKey[0];
				}

				@Override
				public HostKey[] getHostKey(String host, String type) {
					return new HostKey[0];
				}
			});

			jschSession.connect(connectionProperties.getTimeOutSession());

			Channel sftp = jschSession.openChannel("sftp");

			sftp.connect(connectionProperties.getTimeOuChannel());

			ChannelSftp channelSftp = (ChannelSftp) sftp;

			// transfer file from local to remote server
			channelSftp.put(connectionProperties.getLocalFile(), connectionProperties.getRemoteFile());

			// download file from remote server to local
			// channelSftp.get(remoteFile, localFile);

			channelSftp.exit();
			log.info("Saving file remotely {} in {}", connectionProperties.getLocalFile(), connectionProperties.getHost());
		} catch (SftpException | JSchException e) {
			log.error("Problems saving file in {}, error: {}", connectionProperties.getHost(), e.getMessage());
			return false;
		} finally {
			if (jschSession != null) {
				jschSession.disconnect();
			}
		}
		return true;
	}

}
