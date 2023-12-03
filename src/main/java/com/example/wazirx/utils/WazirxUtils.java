package com.example.wazirx.utils;

import com.example.wazirx.model.Fund;
import com.example.wazirx.model.Ticker;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


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
}
