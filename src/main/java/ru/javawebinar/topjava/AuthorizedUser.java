package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {
    private static AtomicInteger id = new AtomicInteger(1);
    private static LocalDate dateFrom = LocalDate.MIN;
    private static LocalDate dateTo = LocalDate.MAX;
    private static LocalTime timeFrom = LocalTime.MIN;
    private static LocalTime timeTo = LocalTime.MAX;

    public static LocalDate getDateFrom() {
        return dateFrom;
    }

    public static void setDateFrom(LocalDate dateFrom) {
        AuthorizedUser.dateFrom = dateFrom;
    }

    public static LocalDate getDateTo() {
        return dateTo;
    }

    public static void setDateTo(LocalDate dateTo) {
        AuthorizedUser.dateTo = dateTo;
    }

    public static LocalTime getTimeFrom() {
        return timeFrom;
    }

    public static void setTimeFrom(LocalTime timeFrom) {
        AuthorizedUser.timeFrom = timeFrom;
    }

    public static LocalTime getTimeTo() {
        return timeTo;
    }

    public static void setTimeTo(LocalTime timeTo) {
        AuthorizedUser.timeTo = timeTo;
    }

    public static void setMealFilter(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {
        AuthorizedUser.dateFrom = dateFrom;
        AuthorizedUser.dateTo = dateTo;
        AuthorizedUser.timeFrom = timeFrom;
        AuthorizedUser.timeTo = timeTo;
    }

    public static int id() {
        return id.get();
    }

    public static void setId(int id) {
        AuthorizedUser.id.set(id);
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
