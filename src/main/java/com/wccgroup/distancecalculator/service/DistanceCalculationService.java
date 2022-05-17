package com.wccgroup.distancecalculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.wccgroup.distancecalculator.converter.DistanceConverter;
import com.wccgroup.distancecalculator.dto.DistanceResponseDto;
import com.wccgroup.distancecalculator.model.PostcodeCoordinate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistanceCalculationService {

    private static final double EARTH_RADIUS = 6371; // radius in kilometers
    private static final String DEFAULT_UNIT = "km";

    private final PostcodeCoordinateService postcodeCoordinateService;
    private final DistanceConverter distanceConverter;

    public DistanceResponseDto calculateDistance(String postcodeFrom, String postcodeTo) {
        final PostcodeCoordinate from = postcodeCoordinateService.findByPostcode(postcodeFrom);
        final PostcodeCoordinate to = postcodeCoordinateService.findByPostcode(postcodeTo);

        return distanceConverter.toResponse(postcodeFrom,
                                            convertLatitudeToDMS(from.getLatitude()),
                                            convertLongitudeToDMS(from.getLongitude()),
                                            postcodeTo,
                                            convertLatitudeToDMS(to.getLatitude()),
                                            convertLongitudeToDMS(to.getLongitude()),
                                            calculateDistance(from, to), DEFAULT_UNIT);
    }

    /**
     * Calculates a straight-line distance by using Haversine formula
     */
    private BigDecimal calculateDistance(PostcodeCoordinate from, PostcodeCoordinate to) {
        BigDecimal latitudeFrom = from.getLatitude();
        BigDecimal longitudeFrom = from.getLongitude();
        BigDecimal latitudeTo = to.getLatitude();
        BigDecimal longitudeTo = to.getLongitude();

        double lon1Radians = Math.toRadians(longitudeFrom.doubleValue());
        double lon2Radians = Math.toRadians(longitudeTo.doubleValue());
        double lat1Radians = Math.toRadians(latitudeFrom.doubleValue());
        double lat2Radians = Math.toRadians(latitudeTo.doubleValue());
        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return new BigDecimal(EARTH_RADIUS * c).setScale(2, RoundingMode.CEILING);
    }

    private String convertLatitudeToDMS(BigDecimal lat) {
        var latDegrees = toDegreesMinutesAndSeconds(lat.doubleValue());
        var direction = lat.signum() >= 0 ? "N" : "S";

        return latDegrees + " " + direction;
    }

    private String convertLongitudeToDMS(BigDecimal lng) {
        var lngDegrees = toDegreesMinutesAndSeconds(lng.doubleValue());
        var direction = lng.signum() >= 0 ? "E" : "W";

        return lngDegrees + " " + direction;
    }

    private String toDegreesMinutesAndSeconds(double coordinate) {
        var absolute = Math.abs(coordinate);
        var degrees = Math.floor(absolute);
        var minutesNotTruncated = (absolute - degrees) * 60;
        var minutes = Math.floor(minutesNotTruncated);
        var seconds = Math.floor((minutesNotTruncated - minutes) * 60);

        return (int) degrees + "° " + (int) minutes + "' " + (int) seconds + "''";
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }

    private double square(double x) {
        return x * x;
    }

}
