package com.weatherapp.tmw_home_test_ori_blanka.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class BatchResponseDTO {
    private BatchMetadataDTO metadata;
    private List<WeatherDataDTO> data;
}
