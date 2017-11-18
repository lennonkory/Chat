package kcomp.chat.common.entities;

import kcomp.chat.common.listeners.Listener;
import kcomp.chat.common.messages.Message;

public interface Client {

	void connect(String url);

	void connect(String userName, String password, String url);

	void subscribe(String destination);

	// Create a message sender
	void send(String destination, String message);

	<T> void send(String destination, Message<T> message);

	void sendToUser(String destination, String message, String userId);

	String getSessionId();

	void setUserName(String userName);

	String getUserName();

	void setListener(Listener<?> Listener);

}
