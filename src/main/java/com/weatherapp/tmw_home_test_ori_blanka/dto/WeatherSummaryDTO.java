package com.weatherapp.tmw_home_test_ori_blanka.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class WeatherSummaryDTO {
    private WeatherMetrics max;
    private WeatherMetrics min;
    private WeatherMetrics avg;

    @Data
    @Getter
    @Setter
    public static class WeatherMetrics {
        private double temperature;
        private double precipitationRate;
        private double humidity;
    }
}
