package ua.kpi.tzyapz.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Персонал+ — Автотести для EmployeeValidator (Вимога BL-80)")
class EmployeeValidatorTest {

    private EmployeeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmployeeValidator();
    }

    @Test
    @DisplayName("Позитивний: правильний email проходить валідацію")
    void validEmailReturnsTrue() {
        assertTrue(validator.isValidEmail("maksym.syrotin@kpi.ua"));
    }

    @Test
    @DisplayName("Негативний: email без домену не проходить")
    void invalidEmailReturnsFalse() {
        assertFalse(validator.isValidEmail("maksym.syrotin@"));
    }

    @Test
    @DisplayName("Позитивний: РНОКПП з 10 цифр проходить")
    void validTaxIdReturnsTrue() {
        assertTrue(validator.isValidTaxId("1234567890"));
    }

    @Test
    @DisplayName("Негативний: isOldEnough кидає помилку, якщо дати відсутні (null)")
    void nullDatesThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.isOldEnough(null, LocalDate.now());
        });
    }

    @Test
    @DisplayName("Позитивний: повна валідація коректних даних повертає порожній список")
    void fullValidationPasses() {
        LocalDate today = LocalDate.of(2026, 11, 10);
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        LocalDate hireDate = LocalDate.of(2025, 5, 1);

        List<String> errors = validator.validate("good@mail.com", "1234567890", birthDate, hireDate, today);
        assertTrue(errors.isEmpty());
    }
}
