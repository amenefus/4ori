package com.weatherapp.tmw_home_test_ori_blanka.controller;

import com.weatherapp.tmw_home_test_ori_blanka.dto.WeatherResponseDTO;
import com.weatherapp.tmw_home_test_ori_blanka.dto.WeatherSummaryDTO;
import com.weatherapp.tmw_home_test_ori_blanka.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather/")
@RequiredArgsConstructor
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/data")
    public List<WeatherResponseDTO> getWeatherData(@RequestParam double lat, @RequestParam double lon) {
        return weatherService.getWeatherDataByLocation(lat, lon);
    }

    @GetMapping("/summarize")
    public WeatherSummaryDTO getWeatherSummarize(@RequestParam double lat, @RequestParam double lon) {
        return weatherService.getWeatherSummary(lat, lon);
    }
}
