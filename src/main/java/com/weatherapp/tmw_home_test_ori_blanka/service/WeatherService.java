package com.weatherapp.tmw_home_test_ori_blanka.service;

import com.weatherapp.tmw_home_test_ori_blanka.dto.WeatherResponseDTO;
import com.weatherapp.tmw_home_test_ori_blanka.dto.WeatherSummaryDTO;
import com.weatherapp.tmw_home_test_ori_blanka.dto.WeatherSummaryDTO.WeatherMetrics;
import com.weatherapp.tmw_home_test_ori_blanka.model.BatchData;
import com.weatherapp.tmw_home_test_ori_blanka.model.WeatherData;
import com.weatherapp.tmw_home_test_ori_blanka.repository.BatchRepository;
import com.weatherapp.tmw_home_test_ori_blanka.repository.WeatherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final BatchRepository batchRepository;

    /**
     * Get weather data by latitude and longitude.
     *
     * @param latitude  Latitude of the location.
     * @param longitude Longitude of the location.
     * @return List of weather data for the given location.
     */
    public List<WeatherResponseDTO> getWeatherDataByLocation(double latitude, double longitude) {
        return weatherRepository.findByLatitudeAndLongitude(latitude, longitude)
                .stream().map(this::mapToResponse)
                .toList();
    }

    /**
     * Get weather data summary (max, min, avg) for a specific location.
     *
     * @param latitude  Latitude of the location.
     * @param longitude Longitude of the location.
     * @return A summary object containing max, min, and avg for temperature, humidity, and precipitation rate.
     */

    @Transactional
    public WeatherSummaryDTO getWeatherSummary(double latitude, double longitude) {
        WeatherSummaryDTO summary = new WeatherSummaryDTO();
        List<WeatherData> weatherData = weatherRepository.findByLatitudeAndLongitude(latitude, longitude);

        DoubleSummaryStatistics tempStats = weatherData.stream()
                .mapToDouble(WeatherData::getTemperature)
                .summaryStatistics();

        DoubleSummaryStatistics humidityStats = weatherData.stream()
                .mapToDouble(WeatherData::getHumidity)
                .summaryStatistics();

        DoubleSummaryStatistics precipStats = weatherData.stream()
                .mapToDouble(WeatherData::getPrecipitationRate)
                .summaryStatistics();

        WeatherMetrics max = new WeatherMetrics();
        max.setTemperature(tempStats.getMax());
        max.setPrecipitationRate(precipStats.getMax());
        max.setHumidity(humidityStats.getMax());
        summary.setMax(max);

        WeatherMetrics min = new WeatherMetrics();
        min.setTemperature(tempStats.getMin());
        min.setPrecipitationRate(precipStats.getMin());
        min.setHumidity(humidityStats.getMin());
        summary.setMin(min);

        WeatherMetrics avg = new WeatherMetrics();
        avg.setTemperature(tempStats.getAverage());
        avg.setPrecipitationRate(precipStats.getAverage());
        avg.setHumidity(humidityStats.getAverage());
        summary.setAvg(avg);

        return summary;
    }

    public List<BatchData> getAllBatches() {
        return batchRepository.findAll();
    }

    private WeatherResponseDTO mapToResponse(WeatherData data) {
        WeatherResponseDTO response = new WeatherResponseDTO();
        response.setForecastTime(data.getForecastTime());
        response.setTemperature(data.getTemperature());
        response.setPrecipitationRate(data.getPrecipitationRate());
        response.setHumidity(data.getHumidity());
        return response;
    }
}
