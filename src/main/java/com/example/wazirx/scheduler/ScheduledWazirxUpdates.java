package com.example.wazirx.scheduler;

import com.example.wazirx.model.Fund;
import com.example.wazirx.model.Ticker;
import com.example.wazirx.service.TelegramBotService;
import com.example.wazirx.service.WazirxApiService;
import com.example.wazirx.utils.WazirxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.wazirx.utils.WazirxUtils.checkIfFundMatches;

@Component
@Slf4j
public class ScheduledWazirxUpdates {

	private final WazirxApiService wazirxApiService;

	private final TelegramBotService telegramBotService;

	private final Long incrementOrDecrementPercent = 5L;

	public ScheduledWazirxUpdates(WazirxApiService wazirxApiService,
	                              TelegramBotService telegramBotService) {
		this.wazirxApiService = wazirxApiService;
		this.telegramBotService = telegramBotService;
	}

	@Scheduled(fixedRate = 10000)
	public void sendUpdatesOnIncrementPercentage() {
		log.info("Sending Wazirx updates");

		List<Fund> funds = wazirxApiService.getFund();
		List<Fund> currentFunds = WazirxUtils.getCurrentFunds(funds);
		log.info("currentFunds: {}", currentFunds);

		List<Ticker> dailyTickerStats = wazirxApiService.dailyTickerStats();

		List<Ticker> filteredTickers = dailyTickerStats.stream()
			.filter(ticker -> checkIfFundMatches(currentFunds, ticker))
			.filter(ticker -> {
				double percentChange = WazirxUtils.getPercentageChange(ticker);
				// trick to print only 2 decimal places
				log.info("ticker: {} Percent change: {}", ticker.getSymbol(), Math.round(percentChange * 100.0) / 100.0);
				return  percentChange > incrementOrDecrementPercent;
			})
			.toList();

		if(!filteredTickers.isEmpty()) {
			telegramBotService.sendTelegramMessage(filteredTickers.toString());
		}
	}

}
