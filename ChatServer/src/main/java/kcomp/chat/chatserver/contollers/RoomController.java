package kcomp.chat.chatserver.contollers;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import kcomp.chat.common.enums.MessageType;
import kcomp.chat.common.enums.MessageVisability;
import kcomp.chat.common.messages.GeneralMessage;
import kcomp.chat.common.messages.Message;
import kcomp.chat.common.messages.UserMessage;

@Controller
public class RoomController {

	private static Logger logger = Logger.getLogger(RoomController.class);

	@Autowired
	public SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/room/{roomName}")
	public void room(Message<?> message, SimpMessageHeaderAccessor accessor) {

		logger.info("Single Room message: " + message.getPayLoad());

		LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) message.getPayLoad();

		String from = (String) map.get("from");
		String text = (String) map.get("text");

		GeneralMessage generalMessage = new GeneralMessage(from, text);

		UserMessage userMessage = new UserMessage(generalMessage.getFrom(), "all", MessageVisability.PUBLIC,
				generalMessage.getText());

		Message<UserMessage> msg = new Message<>(message.getFrom(), MessageType.SEND_MESSAGE, userMessage);

		messagingTemplate.convertAndSend("/topic/rooms" + message.getFrom(), msg);

	}
}
