package kcomp.chat.common.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import kcomp.chat.common.enums.MessageType;

public class Message<T> {

	private String from;
	private MessageType messageType;
	private T payLoad;

	@JsonCreator
	public Message(@JsonProperty("from") String from, @JsonProperty("messageType") MessageType messageType,
			@JsonProperty("payLoad") T payLoad) {
		super();
		this.from = from;
		this.messageType = messageType;
		this.payLoad = payLoad;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public T getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(T payLoad) {
		this.payLoad = payLoad;
	}

}
