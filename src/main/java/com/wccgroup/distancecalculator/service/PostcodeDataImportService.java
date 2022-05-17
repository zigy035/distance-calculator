package com.wccgroup.distancecalculator.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostcodeDataImportService {

    private static final int BATCH_SIZE = 1000;
    private static final String UK_POSTCODES_CSV_FILE_PATH = "/csv/ukpostcodes.csv";
    private static final char SEPARATOR = ',';

    private final JdbcTemplate jdbcTemplate;

    public void importData() throws IOException, CsvValidationException {
        final List<List<String>> records = new ArrayList<>();

        InputStream in = getClass().getResourceAsStream(UK_POSTCODES_CSV_FILE_PATH);
        assert in != null;
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        try (final CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(new CSVParserBuilder().withSeparator(SEPARATOR).build())
                .build()) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }

        //remove header columns
        records.remove(0);

        jdbcTemplate.update("DELETE FROM postcode_coordinate WHERE id > 0");

        int batchCount = records.size() / BATCH_SIZE + ((records.size() % BATCH_SIZE == 0) ? 0 : 1);
        for (int batchNo = 0; batchNo < batchCount; batchNo++) {

            log.info("Inserting batch no {}...", batchNo);

            final List<List<String>> recordBatch = records.subList(batchNo * BATCH_SIZE, Math.min((batchNo + 1) * 1000, records.size()));

            jdbcTemplate.batchUpdate("INSERT INTO postcode_coordinate VALUES (?, ?, ?, ?)", new BatchPreparedStatementSetter() {

                @Override
                public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                    try {
                        ps.setLong(1, Long.parseLong(recordBatch.get(i).get(0)));
                        ps.setString(2, recordBatch.get(i).get(1));
                        ps.setBigDecimal(3, new BigDecimal(recordBatch.get(i).get(2)));
                        ps.setBigDecimal(4, new BigDecimal(recordBatch.get(i).get(3)));
                    } catch (Exception e) {
                        //NOTE: CSV file contains some postcode with incorrect
                        // latitude and longitude, so we're going to set zero as default
                        log.error(e.getMessage());
                        ps.setBigDecimal(3, BigDecimal.ZERO);
                        ps.setBigDecimal(4, BigDecimal.ZERO);
                    }
                }

                @Override
                public int getBatchSize() {
                    return recordBatch.size();
                }
            });
        }

    }
}
