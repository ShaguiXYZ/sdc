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

import com.shagui.sdc.util.collector.SdcCollectors;

/**
 * Utility class for date-related operations.
 * <p>
 * Provides methods to obtain the current date, generate lists of dates between
 * two dates,
 * and retrieve lists of dates representing the last N months.
 * </p>
 *
 * <p>
 * This class is not intended to be instantiated.
 * </p>
 *
 * <ul>
 * <li>{@link #now()} - Returns the current date.</li>
 * <li>{@link #datesBetweenDates(Date, Date)} - Returns a list of dates
 * between two given dates (inclusive).</li>
 * <li>{@link #lastMounth(int)} - Returns a list of dates representing the
 * start of the last N months from today.</li>
 * <li>{@link #lastMounth(Date, int)} - Returns a list of dates representing
 * the start of the last N months from a given date.</li>
 * </ul>
 *
 * <p>
 * Note: This class uses the system default time zone for all date conversions.
 * </p>
 */
public class DateUtils {

    private DateUtils() {
    }

    public static Date now() {
        return new Date();
    }

    public static List<Date> datesBetweenDates(Date startDate, Date endDate) {
        return Stream.iterate(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) + 1)
                .map(DateUtils::localDateToDate).toList();
    }

    public static List<Date> lastMounth(int n) {
        return lastMounth(new Date(), n);
    }

    public static List<Date> lastMounth(Date start, int n) {
        // return reverse list if n is negative
        if (n < 0) {
            return _lastMounth(start, -n).collect(SdcCollectors.toReversedList());
        }

        return _lastMounth(start, n).toList();
    }

    private static Stream<Date> _lastMounth(Date start, int n) {
        return IntStream.rangeClosed(0, n - 1)
                .mapToObj(i -> DateUtils.dateToLocalDate(start).minusMonths(i))
                .map(LocalDate::atStartOfDay)
                .map(DateUtils::loacalDateTimeToInstant)
                .map(Date::from);
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