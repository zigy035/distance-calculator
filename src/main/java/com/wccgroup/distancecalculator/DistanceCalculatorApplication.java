package com.wccgroup.distancecalculator;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wccgroup.distancecalculator.service.PostcodeDataImportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DistanceCalculatorApplication implements CommandLineRunner {

	@Value("${app.should-init-postcode-data:false}")
	private boolean shouldInitPostcodeData;

	private final PostcodeDataImportService postcodeDataImportService;

	public static void main(String[] args) {
		SpringApplication.run(DistanceCalculatorApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		if (shouldInitPostcodeData) {
			final Instant start = Instant.now();
			postcodeDataImportService.importData();
			final Instant finish = Instant.now();

			final long timeElapsed = Duration.between(start, finish).toSeconds();
			log.info("Time elapsed: {} sec", timeElapsed);
		}
	}
}
