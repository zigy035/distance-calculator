package com.wccgroup.distancecalculator.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wccgroup.distancecalculator.dto.UpdatePostcodeRequestDto;
import com.wccgroup.distancecalculator.model.PostcodeCoordinate;
import com.wccgroup.distancecalculator.service.PostcodeCoordinateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/postcode-coordinate")
@RequiredArgsConstructor
public class PostcodeCoordinateController {

    private final PostcodeCoordinateService postcodeCoordinateService;

    @PutMapping("/{postcode}")
    public ResponseEntity<?> updatePostcodeCoordinates(@NotNull @PathVariable("postcode") String postcode, @NotNull @Valid @RequestBody UpdatePostcodeRequestDto request) {
        log.info("Request for updating postcode {} with coordinates: {}", postcode, request);

        postcodeCoordinateService.updateCoordinates(postcode, request.getLatitude(), request.getLongitude());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postcode}")
    public PostcodeCoordinate getPostcodeCoordinate(@NotNull @PathVariable("postcode") String postcode) {
        log.info("Request for postcode coordinates with postcode: {}", postcode);

        return postcodeCoordinateService.findByPostcode(postcode);
    }
}
