package com.weatherapp.tmw_home_test_ori_blanka.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "batch_data")
public class BatchData {
    @Id
    private String batchId;
    private LocalDateTime forecastTime;
    private int numberOfRows;
    private LocalDateTime startIngestTime;
    private LocalDateTime endIngestTime;

    @Enumerated(EnumType.STRING)
    private BatchStatus status;

    public enum BatchStatus {
        RUNNING,
        ACTIVE,
        INACTIVE
    }
}
