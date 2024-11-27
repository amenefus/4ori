package com.weatherapp.tmw_home_test_ori_blanka.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class WeatherResponseDTO {
    private LocalDateTime forecastTime;
    private double temperature;
    private double precipitationRate;
    private double humidity;
}
