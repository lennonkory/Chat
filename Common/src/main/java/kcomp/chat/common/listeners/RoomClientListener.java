package kcomp.chat.common.listeners;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import kcomp.chat.common.enums.MessageType;
import kcomp.chat.common.enums.MessageVisability;
import kcomp.chat.common.messages.RoomMessage;
import kcomp.chat.common.messages.UserMessage;

public class RoomClientListener implements Listener<RoomListener> {

	RoomListener listener;

	@Override
	public void listen(Object payload) {

		LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) payload;
		MessageType messageType = MessageType.valueOf((String) map.get("messageType"));
		LinkedHashMap<?, ?> message = (LinkedHashMap<?, ?>) map.get("payLoad");
		switch (messageType) {
		case OPEN_ROOM:
			RoomMessage roomMessage = createRoomMessage(message);
			listener.openRoom(roomMessage);
			break;
		case SEND_MESSAGE:

			UserMessage userMessage = createUserMessage(message);
			listener.roomMessage(userMessage);
			break;
		default:
			break;
		}

	}

	private UserMessage createUserMessage(LinkedHashMap<?, ?> messageMap) {

		String from = (String) messageMap.get("from");
		String to = (String) messageMap.get("to");
		String message = (String) messageMap.get("message");
		MessageVisability messageVisability = MessageVisability.valueOf((String) messageMap.get("messageVisability"));

		UserMessage userMessage = new UserMessage(from, to, messageVisability, message);
		return userMessage;
	}

	private RoomMessage createRoomMessage(LinkedHashMap<?, ?> messageMap) {
		String roomName = (String) messageMap.get("roomName");
		String creator = (String) messageMap.get("creator");
		String message = (String) messageMap.get("message");
		List<?> clients = (ArrayList<?>) messageMap.get("clients");
		RoomMessage roomMessage = new RoomMessage(roomName, creator, message, clients.toArray());
		return roomMessage;
	}

	@Override
	public void setListener(RoomListener listener) {
		this.listener = listener;
	}

}
