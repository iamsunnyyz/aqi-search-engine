package com.aqisearch.service;

import com.aqisearch.model.AqiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class AqiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AqiService.class);

    @Value("${aqi.api.url}")
    private String apiUrl;

    @Value("${aqi.api.token}")
    private String apiToken;

    @Cacheable(value = "aqiCache", key = "#cityName", unless = "#result == null")
    public AqiResponse getAQIData(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            LOGGER.error("City name cannot be null or empty.");
            throw new IllegalArgumentException("City name cannot be null or empty.");
        }

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Construct the API URL with the token
            String url = apiUrl.replace("{city}", cityName.trim()) + apiToken;
            LOGGER.info("Fetching AQI data for city: {}", cityName);

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && "ok".equals(response.get("status"))) {
                Map<String, Object> data = (Map<String, Object>) response.get("data");
                int aqi = (int) data.get("aqi");
                String mainPollutant = data.get("dominentpol") != null ? data.get("dominentpol").toString() : "Unknown";

                AqiResponse aqiResponse = new AqiResponse();
                aqiResponse.setCityName(cityName);
                aqiResponse.setAqi(aqi);
                aqiResponse.setMainPollutant(mainPollutant);
                aqiResponse.setHealthAdvice(getHealthAdvice(aqi));

                LOGGER.info("AQI data fetched successfully for city: {}", cityName);
                return aqiResponse;
            } else {
                LOGGER.error("Failed to fetch AQI data for city: {}. Response status: {}", cityName, response.get("status"));
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching AQI data for city: {}. Error: {}", cityName, e.getMessage(), e);
        }

        return null;
    }

    private String getHealthAdvice(int aqi) {
        if (aqi <= 50) return "Good - No health implications.";
        if (aqi <= 100) return "Moderate - Acceptable quality.";
        if (aqi <= 200) return "Unhealthy for Sensitive Groups.";
        if (aqi <= 300) return "Unhealthy - Everyone may experience health effects.";
        if (aqi <= 400) return "Very Unhealthy - Health alert for all.";
        return "Hazardous - Serious health effects for all.";
    }
}
