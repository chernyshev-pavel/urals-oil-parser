package ru.pavelch.uralsoilparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pavelch.uralsoilparser.data.PriceMonitoringEntry;
import ru.pavelch.uralsoilparser.data.PricesRange;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Cacheable("price-monitoring-entries")
public class UralsOilApiController {
    public static final String PARAMS_DATE_PATTERN = "dd.MM.yy";

    private final List<PriceMonitoringEntry> oilPriceMonitoringEntries;

    @Autowired
    public UralsOilApiController(List<PriceMonitoringEntry> oilPriceMonitoringEntries) {
        this.oilPriceMonitoringEntries = oilPriceMonitoringEntries;
    }

    @GetMapping("/getPriceByDate")
    public double getPriceByDate(
            @RequestParam @DateTimeFormat(pattern = PARAMS_DATE_PATTERN) LocalDate date
    ) {
        return oilPriceMonitoringEntries.stream()
                .filter(entry -> !date.isBefore(entry.getStart()) && date.isBefore(entry.getStart().plusDays(1)))
                .mapToDouble(PriceMonitoringEntry::getAveragePrice)
                .findAny()
                .orElse(0);
    }

    @GetMapping("/getAveragePriceByDateRange")
    public double getAveragePriceByDateRange(
            @RequestParam @DateTimeFormat(pattern = PARAMS_DATE_PATTERN) LocalDate startInclusiveDate,
            @RequestParam @DateTimeFormat(pattern = PARAMS_DATE_PATTERN) LocalDate endExclusiveDate
    ) {
        return oilPriceMonitoringEntries.stream()
                .filter(entry -> entry.getStart().isBefore(endExclusiveDate) && entry.getEnd().isAfter(startInclusiveDate.minusDays(1)))
                .mapToDouble(PriceMonitoringEntry::getAveragePrice)
                .average()
                .orElse(0);
    }

    @GetMapping("/getPricesRangeByDateRange")
    public PricesRange getPricesRangeByDateRange(
            @RequestParam @DateTimeFormat(pattern = PARAMS_DATE_PATTERN) LocalDate startInclusiveDate,
            @RequestParam @DateTimeFormat(pattern = PARAMS_DATE_PATTERN) LocalDate endExclusiveDate
    ) {
        List<Double> prices = oilPriceMonitoringEntries.stream()
                .filter(entry -> entry.getStart().isBefore(endExclusiveDate) && entry.getEnd().isAfter(startInclusiveDate.minusDays(1)))
                .map(PriceMonitoringEntry::getAveragePrice)
                .sorted()
                .collect(Collectors.toList());

        return prices.size() == 0 ? new PricesRange(0, 0) : new PricesRange(prices.get(0), prices.get(prices.size() - 1));
    }

    @GetMapping("/getAllEntries")
    public List<PriceMonitoringEntry> getAllEntries() {
        return oilPriceMonitoringEntries;
    }

}
