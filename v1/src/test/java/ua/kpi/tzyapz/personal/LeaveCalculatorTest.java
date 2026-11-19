package ua.kpi.tzyapz.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Юніт-тести модуля розрахунку відпусток LeaveCalculator (BL-10 … BL-40). */
@DisplayName("LeaveCalculator — розрахунок відпусток (BL-10 … BL-40)")
class LeaveCalculatorTest {

    private LeaveCalculator leave;

    @BeforeEach
    void setUp() {
        leave = new LeaveCalculator();
    }

    // --- BL-10: accrued(monthsWorked) ---

    @ParameterizedTest(name = "BL-10 [TC-01..TC-04]: accrued({0}) = {1}")
    @CsvSource({
        "0, 0",
        "1, 2",
        "6, 12",
        "12, 24",
        "13, 24",
        "15, 24"
    })
    @DisplayName("TC-01..TC-04 [BL-10]: нарахування днів відпустки за місяці з урахуванням річного ліміту 24")
    void accruedCalculatesWithYearlyCap(int monthsWorked, int expectedDays) {
        assertEquals(expectedDays, leave.accrued(monthsWorked));
    }

    @Test
    @DisplayName("TC-05 [BL-10]: від'ємна кількість місяців викликає IllegalArgumentException")
    void accruedNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> leave.accrued(-1));
    }

    // --- BL-20: carryOver(unused) ---

    @ParameterizedTest(name = "BL-20 [TC-06..TC-09]: carryOver({0}) = {1}")
    @CsvSource({
        "0, 0",
        "3, 3",
        "5, 5",
        "6, 5",
        "10, 5"
    })
    @DisplayName("TC-06..TC-09 [BL-20]: перенесення залишку відпустки обмежується 5 днями")
    void carryOverCapsAtMax(int unused, int expected) {
        assertEquals(expected, leave.carryOver(unused));
    }

    @Test
    @DisplayName("TC-10 [BL-20]: від'ємний залишок днів викликає IllegalArgumentException")
    void carryOverNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> leave.carryOver(-1));
    }

    // --- BL-30: durationDays(start, end) ---

    @Test
    @DisplayName("TC-11..TC-14 [BL-30]: підрахунок робочих днів включно з обома межами та без вихідних")
    void durationDaysCountsWorkingDaysInclusive() {
        assertAll(
            // TC-11: Один робочий день (понеділок) -> має бути 1
            () -> assertEquals(1, leave.durationDays(
                LocalDate.of(2026, 11, 16), LocalDate.of(2026, 11, 16))),
            // TC-12: Один вихідний день (субота) -> 0
            () -> assertEquals(0, leave.durationDays(
                LocalDate.of(2026, 11, 14), LocalDate.of(2026, 11, 14))),
            // TC-13: Повний робочий тиждень Пн-Пт -> 5
            () -> assertEquals(5, leave.durationDays(
                LocalDate.of(2026, 11, 16), LocalDate.of(2026, 11, 20))),
            // TC-14: Період із захватом вихідних Пт-Пн -> 2
            () -> assertEquals(2, leave.durationDays(
                LocalDate.of(2026, 11, 13), LocalDate.of(2026, 11, 16)))
        );
    }

    @Test
    @DisplayName("TC-15, TC-16 [BL-30]: некоректні дати або null викликають IllegalArgumentException")
    void durationDaysInvalidDatesThrow() {
        LocalDate date = LocalDate.of(2026, 11, 16);
        assertAll(
            // TC-16: null
            () -> assertThrows(IllegalArgumentException.class, () -> leave.durationDays(null, date)),
            () -> assertThrows(IllegalArgumentException.class, () -> leave.durationDays(date, null)),
            // TC-15: start > end
            () -> assertThrows(IllegalArgumentException.class,
                () -> leave.durationDays(LocalDate.of(2026, 11, 20), LocalDate.of(2026, 11, 16)))
        );
    }

    // --- BL-40: canRequest(balance, duration) ---

    @Test
    @DisplayName("TC-17..TC-20 [BL-40]: перевірка можливості подати заявку на відпустку")
    void canRequestBoundaryChecks() {
        assertAll(
            // TC-17: duration = 0 -> false
            () -> assertFalse(leave.canRequest(5, 0)),
            () -> assertFalse(leave.canRequest(5, -1)),
            // TC-18: duration = 1 -> true
            () -> assertTrue(leave.canRequest(5, 1)),
            // TC-19: duration == balance -> true
            () -> assertTrue(leave.canRequest(5, 5)),
            // TC-20: duration > balance -> false
            () -> assertFalse(leave.canRequest(5, 6)),
            () -> assertFalse(leave.canRequest(0, 1))
        );
    }
}
