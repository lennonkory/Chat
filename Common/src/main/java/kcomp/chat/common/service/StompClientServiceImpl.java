package kcomp.chat.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import kcomp.chat.common.entities.Client;
import kcomp.chat.common.entities.SocketProperties;
import kcomp.chat.common.entities.StompWebSocketClient;

@Service
@Configurable
public class StompClientServiceImpl implements ClientService {

	@Autowired
	private SocketProperties socketProps;

	@Override
	public Client createClient(String userName) {
		Client client = new StompWebSocketClient();
		client.connect(socketProps.getUrl());
		client.setUserName(userName);

		for (String sub : socketProps.getQueues()) {
			client.subscribe(sub);
		}

		return client;
	}

}
