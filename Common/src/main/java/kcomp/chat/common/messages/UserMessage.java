package kcomp.chat.common.messages;

import kcomp.chat.common.enums.MessageVisability;

public class UserMessage {

	private String from;
	private String to;
	private MessageVisability messageVisability;
	private String message;

	public UserMessage(String from, String to, MessageVisability messageVisability, String message) {
		super();
		this.from = from;
		this.to = to;
		this.messageVisability = messageVisability;
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public MessageVisability getMessageVisability() {
		return messageVisability;
	}

	public void setMessageVisability(MessageVisability messageVisability) {
		this.messageVisability = messageVisability;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
