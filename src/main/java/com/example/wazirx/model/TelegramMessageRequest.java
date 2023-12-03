package com.example.wazirx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class TelegramMessageRequest implements Serializable {

	@JsonProperty("chat_id")
	private String chatId;

	private String text;
}
