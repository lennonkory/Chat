package kcomp.chat.common.entities;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("socket")
public class SocketProperties {

	private String url;
	private List<String> topics;
	private List<String> queues;
	private String loginUrl;
	private String addRoom;
	private String openRoom;
	private String room;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getQueues() {
		return queues;
	}

	public void setQueues(List<String> queues) {
		this.queues = queues;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getAddRoom() {
		return addRoom;
	}

	public void setAddRoom(String addRoom) {
		this.addRoom = addRoom;
	}

	public String getOpenRoom() {
		return openRoom;
	}

	public void setOpenRoom(String openRoom) {
		this.openRoom = openRoom;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
