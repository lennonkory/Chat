package kcomp.chat.chatserver.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Component;

import kcomp.chat.common.entities.Room;
import kcomp.chat.common.enums.MessageType;
import kcomp.chat.common.messages.Message;
import kcomp.chat.common.messages.RoomMessage;

@Scope(value = "singleton")
@Component
public class Lobby {

	/*
	 * Clients Rooms
	 */

	@Autowired
	public SimpMessageSendingOperations messagingTemplate;

	Map<String, String> clients;

	Map<String, Room> rooms;

	public Lobby() {
		clients = new HashMap<>();
		rooms = new HashMap<>();
	}

	public void addClientToLobby(String sessionId, String userName) {
		clients.put(sessionId, userName);
		sendLoginAcknowledgement(sessionId);
		// Add rooms to Lobby
		SimpMessageHeaderAccessor header = createMessage(sessionId);
		for (String roomName : rooms.keySet()) {
			Room room = rooms.get(roomName);
			Message<String> msg = new Message<>(room.getCreator(), MessageType.ADD_ROOM, roomName);
			messagingTemplate.convertAndSendToUser(sessionId, "/queue/lobby", msg, header.getMessageHeaders());
		}
	}

	public void addRoom(String from, String roomName) {
		Message<String> msg = new Message<>(from, MessageType.ADD_ROOM, roomName);
		Room room = new Room(roomName, from, new ArrayList<Object>());
		room.addClientToRoom(from);
		this.rooms.put(roomName, room);
		messagingTemplate.convertAndSend("/topic/messages", msg);

	}

	public void openRoom(String from, String roomName, String sessionId) {

		SimpMessageHeaderAccessor header = createMessage(sessionId);
		Room room = rooms.get(roomName);
		addClientToRoom(room, from);
		RoomMessage roommsg = new RoomMessage(roomName, from, "openRoom", room.getClients().toArray());
		Message<RoomMessage> msg = new Message<>(from, MessageType.OPEN_ROOM, roommsg);
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/lobby", msg, header.getMessageHeaders());

		msg = new Message<>(roomName, MessageType.OPEN_ROOM, roommsg);

		messagingTemplate.convertAndSend("/topic/rooms" + roomName, msg);

	}

	// Adds client to room if they are not already in the room
	private void addClientToRoom(Room room, String userName) {

		boolean inRoom = false;

		for (Object object : room.getClients()) {
			if (userName.equals(object)) {
				inRoom = true;
				break;
			}
		}

		if (!inRoom) {
			room.getClients().add(userName);
		}

	}

	public void sendMessageToRoom(String roomName, String sessionId) {
		SimpMessageHeaderAccessor header = createMessage(sessionId);
		Message<String> msgStr = new Message<>(roomName, MessageType.OPEN_ROOM, "HI");
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/room", msgStr, header.getMessageHeaders());

	}

	private void sendLoginAcknowledgement(String sessionId) {
		SimpMessageHeaderAccessor header = createMessage(sessionId);
		Message<String> msg = new Message<>("Server", MessageType.LOG_IN, null);
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/lobby", msg, header.getMessageHeaders());
	}

	private SimpMessageHeaderAccessor createMessage(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor;
	}

	/*
	 * find user find room exit??
	 */

}
