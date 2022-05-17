package com.wccgroup.distancecalculator.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wccgroup.distancecalculator.dto.DistanceRequestDto;
import com.wccgroup.distancecalculator.dto.DistanceResponseDto;
import com.wccgroup.distancecalculator.service.DistanceCalculationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/calculate-distance")
@RequiredArgsConstructor
public class DistanceCalculationController {

    private final DistanceCalculationService distanceCalculationService;

    @PostMapping
    public DistanceResponseDto calculateDistance(@NotNull @Valid @RequestBody DistanceRequestDto request) {
        log.info("Request for distance calculation: {}", request);

        return distanceCalculationService.calculateDistance(request.getPostcodeFrom(), request.getPostcodeTo());
    }

}
