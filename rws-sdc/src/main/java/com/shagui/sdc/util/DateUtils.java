package com.shagui.sdc.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DateUtils {

    private DateUtils() {
    }

    public static List<Date> getDatesBetweenDates(Date startDate, Date endDate) {
        return Stream.iterate(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) + 1)
                .map(DateUtils::localDateToDate).toList();
    }

    public static List<Date> getLastMounth(int n) {
        return getLastMounth(new Date(), n);
    }

    public static List<Date> getLastMounth(Date start, int n) {
        return IntStream.rangeClosed(0, n - 1)
                .mapToObj(i -> DateUtils.dateToLocalDate(start).minusMonths(i))
                .map(LocalDate::atStartOfDay)
                .map(DateUtils::loacalDateTimeToInstant)
                .map(Date::from).toList();
    }

    private static LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static Instant loacalDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    private static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}