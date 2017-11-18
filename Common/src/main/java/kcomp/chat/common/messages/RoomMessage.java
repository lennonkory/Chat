package kcomp.chat.common.messages;

public class RoomMessage {

	private String roomName;
	private String creator;
	private String message;
	private Object[] clients;

	public RoomMessage(String roomName, String creator, String message, Object[] clients) {
		super();
		this.roomName = roomName;
		this.creator = creator;
		this.message = message;
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

	public Object[] getClients() {
		return clients;
	}

	public void setClients(Object[] clients) {
		this.clients = clients;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
