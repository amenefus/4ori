package com.weatherapp.tmw_home_test_ori_blanka.repository;

import com.weatherapp.tmw_home_test_ori_blanka.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherData, Long> {

    @Query("SELECT weather FROM WeatherData weather WHERE weather.latitude = :lat AND weather.longitude = :lon")
    List<WeatherData> findByLatitudeAndLongitude(Double lat, Double lon);
}
