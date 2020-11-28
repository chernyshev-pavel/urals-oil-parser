package ru.pavelch.uralsoilparser;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pavelch.uralsoilparser.data.PriceMonitoringEntry;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ParserTest {

    @Autowired
    private List<PriceMonitoringEntry> oilPriceMonitoringEntries;

    @Test
    @SneakyThrows
    void parseDate() {
        Assertions.assertEquals(
                LocalDate.of(2020, 6, 15),
                UralsOilConfiguration.parseDate("15.июн.20")
        );
    }

    @Test
    void oilPriceMonitoringEntries() {
        Assertions.assertNotEquals(0, oilPriceMonitoringEntries.size());
    }


}