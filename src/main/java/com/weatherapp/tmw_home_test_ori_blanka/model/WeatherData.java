package com.weatherapp.tmw_home_test_ori_blanka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "weather_data")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;
    private double temperature;
    private double humidity;
    private double precipitationRate;
    private LocalDateTime forecastTime;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private BatchData batchData;
}
