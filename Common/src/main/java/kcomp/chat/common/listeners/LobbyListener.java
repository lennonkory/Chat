package kcomp.chat.common.listeners;

public interface LobbyListener {

	void addRoom(String roomName, String creator);

	void openRoom(String roomName, Object[] clients);

}
