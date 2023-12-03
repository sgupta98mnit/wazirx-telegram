package com.example.wazirx.utils;

import com.example.wazirx.model.Fund;
import com.example.wazirx.model.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class WazirxUtils {

	public static String getBaseSymbol(Ticker ticker) {
		return StringUtils.replace(ticker.getSymbol(), ticker.getQuoteAsset(), "");
	}

	public static double getPercentageChange(Ticker ticker) {
		return (ticker.getLastPrice() - ticker.getOpenPrice()) / ticker.getOpenPrice() * 100;
	}

	public static boolean checkIfFundMatches(List<Fund> currentFunds, Ticker ticker) {
		return currentFunds.stream()
			.anyMatch(fund -> StringUtils.equalsIgnoreCase(fund.getAsset(), getBaseSymbol(ticker)));
	}

	public static List<Fund> getCurrentFunds(List<Fund> funds) {
		return funds.stream()
			.filter(fund -> fund.getFree() > 0 || fund.getLocked() > 0 || fund.getReservedFee() > 0)
			.toList();
	}
}
