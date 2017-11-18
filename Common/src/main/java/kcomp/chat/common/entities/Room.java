package kcomp.chat.common.entities;

import java.util.List;

public class Room {

	private String roomName;
	private String creator;
	private List<Object> clients;

	public Room(String roomName, String creator, List<Object> clients) {
		super();
		this.roomName = roomName;
		this.creator = creator;
		this.clients = clients;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public List<Object> getClients() {
		return clients;
	}

	public void setClients(List<Object> clients) {
		this.clients = clients;
	}

	public void addClientToRoom(Object client) {
		clients.add(client);
	}

	public void removeClientFromRoom(Object client) {
		clients.remove(client);
	}

}
