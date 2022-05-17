package com.wccgroup.distancecalculator.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdatePostcodeRequestDto {

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
