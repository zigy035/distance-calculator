package com.wccgroup.distancecalculator.model;

import static com.wccgroup.distancecalculator.common.AppConstants.UK_POSTCODE_REGEX;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Entity
@Table(name = "postcode_coordinate")
public class PostcodeCoordinate {

    @Id
    private Long id;

    @NotNull
    @Pattern(regexp = UK_POSTCODE_REGEX)
    private String postcode;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;
}
