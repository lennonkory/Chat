package kcomp.chat.common.listeners;

import kcomp.chat.common.messages.RoomMessage;
import kcomp.chat.common.messages.UserMessage;

public interface RoomListener {

	void roomMessage(UserMessage message);

	void openRoom(RoomMessage message);

}
