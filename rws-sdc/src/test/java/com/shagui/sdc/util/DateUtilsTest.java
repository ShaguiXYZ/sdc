package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void getDatesBetweenDates() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 5);
        List<Date> expectedDates = Arrays.asList(
                Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(startDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(startDate.plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(startDate.plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(startDate.plusDays(4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        List<Date> actualDates = DateUtils.getDatesBetweenDates(
                Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertEquals(expectedDates, actualDates);
    }

    @Test
    void getLastMounth() {
        int n = 3;
        Date now = new Date();
        LocalDate localleDate = dateToLocalDate(now);
        List<Date> expectedDates = Arrays.asList(
                Date.from(localleDate.minusMonths(0).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(localleDate.minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(localleDate.minusMonths(2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        List<Date> actualDates = DateUtils.getLastMounth(now, n);
        assertEquals(expectedDates, actualDates);
    }

    private LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}