package kcomp.chat.chatserver.contollers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kcomp.chat.chatserver.entities.Lobby;
import kcomp.chat.common.messages.GeneralMessage;
import kcomp.chat.common.messages.Message;

@Controller
public class LobbyController {

	private static Logger logger = Logger.getLogger(LobbyController.class);

	@Autowired
	Lobby lobby;

	@Autowired
	public SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/lobby")
	public void start(GeneralMessage message, SimpMessageHeaderAccessor accessor) {
		String sessionId = accessor.getSessionId();
		logger.info("Adding user: " + sessionId);
		lobby.addClientToLobby(sessionId, message.getFrom());

	}

	@MessageMapping("/lobby/addRoom")
	public <T> void addRoom(Message<T> message, SimpMessageHeaderAccessor accessor) {
		logger.info("Adding Room: " + message.getPayLoad());
		lobby.addRoom(message.getFrom(), (String) message.getPayLoad());

	}

	@MessageMapping("/lobby/openRoom")
	public <T> void openRoom(Message<T> message, SimpMessageHeaderAccessor accessor) {
		logger.info("Opening Room: " + message.getPayLoad());
		lobby.openRoom(message.getFrom(), (String) message.getPayLoad(), accessor.getSessionId());
	}

	@RequestMapping("/greeting")
	public String greetingT() {
		logger.info("Greeting Controller");
		return "greeting";
	}

	@RequestMapping("/lobbyPage")
	public String lobby() {
		logger.info("Lobby");
		return "lobbyPage";
	}

}
