package kcomp.chat.common.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralMessage {
	private String from;
	private String text;

	@JsonCreator
	public GeneralMessage(@JsonProperty("from") String from, @JsonProperty("text") String text) {
		this.from = from;
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
