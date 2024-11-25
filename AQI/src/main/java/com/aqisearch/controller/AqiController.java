package com.aqisearch.controller;

import com.aqisearch.model.AqiResponse;
import com.aqisearch.service.AqiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aqi")
public class AqiController {

    private final AqiService aqiService;

    public AqiController(AqiService aqiService) {
        this.aqiService = aqiService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<?> getAQIByCity(@RequestParam String cityName) {
        AqiResponse aqiResponse = aqiService.getAQIData(cityName);
        if (aqiResponse != null) {
            return ResponseEntity.ok(aqiResponse);
        }
        return ResponseEntity.badRequest().body("Invalid city name or data unavailable.");
    }
}
