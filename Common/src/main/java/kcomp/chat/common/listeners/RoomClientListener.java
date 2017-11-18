package kcomp.chat.common.listeners;

import java.util.LinkedHashMap;

import kcomp.chat.common.enums.MessageVisability;
import kcomp.chat.common.messages.UserMessage;

public class RoomClientListener implements Listener<RoomListener> {

	RoomListener listener;

	@Override
	public void listen(Object payload) {
		LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) payload;
		LinkedHashMap<?, ?> message = (LinkedHashMap<?, ?>) map.get("payLoad");
		UserMessage userMessage = createUserMessage(message);
		listener.roomMessage(userMessage);
	}

	private UserMessage createUserMessage(LinkedHashMap<?, ?> messageMap) {

		String from = (String) messageMap.get("from");
		String to = (String) messageMap.get("to");
		String message = (String) messageMap.get("message");
		MessageVisability messageVisability = MessageVisability.valueOf((String) messageMap.get("messageVisability"));

		UserMessage userMessage = new UserMessage(from, to, messageVisability, message);
		return userMessage;
	}

	@Override
	public void setListener(RoomListener listener) {
		this.listener = listener;
	}

}
