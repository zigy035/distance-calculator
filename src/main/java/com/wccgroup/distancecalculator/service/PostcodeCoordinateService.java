package com.wccgroup.distancecalculator.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wccgroup.distancecalculator.configuration.ResourceNotFoundException;
import com.wccgroup.distancecalculator.model.PostcodeCoordinate;
import com.wccgroup.distancecalculator.repository.PostcodeCoordinateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostcodeCoordinateService {

    private final PostcodeCoordinateRepository postcodeCoordinateRepository;

    @Transactional
    public void updateCoordinates(final String postcode,
                                  final BigDecimal latitude,
                                  final BigDecimal longitude) {
        final PostcodeCoordinate postcodeCoordinate = findByPostcode(postcode);
        postcodeCoordinate.setLatitude(latitude);
        postcodeCoordinate.setLongitude(longitude);
        postcodeCoordinateRepository.save(postcodeCoordinate);
    }

    public List<String> getAllPostcodesWithLength(int length) {
        return postcodeCoordinateRepository.getAllPostcodesWithLength(length);
    }

    public PostcodeCoordinate findByPostcode(final String postcode) {
        return postcodeCoordinateRepository.findByPostcode(postcode)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Postcode %s does not exist", postcode)));
    }
}
