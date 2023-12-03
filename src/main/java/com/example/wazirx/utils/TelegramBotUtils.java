package com.example.wazirx.utils;

public class TelegramBotUtils {

	public static String getTelegramApiUrl(String botToken, String methodName) {
		return "https://api.telegram.org/bot" + botToken + "/" + methodName;
	}
}
