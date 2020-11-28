package ru.pavelch.uralsoilparser.data;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PricesRange {
    double min, max;
}
