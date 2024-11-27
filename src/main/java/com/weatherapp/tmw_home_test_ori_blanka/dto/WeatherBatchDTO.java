package com.weatherapp.tmw_home_test_ori_blanka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class WeatherBatchDTO {
    @JsonProperty("batch_id")
    private String batchId;

    @JsonProperty("forecast_time")
    private LocalDateTime forecastTime;
}
