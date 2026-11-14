# Звіт про автоматизацію UI-тестування — ПЗ «Персонал+» (Sprint 4)

* **Об'єкт тестування:** веб-інтерфейс OrangeHRM OS 5.9 (`https://opensource-demo.orangehrmlive.com`)
* **Виконавець (SDET):** Кудря Кіріл (група ФБ-42)
* **Інструменти:** Katalon Recorder / Selenium IDE (`.side` + JUnit export), Selenium WebDriver 4.21.0, JUnit 5 (`@Tag("ui")`), Google Chrome Headless

---

## 1. Запис сценаріїв у рекордері (`selenium-ide/`)

* `selenium-ide/personal.side` — проєкт у форматі Selenium IDE з двома базовими сценаріями (`TC001_LoginLogout`, `TC002_InvalidPassword`).
* `selenium-ide/export/TC001LoginLogoutTest.java` — експортована чернетка сценарію входу та виходу.
* `selenium-ide/export/TC002InvalidPasswordTest.java` — експортована чернетка перевірки помилки невірного пароля.

---

## 2. Автоматизовані тести на Selenium WebDriver (`src/test/java/ua/kpi/tzyapz/personal/ui/`)

Усі тести наслідують `BaseUiTest`, використовують явні очікування (`WebDriverWait`) та промарковані анотацією `@Tag("ui")`.

| Тест-кейс (Sprint 3) | Клас і метод автотесту | Перевірка (Assertion) | Статус |
|---|---|---|---|
| **TC-001** | `AuthUiTest#successfulLoginAndLogout` | Успішна авторизація `Admin`, перевірка заголовка `Dashboard` та коректний вихід (`Logout`) | Passed |
| **TC-002** | `AuthUiTest#loginWithInvalidPasswordShowsError` | Відображення повідомлення `Invalid credentials` при введенні невірного пароля | Passed |
| **TC-003** | `AuthUiTest#loginWithEmptyFieldsShowsRequiredValidation` | Відображення 2 помилок валідації `Required` при відправці порожньої форми входу | Passed |
| **TC-005** | `ValidationUiTest#openPimEmployeeList` | Навігація до модуля `PIM` та перевірка заголовка розділу | Passed |
| **TC-006** | `ValidationUiTest#addEmployeeEmptyRequiredFieldsShowsValidation` | Перехід у `PIM -> Add Employee` та перевірка помилок `Required` для порожніх імені й прізвища | Passed |
| **TC-021** | `ValidationUiTest#openMyInfoPersonalDetails` | Відкриття профілю користувача `My Info` та перевірка заголовка `Personal Details` | Passed |