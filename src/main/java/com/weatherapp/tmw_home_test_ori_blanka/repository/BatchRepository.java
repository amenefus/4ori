package com.weatherapp.tmw_home_test_ori_blanka.repository;

import com.weatherapp.tmw_home_test_ori_blanka.model.BatchData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchRepository extends JpaRepository<BatchData, String> {
    List<BatchData> findByStatusOrderByForecastTimeDesc(BatchData.BatchStatus status);
}
