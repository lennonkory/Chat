package kcomp.chat.common.listeners;

import java.util.LinkedHashMap;

public class RoomClientListener implements Listener<RoomListener> {

	RoomListener listener;

	@Override
	public void listen(Object payload) {
		LinkedHashMap<?, ?> mappy = (LinkedHashMap<?, ?>) payload;
		listener.roomMessage((String) mappy.get("payLoad"));
	}

	@Override
	public void setListener(RoomListener listener) {
		this.listener = listener;
	}

}
