package kcomp.chat.chatserver.contollers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import kcomp.chat.chatserver.entities.Lobby;
import kcomp.chat.common.enums.MessageType;
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

	@MessageMapping("/room/{roomName}")
	public void room(Message<String> message, SimpMessageHeaderAccessor accessor) {
		logger.info("Single Room: " + message.getPayLoad());
		String sessionId = accessor.getSessionId();
		logger.info("Room sessionId: " + sessionId);
		// lobby.sendMessageToRoom(message.getPayLoad(),
		// sessionId);/topic/rooms/test
		Message<String> msg = new Message<>(message.getFrom(), MessageType.ADD_ROOM, message.getPayLoad());

		messagingTemplate.convertAndSend("/topic/rooms" + message.getFrom(), msg);
		msg.setPayLoad(message.getPayLoad());
		// messagingTemplate.convertAndSend("/topic/rooms", msg);

	}

}
