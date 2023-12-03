package com.example.wazirx.service;

import com.example.wazirx.model.TelegramMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.wazirx.utils.TelegramBotUtils.getTelegramApiUrl;

@Component
@Slf4j
public class TelegramBotService {

	@Value("${telegram.bot.token}")
	private String telegramBotToken;

	private String chatId = "6888268815";

	public void sendTelegramMessage(String message) {
		log.info("Sending telegram message: {}", message);
		String messageApiUrl = getTelegramApiUrl(telegramBotToken, "sendMessage");
		TelegramMessageRequest telegramMessageRequest = TelegramMessageRequest.builder()
			.chatId(chatId)
			.text(message)
			.build();
		RestTemplate restTemplate = new RestTemplate();

		String response = restTemplate.postForObject(messageApiUrl, telegramMessageRequest, String.class);
		log.info("Telegram message sent: {}", response);
	}

	public String getChatId() {
		log.info("Getting chat id");
		String chatApiUrl = getTelegramApiUrl(telegramBotToken, "getChat");
		RestTemplate restTemplate = new RestTemplate();
		TelegramMessageRequest telegramMessageRequest = TelegramMessageRequest.builder()
			.chatId(chatId)
			.build();
		String response = restTemplate.postForObject(chatApiUrl, telegramMessageRequest, String.class);
		log.info("Chat id: {}", response);
		return response;
	}

	public String getUpdates() {
		log.info("Getting updates");
		String updatesApiUrl = getTelegramApiUrl(telegramBotToken, "getUpdates");
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(updatesApiUrl, String.class);
		log.info("Updates: {}", response);
		return response;
	}
}
