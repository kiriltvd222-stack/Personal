package ua.kpi.tzyapz.personal;

import java.time.DayOfWeek;
import java.time.LocalDate;

public final class LeaveCalculator {

    public static final int ACCRUAL_PER_MONTH = 2;
    public static final int MAX_ACCRUAL_PER_YEAR = 24;
    public static final int MAX_CARRY_OVER = 5;

    public int accrued(int monthsWorked) {
        if (monthsWorked < 0) {
            throw new IllegalArgumentException("monthsWorked має бути >= 0");
        }
        return Math.min(monthsWorked * ACCRUAL_PER_MONTH, MAX_ACCRUAL_PER_YEAR);
    }

    public int carryOver(int unused) {
        if (unused < 0) {
            throw new IllegalArgumentException("unused має бути >= 0");
        }
        return Math.min(unused, MAX_CARRY_OVER);
    }

    public int durationDays(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Обидві дати обов'язкові");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Дата початку пізніша за дату завершення");
        }
        int days = 0;
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            if (isWorkingDay(d)) {
                days++;
            }
        }
        return days;
    }

    public boolean canRequest(int balance, int duration) {
        int remaining = balance - duration;
        return duration >= 1 && remaining > 0;
    }

    private static boolean isWorkingDay(LocalDate d) {
        DayOfWeek w = d.getDayOfWeek();
        return w != DayOfWeek.SATURDAY && w != DayOfWeek.SUNDAY;
    }
}