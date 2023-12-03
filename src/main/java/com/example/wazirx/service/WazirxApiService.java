package com.example.wazirx.service;

import com.example.wazirx.model.Fund;
import com.example.wazirx.model.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WazirxApiService {

	@Value("${wazirx.api.key}")
	private String apiKey;

	@Value("${wazirx.api.secret}")
	private String apiSecret;

	private String baseApiUrl = "https://api.wazirx.com";

	public List<Ticker> dailyTickerStats() {
		log.info("24hrTickerStats");
		String url = baseApiUrl + "/sapi/v1/tickers/24hr";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Ticker>> result =
			restTemplate.exchange(url, HttpMethod.GET, getWazirxHeaders(),
				new ParameterizedTypeReference<List<Ticker>>() {
				});
		log.info("24hrTickerStats: {}", result.getBody());
		return result.getBody();
	}

	public HttpEntity<?> getWazirxHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-API-KEY", apiKey);
		 HttpEntity<?> entity = new HttpEntity<>(headers);
		 return entity;
	}

	public List<Fund> getFund() {
		try {
			log.info("getFund");
			Long timestamp = System.currentTimeMillis();
			String url = UriComponentsBuilder.fromHttpUrl(baseApiUrl + "/sapi/v1/funds")
				.queryParam("timestamp", timestamp)
				.queryParam("signature", getWazirxSignature("timestamp=" + timestamp)).toUriString();

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<Fund>> result =
				restTemplate.exchange(url, HttpMethod.GET, getWazirxHeaders(),
					new ParameterizedTypeReference<List<Fund>>() {
					});
			List<Fund> funds = result.getBody();
			assert funds != null;
			funds = funds.stream()
				.filter(fund -> fund.getFree() > 0)
				.collect(Collectors.toList());
			log.info("Funds: {}", funds);
			return funds;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public String getWazirxSignature(String message) throws UnsupportedEncodingException {
		Mac sha256Mac = HmacUtils.getHmacSha256(apiSecret.getBytes(StandardCharsets.UTF_8));
		return Hex.encodeHexString(sha256Mac.doFinal(message.getBytes("UTF-8")));
	}
}
