package ru.pavelch.uralsoilparser;

import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.pavelch.uralsoilparser.data.PriceMonitoringEntry;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCaching
public class UralsOilConfiguration {

    @Bean
    @SneakyThrows
    public List<PriceMonitoringEntry> oilPriceMonitoringEntries(@Value("${oil-prices-csv-url}") String url) {
        List<PriceMonitoringEntry> entries = new ArrayList<>();

        try (CSVParser parser = CSVParser.parse(new URL(url), StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
            for (CSVRecord record : parser.getRecords()) {
                if (record.getRecordNumber() == 1) {
                    continue; // skip header comment
                }

                try {
                    entries.add(new PriceMonitoringEntry(
                            parseDate(record.get(0)),
                            parseDate(record.get(1)),
                            Double.parseDouble(record.get(2).replace(',', '.'))
                    ));
                } catch (ParseException | NumberFormatException e) {
                    throw new ParseException(String.format(
                            "Could not parse \"%s\": %s",
                            record.toString(),
                            e.getMessage()
                    ), 0);
                }
            }
        }

        return entries;
    }

    public static LocalDate parseDate(String dateAsString) throws ParseException {
        String[] split = dateAsString.split("\\.");
        if (split.length != 3) {
            throw new ParseException("Invalid format: " + dateAsString, 0);
        }

        try {
            return LocalDate.of(
                    2000 + Integer.parseInt(split[2]),
                    Arrays.asList(
                            "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"
                    ).indexOf(split[1]) + 1,
                    Integer.parseInt(split[0])
            );
        } catch (NumberFormatException e) {
            throw new ParseException("Can't parse numbers", 0);
        } catch (DateTimeException e) {
            throw new DateTimeException(e.getMessage() + ". Provided month: " + split[1], e.getCause());
        }
    }

}
