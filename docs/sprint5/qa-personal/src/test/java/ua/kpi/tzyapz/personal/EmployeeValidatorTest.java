package ua.kpi.tzyapz.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@DisplayName("EmployeeValidator — валідація даних працівника (BL-80)")
class EmployeeValidatorTest {

    private EmployeeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmployeeValidator();
    }

    // --- Email (TC-32, TC-33) ---

    @Test
    @DisplayName("BL-80 (TC-32): коректний email проходить валідацію")
    void isValidEmailReturnsTrueForValidAddress() {
        assertTrue(validator.isValidEmail("ivan.petrenko@personal.plus"));
    }

    @ParameterizedTest(name = "BL-80 (TC-33): некоректний email=\"{0}\" відхиляється")
    @NullAndEmptySource
    @ValueSource(strings = {"ivan@localhost", "ivan.petrenko@", "plainaddress"})
    @DisplayName("BL-80 (TC-33): email без зони, без @, порожній або null повертає false")
    void isValidEmailReturnsFalseForInvalidAddress(String email) {
        assertFalse(validator.isValidEmail(email));
    }

    // --- РНОКПП / Tax ID (TC-34 .. TC-37) ---

    @Test
    @DisplayName("BL-80 (TC-34): РНОКПП із рівно 10 цифр проходить валідацію")
    void isValidTaxIdReturnsTrueForTenDigits() {
        assertTrue(validator.isValidTaxId("1234567890"));
    }

    @Test
    @DisplayName("BL-80 (TC-35): граничний РНОКПП із 9 цифр відхиляється")
    // Defect: очікується падіння на v1 (метод перевіряє length() <= 10 замість == 10)
    void isValidTaxIdReturnsFalseForNineDigits() {
        assertFalse(validator.isValidTaxId("123456789"));
    }

    @Test
    @DisplayName("BL-80 (TC-36): граничний РНОКПП з 11 цифр відхиляється")
    void isValidTaxIdReturnsFalseForElevenDigits() {
        assertFalse(validator.isValidTaxId("12345678901"));
    }

    @ParameterizedTest(name = "BL-80 (TC-37): некоректний taxId=\"{0}\" відхиляється")
    @NullAndEmptySource
    @ValueSource(strings = {"12345abcde", "123456789A"})
    @DisplayName("BL-80 (TC-37): РНОКПП із літерами, порожній або null повертає false")
    void isValidTaxIdReturnsFalseForLettersOrEmpty(String taxId) {
        assertFalse(validator.isValidTaxId(taxId));
    }

    // --- Дата найму (TC-38 .. TC-41) ---

    @Test
    @DisplayName("BL-80 (TC-38): дата найму в минулому коректна")
    void isHireDateValidReturnsTrueForPastDate() {
        LocalDate hireDate = LocalDate.of(2026, 11, 11);
        LocalDate today = LocalDate.of(2026, 11, 12);
        assertTrue(validator.isHireDateValid(hireDate, today));
    }

    @Test
    @DisplayName("BL-80 (TC-39): гранична дата найму сьогодні дозволена")
    void isHireDateValidReturnsTrueForToday() {
        LocalDate today = LocalDate.of(2026, 11, 12);
        assertTrue(validator.isHireDateValid(today, today));
    }

    @Test
    @DisplayName("BL-80 (TC-40): гранична дата найму в майбутньому (завтра) відхиляється")
    void isHireDateValidReturnsFalseForFutureDate() {
        LocalDate hireDate = LocalDate.of(2026, 11, 13);
        LocalDate today = LocalDate.of(2026, 11, 12);
        assertFalse(validator.isHireDateValid(hireDate, today));
    }

    @Test
    @DisplayName("BL-80 (TC-41): відсутня дата найму або поточна дата (null) викликає IllegalArgumentException")
    void isHireDateValidThrowsOnNull() {
        LocalDate today = LocalDate.of(2026, 11, 12);
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> validator.isHireDateValid(null, today)),
            () -> assertThrows(IllegalArgumentException.class, () -> validator.isHireDateValid(today, null))
        );
    }

    // --- Вік працівника (TC-42 .. TC-44) ---

    @Test
    @DisplayName("BL-80 (TC-42): граничний вік рівно 16 років на дату найму дозволено")
    void isOldEnoughReturnsTrueForExactlySixteen() {
        LocalDate birthDate = LocalDate.of(2010, 11, 12);
        LocalDate hireDate = LocalDate.of(2026, 11, 12);
        assertTrue(validator.isOldEnough(birthDate, hireDate));
    }

    @Test
    @DisplayName("BL-80 (TC-43): граничний вік 16 років без 1 дня відхиляється")
    // Defect: очікується падіння на v1 (різниця лише років ігнорує місяць і день)
    void isOldEnoughReturnsFalseOneDayBeforeSixteen() {
        LocalDate birthDate = LocalDate.of(2010, 11, 13);
        LocalDate hireDate = LocalDate.of(2026, 11, 12);
        assertFalse(validator.isOldEnough(birthDate, hireDate));
    }

    @Test
    @DisplayName("BL-80 (TC-44): відсутня дата народження або найму (null) викликає IllegalArgumentException")
    void isOldEnoughThrowsOnNull() {
        LocalDate hireDate = LocalDate.of(2026, 11, 12);
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> validator.isOldEnough(null, hireDate)),
            () -> assertThrows(IllegalArgumentException.class, () -> validator.isOldEnough(hireDate, null))
        );
    }

    // --- Повна валідація validate(...) (TC-45, TC-46) ---

    @Test
    @DisplayName("BL-80 (TC-45): повна валідація коректних даних повертає порожній список")
    void validateReturnsEmptyListWhenAllFieldsValid() {
        LocalDate birth = LocalDate.of(2000, 1, 1);
        LocalDate hire = LocalDate.of(2026, 11, 12);
        LocalDate today = LocalDate.of(2026, 11, 12);

        List<String> errors = validator.validate("ivan@personal.plus", "1234567890", birth, hire, today);
        assertTrue(errors.isEmpty(), "Очікувався порожній список, але отримано: " + errors);
    }

    @Test
    @DisplayName("BL-80 (TC-46): повна валідація некоректних даних повертає всі 4 коди помилок")
    // Defect: падає на v1 через баг у isValidTaxId (пропускає "123")
    void validateReturnsAllErrorCodesWhenAllFieldsInvalid() {
        LocalDate birth = LocalDate.of(2012, 1, 1);
        LocalDate hire = LocalDate.of(2026, 12, 1);
        LocalDate today = LocalDate.of(2026, 11, 12);

        List<String> errors = validator.validate("bad", "123", birth, hire, today);
        assertEquals(List.of("EMAIL", "TAX_ID", "HIRE_DATE", "AGE"), errors);
    }
}
