package ua.kpi.tzyapz.personal.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("ui")
class ValidationUiTest extends BaseUiTest {

    @Test
    @DisplayName("TC-005: Спроба створення користувача з ім'ям коротшим за мінімальне (4 символи)")
    void addUserWithShortUsernameShowsValidationError() {
        loginAsAdmin();

        waitVisible(By.xpath("//span[text()='Admin']")).click();
        waitVisible(By.xpath("//button[normalize-space()='Add']")).click();

        WebElement usernameInput = waitVisible(
                By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input"));
        usernameInput.sendKeys("User");

        // Переводимо фокус на інше поле/елемент форми для спрацювання клієнтської валідації
        waitVisible(By.xpath("//h6[text()='Add User']")).click();

        WebElement errorMsg = waitVisible(By.cssSelector(".oxd-input-field-error-message"));
        assertEquals("Should have at least 5 characters", errorMsg.getText());
    }

    @Test
    @DisplayName("TC-006: Спроба створення користувача з паролем без цифр")
    void addUserWithPasswordWithoutDigitsShowsValidationError() {
        loginAsAdmin();

        waitVisible(By.xpath("//span[text()='Admin']")).click();
        waitVisible(By.xpath("//button[normalize-space()='Add']")).click();

        WebElement passwordInput = waitVisible(
                By.xpath("//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input"));
        passwordInput.sendKeys("Password");

        // Переводимо фокус для спрацювання валідації складності пароля
        waitVisible(By.xpath("//h6[text()='Add User']")).click();

        WebElement errorMsg = waitVisible(By.cssSelector(".oxd-input-field-error-message"));
        assertEquals("Your password must contain minimum 1 number", errorMsg.getText());
    }

    @Test
    @DisplayName("TC-021: Спроба додавання кандидата з недійсним email (без доменної зони)")
    void addCandidateWithInvalidEmailShowsFormatError() {
        loginAsAdmin();

        waitVisible(By.xpath("//span[text()='Recruitment']")).click();
        waitVisible(By.xpath("//button[normalize-space()='Add']")).click();

        waitVisible(By.name("firstName")).sendKeys("Ivan");
        waitVisible(By.name("lastName")).sendKeys("Franko");

        WebElement emailInput = waitVisible(
                By.xpath("//label[text()='Email']/ancestor::div[contains(@class,'oxd-input-group')]//input"));
        emailInput.sendKeys("ivan_franko@test");

        waitVisible(By.cssSelector("button[type='submit']")).click();

        WebElement errorMsg = waitVisible(By.cssSelector(".oxd-input-field-error-message"));
        assertTrue(errorMsg.getText().contains("Expected format"),
                "Має відображатися помилка формату email ('Expected format: admin@example.com')");
    }
}
