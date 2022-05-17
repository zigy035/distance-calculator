package com.wccgroup.distancecalculator.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.wccgroup.distancecalculator.common.AppConstants;

import lombok.Data;

@Data
public class DistanceRequestDto {

    @NotNull
    @Pattern(regexp = AppConstants.UK_POSTCODE_REGEX)
    private String postcodeFrom;

    @NotNull
    @Pattern(regexp = AppConstants.UK_POSTCODE_REGEX)
    private String postcodeTo;
}
