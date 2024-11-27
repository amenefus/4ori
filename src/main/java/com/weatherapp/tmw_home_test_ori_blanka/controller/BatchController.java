package com.weatherapp.tmw_home_test_ori_blanka.controller;

import com.weatherapp.tmw_home_test_ori_blanka.model.BatchData;
import com.weatherapp.tmw_home_test_ori_blanka.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/batches")
@RequiredArgsConstructor
public class BatchController {
    @Autowired
    private final WeatherService weatherService;

    @GetMapping
    public List<BatchData> getBatches() {
        return weatherService.getAllBatches();
    }
}