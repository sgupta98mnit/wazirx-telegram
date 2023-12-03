package com.example.wazirx.utils;

public class TelegramBotUtils {

	public static String getTelegramApiUrl(String botToken, String metohdName) {
		return "https://api.telegram.org/bot" + botToken + "/" + metohdName;
	}
}
