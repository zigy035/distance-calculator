package com.wccgroup.distancecalculator;

import com.wccgroup.distancecalculator.client.PostcodeCoordinateClient;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import static com.wccgroup.distancecalculator.common.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceCalculationControllerTest {

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
	@Sql(scripts = INIT_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = CLEANUP_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testRequestDistanceCalculationAndReturnOK() {
		final String postcodeFrom = "AB10 1XG";
		final String postcodeTo = "AB21 0AL";

		final ValidatableResponse response =
				postcodeCoordinateClient.post(postcodeFrom, postcodeTo, port, USER_AUTHORIZATION);

		assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.extract().path("from.postcode").toString()).isEqualTo(postcodeFrom);
		assertThat(response.extract().path("to.postcode").toString()).isEqualTo(postcodeTo);
		assertThat(response.extract().path("distance").toString()).isEqualTo("13.51");
	}

	@Test
	@Sql(scripts = INIT_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = CLEANUP_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testRequestDistanceCalculationAndReturnUnauthorized() {
		final String postcodeFrom = "AB10 1XG";
		final String postcodeTo = "AB21 0AL";

		final ValidatableResponse response =
				postcodeCoordinateClient.post(postcodeFrom, postcodeTo, port, "WrongAuth");

		assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	@Sql(scripts = INIT_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = CLEANUP_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testRequestDistanceCalculationAndReturnBadRequest() {
		final String postcodeFrom = "AB101XG";
		final String postcodeTo = "AB21 0AL";

		final ValidatableResponse response =
				postcodeCoordinateClient.post(postcodeFrom, postcodeTo, port, USER_AUTHORIZATION);

		assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@Sql(scripts = INIT_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = CLEANUP_DATA_SCRIPT, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testRequestDistanceCalculationAndReturnNotFound() {
		final String postcodeFrom = "ABCD EFG";
		final String postcodeTo = "AB21 0AL";

		final ValidatableResponse response =
				postcodeCoordinateClient.post(postcodeFrom, postcodeTo, port, USER_AUTHORIZATION);

		assertThat(response.extract().statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

}
