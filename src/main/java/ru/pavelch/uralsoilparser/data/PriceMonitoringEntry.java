package ru.pavelch.uralsoilparser.data;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceMonitoringEntry {
    LocalDate start;
    LocalDate end;
    double averagePrice;
}
