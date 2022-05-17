package com.wccgroup.distancecalculator.converter;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.wccgroup.distancecalculator.dto.DistanceResponseDto;
import com.wccgroup.distancecalculator.dto.PostcodeCoordinateDto;

@Component
public class DistanceConverter {

    public DistanceResponseDto toResponse(String fromPostcode, String fromLatitude, String fromLongitude,
                                          String toPostcode, String toLatitude, String toLongitude,
                                          BigDecimal distance, String unit) {

        PostcodeCoordinateDto fromDto = PostcodeCoordinateDto.builder()
                .postcode(fromPostcode)
                .latitude(fromLatitude)
                .longitude(fromLongitude)
                .build();

        PostcodeCoordinateDto toDto = PostcodeCoordinateDto.builder()
                .postcode(toPostcode)
                .latitude(toLatitude)
                .longitude(toLongitude)
                .build();

        return DistanceResponseDto.builder()
                .from(fromDto)
                .to(toDto)
                .distance(distance)
                .unit(unit)
                .build();
    }
}
