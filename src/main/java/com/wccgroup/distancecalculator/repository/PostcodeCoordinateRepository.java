package com.wccgroup.distancecalculator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wccgroup.distancecalculator.model.PostcodeCoordinate;

public interface PostcodeCoordinateRepository extends JpaRepository<PostcodeCoordinate, Long> {

    Optional<PostcodeCoordinate> findByPostcode(String postcode);

    @Query(value = "SELECT p.postcode FROM PostcodeCoordinate p WHERE LENGTH(p.postcode) = :length")
    List<String> getAllPostcodesWithLength(@Param("length") int length);
}
