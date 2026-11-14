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
    @DisplayName("TC-005: Перехід до розділу PIM та відкриття списку працівників")
    void openPimEmployeeList() {
        loginAsAdmin();

        waitVisible(By.xpath("//span[text()='PIM']")).click();
        WebElement pageTitle = waitVisible(By.xpath("//h6[text()='PIM']"));
        assertEquals("PIM", pageTitle.getText());
    }

    @Test
    @DisplayName("TC-006: Валідація обов'язкових полів при додаванні нового працівника (PIM -> Add Employee)")
    void addEmployeeEmptyRequiredFieldsShowsValidation() {
        loginAsAdmin();

        waitVisible(By.xpath("//span[text()='PIM']")).click();
        waitVisible(By.linkText("Add Employee")).click();

        // Чекаємо завантаження форми і натискаємо Save без заповнення First Name та Last Name
        waitVisible(By.name("firstName"));
        waitVisible(By.cssSelector("button[type='submit']")).click();
        waitVisible(By.cssSelector(".oxd-input-field-error-message"));

        int requiredCount = driver.findElements(By.cssSelector(".oxd-input-field-error-message")).size();
        assertTrue(requiredCount >= 2, "Мають відображатися помилки валідації 'Required' для імені та прізвища");
    }

    @Test
    @DisplayName("TC-021: Перевірка відкриття профілю користувача (My Info -> Personal Details)")
    void openMyInfoPersonalDetails() {
        loginAsAdmin();

        waitVisible(By.xpath("//span[text()='My Info']")).click();
        WebElement sectionHeader = waitVisible(By.xpath("//h6[text()='Personal Details']"));
        assertEquals("Personal Details", sectionHeader.getText());
    }
}