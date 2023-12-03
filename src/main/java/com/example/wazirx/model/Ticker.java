package com.example.wazirx.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Ticker implements Serializable {
	private String symbol;
	private String baseAsset;
	private String quoteAsset;
	private double lastPrice;
	private double lowPrice;
	private double highPrice;
	private double volume;
	private double sell;
	private double buy;
	private double askPrice;
	private double openPrice;
}
