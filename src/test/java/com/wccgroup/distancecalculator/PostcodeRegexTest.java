package com.wccgroup.distancecalculator;

import static com.wccgroup.distancecalculator.common.AppConstants.UK_POSTCODE_REGEX;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wccgroup.distancecalculator.service.PostcodeCoordinateService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(value = "classpath:application-test.properties")
public class PostcodeRegexTest {

    private static final Pattern POSTCODE_PATTERN = Pattern.compile(UK_POSTCODE_REGEX);

    @Autowired
    private PostcodeCoordinateService postcodeCoordinateService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegexForPostcodesWithLengthOfSix() {
        final List<String> postcodes = postcodeCoordinateService.getAllPostcodesWithLength(6);

        assertThat(postcodes.size()).isGreaterThan(0);
        postcodes.forEach(postcode -> assertThat(POSTCODE_PATTERN.matcher(postcode).matches()).isTrue());
    }

    @Test
    public void testRegexForPostcodesWithLengthOfSeven() {
        final List<String> postcodes = postcodeCoordinateService.getAllPostcodesWithLength(7);

        assertThat(postcodes.size()).isGreaterThan(0);
        postcodes.forEach(postcode -> assertThat(POSTCODE_PATTERN.matcher(postcode).matches()).isTrue());
    }

    @Test
    public void testRegexForPostcodesWithLengthOfEight() {
        final List<String> postcodes = postcodeCoordinateService.getAllPostcodesWithLength(8);

        assertThat(postcodes.size()).isGreaterThan(0);
        postcodes.forEach(postcode -> assertThat(POSTCODE_PATTERN.matcher(postcode).matches()).isTrue());
    }

}
