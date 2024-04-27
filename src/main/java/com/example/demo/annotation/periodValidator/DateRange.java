package com.example.demo.annotation.periodValidator;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@ValidDateRange
public class DateRange {
    private LocalDate fromDate;
    private LocalDate toDate;
}
