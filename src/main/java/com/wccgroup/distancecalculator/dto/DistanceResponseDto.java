package com.wccgroup.distancecalculator.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistanceResponseDto {

    private PostcodeCoordinateDto from;
    private PostcodeCoordinateDto to;
    private BigDecimal distance;
    private String unit;
}
