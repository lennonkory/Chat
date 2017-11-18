package kcomp.chat.common.entities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import kcomp.chat.common.listeners.Listener;
import kcomp.chat.common.messages.ClientMessage;
import kcomp.chat.common.messages.Message;

public class StompWebSocketClient implements Client {

	private static Logger logger = Logger.getLogger(StompWebSocketClient.class.getName());

	private StompSession session;
	private String userName;
	private Listener<?> listener;

	@Override
	public void connect(String url) {

		logger.info("Connecting client to: " + url);

		StandardWebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(simpleWebSocketClient));

		SockJsClient sockJsClient = new SockJsClient(transports);

		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		StompSessionHandler sessionHandler = new MyStompSessionHandler();

		try {
			session = stompClient.connect(url, sessionHandler).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void connect(String userName, String password, String url) {

	}

	@Override
	public void subscribe(String destination) {

		session.subscribe(destination, new StompFrameHandler() {

			@Override
			public Type getPayloadType(StompHeaders headers) {
				return Object.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				listener.listen(payload);
				System.err.println(payload.toString());
			}
		});
	}

	@Override
	public void send(String destination, String message) {
		ClientMessage msg = new ClientMessage("SomeJava", message);
		session.send(destination, msg);
	}

	@Override
	public void sendToUser(String destination, String message, String userId) {

	}

	static public class MyStompSessionHandler extends StompSessionHandlerAdapter {
	}

	@Override
	public String getSessionId() {
		return session.getSessionId();
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public <T> void send(String destination, Message<T> message) {
		session.send(destination, message);
	}

	@Override
	public void setListener(Listener<?> listener) {
		this.listener = listener;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
