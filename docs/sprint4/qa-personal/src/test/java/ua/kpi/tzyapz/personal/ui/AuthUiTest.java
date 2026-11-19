package ua.kpi.tzyapz.personal.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("ui")
class AuthUiTest extends BaseUiTest {

    @Test
    @DisplayName("TC-001: Успішний вхід в систему під обліковим записом Admin та вихід")
    void successfulLoginAndLogout() {
        loginAsAdmin();

        WebElement header = waitVisible(By.xpath("//h6[text()='Dashboard']"));
        assertEquals("Dashboard", header.getText());

        // Вихід із системи
        waitVisible(By.cssSelector(".oxd-userdropdown-tab")).click();
        waitVisible(By.linkText("Logout")).click();

        assertTrue(waitVisible(By.name("username")).isDisplayed());
    }

    @Test
    @DisplayName("TC-002: Помилка входу при введенні невірного пароля")
    void loginWithInvalidPasswordShowsError() {
        waitVisible(By.name("username")).sendKeys("Admin");
        waitVisible(By.name("password")).sendKeys("WrongPassword123");
        waitVisible(By.cssSelector("button[type='submit']")).click();

        WebElement alert = waitVisible(By.cssSelector(".oxd-alert-content-text"));
        assertEquals("Invalid credentials", alert.getText());
    }

    @Test
    @DisplayName("TC-003: Спроба входу з порожнім полем Username")
    void loginWithEmptyUsernameShowsRequiredValidation() {
        // Поле 'Username' залишаємо порожнім, у 'Password' вводимо 'admin123'
        waitVisible(By.name("password")).sendKeys("admin123");
        waitVisible(By.cssSelector("button[type='submit']")).click();

        WebElement errorMsg = waitVisible(By.cssSelector(".oxd-input-field-error-message"));
        assertEquals("Required", errorMsg.getText());

        List<WebElement> allErrors = driver.findElements(By.cssSelector(".oxd-input-field-error-message"));
        assertEquals(1, allErrors.size(), "Очікується рівно 1 повідомлення 'Required' лише під полем Username");
    }
}
