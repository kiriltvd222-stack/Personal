package ua.kpi.tzyapz.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("PayrollCalculator: розрахунок зарплати, ставки та понаднормових (BL-50..BL-70)")
class PayrollCalculatorTest {

    private PayrollCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new PayrollCalculator();
    }

    // --- BL-50: net(gross) ---

    @ParameterizedTest(name = "BL-50 (TC-21..23): gross={0} -> net={1}")
    @CsvSource({
            "0.00, 0.00",
            "10000.00, 7700.00",
            "12345.67, 9506.17"
    })
    @DisplayName("BL-50: розрахунок суми «на руки» з утриманням ПДФО 18% та ВЗ 5% (округлення HALF_UP)")
    // Defect: Issue #8 (BUG-007)
    void netCalculatesCorrectlyForValidGross(String grossStr, String expectedNetStr) {
        // Arrange
        BigDecimal gross = new BigDecimal(grossStr);
        BigDecimal expected = new BigDecimal(expectedNetStr);

        // Act
        BigDecimal actual = calculator.net(gross);

        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "BL-50 (TC-24): некоректний gross={0} кидає виняток")
    @NullSource
    @ValueSource(strings = {"-0.01", "-100.00"})
    @DisplayName("BL-50: від'ємний або null gross викликає IllegalArgumentException")
    void netThrowsOnNegativeOrNull(String grossStr) {
        // Arrange
        BigDecimal gross = grossStr == null ? null : new BigDecimal(grossStr);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> calculator.net(gross));
    }

    // --- BL-60: hourlyRate(monthly) ---

    @ParameterizedTest(name = "BL-60 (TC-25..27): monthly={0} -> rate={1}")
    @CsvSource({
            "0.00, 0.00",
            "16800.00, 100.00",
            "20000.00, 119.05"
    })
    @DisplayName("BL-60: розрахунок годинної ставки (monthly / 168) з округленням HALF_UP")
    // Defect: Issue #9 (BUG-008)
    void hourlyRateCalculatesCorrectly(String monthlyStr, String expectedRateStr) {
        // Arrange
        BigDecimal monthly = new BigDecimal(monthlyStr);
        BigDecimal expected = new BigDecimal(expectedRateStr);

        // Act
        BigDecimal actual = calculator.hourlyRate(monthly);

        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "BL-60 (TC-28): некоректний monthly={0} кидає виняток")
    @NullSource
    @ValueSource(strings = {"-0.01", "-1.00"})
    @DisplayName("BL-60: від'ємний або null monthly викликає IllegalArgumentException")
    void hourlyRateThrowsOnNegativeOrNull(String monthlyStr) {
        // Arrange
        BigDecimal monthly = monthlyStr == null ? null : new BigDecimal(monthlyStr);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> calculator.hourlyRate(monthly));
    }

    // --- BL-70: overtimePay(monthly, hours) ---

    @ParameterizedTest(name = "BL-70 (TC-29..30): monthly={0}, hours={1} -> pay={2}")
    @CsvSource({
            "16800.00, 0, 0.00",
            "16800.00, 2.5, 375.00"
    })
    @DisplayName("BL-70: розрахунок оплати понаднормових (ставка * 1.5 * години)")
    void overtimePayCalculatesCorrectly(String monthlyStr, String hoursStr, String expectedPayStr) {
        // Arrange
        BigDecimal monthly = new BigDecimal(monthlyStr);
        BigDecimal hours = new BigDecimal(hoursStr);
        BigDecimal expected = new BigDecimal(expectedPayStr);

        // Act
        BigDecimal actual = calculator.overtimePay(monthly, hours);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("BL-70 (TC-31): від'ємні понаднормові години викликають IllegalArgumentException")
    void overtimePayThrowsOnNegativeHours() {
        // Arrange
        BigDecimal monthly = new BigDecimal("16800.00");
        BigDecimal negativeHours = new BigDecimal("-1");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> calculator.overtimePay(monthly, negativeHours));
    }
}