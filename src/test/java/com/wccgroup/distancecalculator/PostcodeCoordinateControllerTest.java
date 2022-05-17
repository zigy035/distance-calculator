package com.wccgroup.distancecalculator;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import com.wccgroup.distancecalculator.client.PostcodeCoordinateClient;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.ValidatableResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
public class PostcodeCoordinateControllerTest {

    private static final String ADMIN_AUTHORIZATION = "Basic YWRtaW46YWRtaW4=";

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostcodeCoordinateClient postcodeCoordinateClient;

    @BeforeEach
    public void before() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test
    public void testRequestUpdateCoordinatesAndReturnOK() {
        final String postcode = "BN91 9AA";
        final BigDecimal latitude = new BigDecimal("12.345678");
        final BigDecimal longitude = new BigDecimal("12.456789");

        postcodeCoordinateClient.put(postcode, latitude, longitude, port, ADMIN_AUTHORIZATION);

        final ValidatableResponse response = postcodeCoordinateClient.get(postcode, port, ADMIN_AUTHORIZATION);

        assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.extract().path("postcode").toString()).isEqualTo(postcode);
        assertThat(response.extract().path("latitude").toString()).isEqualTo(latitude.toString());
        assertThat(response.extract().path("longitude").toString()).isEqualTo(longitude.toString());
    }

    @Test
    public void testRequestUpdateCoordinatesAndReturnUnauthorized() {
        final String postcode = "BN91 9AA";
        final BigDecimal latitude = new BigDecimal("12.345678");
        final BigDecimal longitude = new BigDecimal("12.456789");

        final ValidatableResponse response = postcodeCoordinateClient.put(postcode, latitude, longitude, port, "WrongAuth");

        assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
