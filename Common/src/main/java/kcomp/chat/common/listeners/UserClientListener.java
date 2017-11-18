package kcomp.chat.common.listeners;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import kcomp.chat.common.enums.MessageType;
import kcomp.chat.common.messages.RoomMessage;

public class UserClientListener implements Listener<LobbyListener> {

	private LobbyListener lobbyListener;

	@Override
	public void listen(Object payload) {
		LinkedHashMap<?, ?> mappy = (LinkedHashMap<?, ?>) payload;

		MessageType messageType = MessageType.valueOf((String) mappy.get("messageType"));

		if (messageType == null) {
			System.err.println("Message Type is null" + payload.toString());
			return;
		}

		if (messageType.equals(MessageType.ADD_ROOM)) {
			lobbyListener.addRoom((String) mappy.get("payLoad"), (String) mappy.get("from"));
		} else if (messageType.equals(MessageType.OPEN_ROOM)) {
			LinkedHashMap<?, ?> roomMessageMap = (LinkedHashMap<?, ?>) mappy.get("payLoad");
			ArrayList<?> clients = (ArrayList<?>) roomMessageMap.get("clients");
			RoomMessage roomMessage = new RoomMessage((String) roomMessageMap.get("roomName"),
					(String) roomMessageMap.get("creator"), (String) roomMessageMap.get("message"), clients.toArray());
			String roomName = roomMessage.getRoomName();
			lobbyListener.openRoom(roomName, roomMessage.getClients());
		}
	}

	@Override
	public void setListener(LobbyListener listener) {
		this.lobbyListener = listener;
	}

}
