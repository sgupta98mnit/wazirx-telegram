package com.example.wazirx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fund {
	private String asset;
	private double free;
	private double locked;
	private double reservedFee;
}
