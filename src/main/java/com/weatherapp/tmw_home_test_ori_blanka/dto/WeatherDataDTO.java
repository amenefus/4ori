package com.weatherapp.tmw_home_test_ori_blanka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class WeatherDataDTO {
    private double latitude;
    private double longitude;
    private double temperature;
    private double humidity;

    @JsonProperty("precipitation_rate")
    private double precipitationRate;
}
