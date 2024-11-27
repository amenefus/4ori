package com.weatherapp.tmw_home_test_ori_blanka.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BatchMetadataDTO {
    private String batchId;
    private int count;
    private int page;
    private int totalItems;
    private int totalPages;
}
