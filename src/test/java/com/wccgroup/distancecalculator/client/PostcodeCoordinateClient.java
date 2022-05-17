package com.wccgroup.distancecalculator.client;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;

@Component
public class PostcodeCoordinateClient {

    public ValidatableResponse post(String postcodeFrom, String postcodeTo,
                                    final int port, String authHeader) {
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("postcodeFrom", postcodeFrom);
        builder.add("postcodeTo", postcodeTo);

        return RestAssured.given()
                .port(port)
                .when().contentType(ContentType.JSON)
                .header(new Header(HttpHeaders.AUTHORIZATION, authHeader))
                .body(builder.build().toString())
                .post("/api/calculate-distance")
                .then();
    }

    public ValidatableResponse put(String postcode, BigDecimal latitude, BigDecimal longitude,
                                   final int port, String authHeader) {
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("latitude", latitude);
        builder.add("longitude", longitude);

        return RestAssured.given()
                .port(port)
                .when().contentType(ContentType.JSON)
                .header(new Header(HttpHeaders.AUTHORIZATION, authHeader))
                .body(builder.build().toString())
                .put("/api/postcode-coordinate/" + postcode)
                .then();
    }

    public ValidatableResponse get(String postcode, final int port, String authHeader) {
        return RestAssured.given()
                .port(port)
                .when().contentType(ContentType.JSON)
                .header(new Header(HttpHeaders.AUTHORIZATION, authHeader))
                .get("/api/postcode-coordinate/" + postcode)
                .then();
    }
}
