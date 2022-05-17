package com.wccgroup.distancecalculator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostcodeCoordinateDto {

    private String postcode;
    private String latitude;
    private String longitude;
}
