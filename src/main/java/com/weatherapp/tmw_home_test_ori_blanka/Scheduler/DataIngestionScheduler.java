package com.weatherapp.tmw_home_test_ori_blanka.Scheduler;

import com.weatherapp.tmw_home_test_ori_blanka.dto.WeatherBatchDTO;
import com.weatherapp.tmw_home_test_ori_blanka.model.BatchData;
import com.weatherapp.tmw_home_test_ori_blanka.repository.BatchRepository;
import com.weatherapp.tmw_home_test_ori_blanka.service.IngestWeatherDataService;
import com.weatherapp.tmw_home_test_ori_blanka.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataIngestionScheduler {
    private final IngestWeatherDataService ingestWeatherDataService;
    private final BatchRepository batchRepository;

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void ingestWeatherData() {
        log.info("Starting weather data ingestion");
        try {
            // Get available batches
            List<WeatherBatchDTO> availableBatches = ingestWeatherDataService.fetchAvailableBatches();
            log.info("Found {} available batches", availableBatches.size());

            for (WeatherBatchDTO batch : availableBatches) {
                if (!batchRepository.existsById(batch.getBatchId())) {
                    BatchData batchData = new BatchData();
                    batchData.setBatchId(batch.getBatchId());
                    batchData.setForecastTime(batch.getForecastTime());
                    ingestWeatherDataService.processBatchWithRetry(batchData);
                }
            }

            // Clean up old data
            ingestWeatherDataService.cleanupOldBatches();
            log.info("Completed weather data ingestion");
        } catch (Exception e) {
            log.error("Error during weather data ingestion", e);
            throw e;
        }
    }
}
