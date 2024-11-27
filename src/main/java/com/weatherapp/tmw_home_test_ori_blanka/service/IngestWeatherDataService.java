package com.weatherapp.tmw_home_test_ori_blanka.service;

import com.weatherapp.tmw_home_test_ori_blanka.dto.BatchResponseDTO;
import com.weatherapp.tmw_home_test_ori_blanka.dto.WeatherBatchDTO;
import com.weatherapp.tmw_home_test_ori_blanka.model.BatchData;
import com.weatherapp.tmw_home_test_ori_blanka.model.WeatherData;
import com.weatherapp.tmw_home_test_ori_blanka.repository.BatchRepository;
import com.weatherapp.tmw_home_test_ori_blanka.repository.WeatherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngestWeatherDataService {
    private final WeatherRepository weatherRepository;
    private final BatchRepository batchRepository;
    private final WebClient webClient;

    public List<WeatherBatchDTO> fetchAvailableBatches() {
        return webClient.get()
                .uri("/batches")
                .retrieve()
                .bodyToFlux(WeatherBatchDTO.class)
                .collectList()
                .block();
    }

    @Retryable(value = Exception.class, maxAttempts = 3)
    public void processBatchWithRetry(BatchData batchData) {
        log.info("Processing batch: {}", batchData.getBatchId());
        try {
            processNewBatch(batchData);
        } catch (Exception e) {
            log.error("Error processing batch {}", batchData.getBatchId(), e);
            throw e;
        }
    }

    @Transactional
    protected void processNewBatch(BatchData batchData) {
        batchData.setStartIngestTime(now());
        batchData.setStatus(BatchData.BatchStatus.RUNNING);
        batchRepository.save(batchData);
        log.info("Saving batch {}", batchData.getBatchId());

        try {
            int currentPage = 0;
            BatchResponseDTO response;
            int totalRows = 0;

            do {
                response = fetchBatchPage(batchData.getBatchId(), currentPage);
                saveBatchData(response, batchData);
                totalRows += response.getData().size();
                currentPage++;
            } while (currentPage < response.getMetadata().getTotalPages());

            batchData.setStatus(BatchData.BatchStatus.ACTIVE);
            batchData.setNumberOfRows(totalRows);
            batchData.setEndIngestTime(now());

        } catch (Exception e) {
            batchData.setStatus(BatchData.BatchStatus.INACTIVE);
        }

        batchRepository.save(batchData);
    }

    @Retryable(value = Exception.class, maxAttempts = 3)
    private BatchResponseDTO fetchBatchPage(String batchId, int page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/batches/{batchId}")
                        .queryParam("page", page)
                        .build(batchId))
                .retrieve()
                .bodyToMono(BatchResponseDTO.class)
                .block();
    }

    private void saveBatchData(BatchResponseDTO response, BatchData batchData) {
        List<WeatherData> weatherDataList = response.getData().stream()
                .map(dto -> {
                    WeatherData data = new WeatherData();
                    data.setLatitude(dto.getLatitude());
                    data.setLongitude(dto.getLongitude());
                    data.setTemperature(dto.getTemperature());
                    data.setHumidity(dto.getHumidity());
                    data.setPrecipitationRate(dto.getPrecipitationRate());
                    data.setForecastTime(batchData.getStartIngestTime());
                    data.setBatchData(batchData);
                    return data;
                })
                .toList();

        weatherRepository.saveAll(weatherDataList);
    }

    public void cleanupOldBatches() {
        List<BatchData> activeBatches = batchRepository.findByStatusOrderByForecastTimeDesc(BatchData.BatchStatus.ACTIVE);
        if (activeBatches.size() > 3) {
            activeBatches.stream()
                    .skip(3) // Keep the first 3
                    .forEach(batch -> {
                        batch.setStatus(BatchData.BatchStatus.INACTIVE);
                        batchRepository.save(batch);
                    });
        }
    }
}
