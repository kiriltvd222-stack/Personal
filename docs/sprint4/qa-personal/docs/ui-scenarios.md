# Звіт про автоматизацію UI-тестування — ПЗ «Персонал+» (Sprint 4)

- **Об'єкт тестування:** веб-інтерфейс OrangeHRM OS 5.9 (`https://opensource-demo.orangehrmlive.com`)
- **Виконавець (SDET):** Кудря Кіріл (група ФБ-42)
- **Інструменти:** Katalon Recorder / Selenium IDE (`.side` + JUnit export), Selenium WebDriver 4.21.0, JUnit 5 (`@Tag("ui")`), Google Chrome Headless
- **Джерело кейсів:** `docs/sprint3/test-cases_reviewed.xlsx`

## 1. Запис сценаріїв у рекордері (`selenium-ide/`)

- `selenium-ide/personal.side` — проєкт у форматі Selenium IDE з двома базовими сценаріями (`TC001_LoginLogout`, `TC002_InvalidPassword`).
- `selenium-ide/export/TC001LoginLogoutTest.java` — експортована чернетка сценарію входу та виходу.
- `selenium-ide/export/TC002InvalidPasswordTest.java` — експортована чернетка перевірки помилки невірного пароля.

## 2. Автоматизовані тести на Selenium WebDriver (`src/test/java/ua/kpi/tzyapz/personal/ui/`)

Усі тести наслідують `BaseUiTest`, використовують явні очікування (`WebDriverWait`) та промарковані анотацією `@Tag("ui")`.

| Тест-кейс (Sprint 3) | Вимога | Кроки сценарію | Ключові локатори | Очікуваний результат (Assertion) | Клас і метод автотесту | Статус |
|---|---|---|---|---|---|---|
| TC-001 | F-10, GUI-10, GUI-30 | 1. Відкрити сторінку входу.<br>2. Ввести `Admin` та `admin123`, натиснути `Login`.<br>3. Перевірити заголовок `Dashboard`.<br>4. Відкрити меню користувача та натиснути `Logout`. | `By.name("username")`, `By.name("password")`, `By.cssSelector("button[type='submit']")`, `By.xpath("//h6[text()='Dashboard']")`, `By.cssSelector(".oxd-userdropdown-tab")`, `By.linkText("Logout")` | Відкривається сторінка `Dashboard`; після натискання `Logout` сесія завершується і відображається форма входу | `AuthUiTest#successfulLoginAndLogout` | Passed |
| TC-002 | F-20, GUI-20 | 1. Відкрити сторінку входу.<br>2. У поле `Username` ввести `Admin`, у `Password` — `WrongPassword123`.<br>3. Натиснути `Login`. | `By.name("username")`, `By.name("password")`, `By.cssSelector("button[type='submit']")`, `By.cssSelector(".oxd-alert-content-text")` | Вхід не виконується; у верхній частині форми відображається повідомлення `Invalid credentials` | `AuthUiTest#loginWithInvalidPasswordShowsError` | Passed |
| TC-003 | F-20, GUI-40 | 1. Відкрити сторінку входу.<br>2. Залишити поле `Username` порожнім.<br>3. У поле `Password` ввести `admin123` та натиснути `Login`. | `By.name("password")`, `By.cssSelector("button[type='submit']")`, `By.cssSelector(".oxd-input-field-error-message")` | Вхід не виконується; під полем `Username` з'являється одне повідомлення `Required` | `AuthUiTest#loginWithEmptyUsernameShowsRequiredValidation` | Passed |
| TC-005 | F-30 | 1. Увійти як `Admin` / `admin123`.<br>2. Перейти в модуль `Admin` → натиснути `+ Add`.<br>3. У поле `Username` ввести `User` (4 символи).<br>4. Перевести фокус на інший елемент форми. | `By.xpath("//span[text()='Admin']")`, `By.xpath("//button[normalize-space()='Add']")`, `By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input")`, `By.cssSelector(".oxd-input-field-error-message")` | Під полем `Username` червоним текстом відображається помилка `Should have at least 5 characters` | `ValidationUiTest#addUserWithShortUsernameShowsValidationError` | Passed |
| TC-006 | F-30, GUI-50 | 1. Увійти як `Admin` / `admin123`.<br>2. Перейти в модуль `Admin` → натиснути `+ Add`.<br>3. У поле `Password` ввести `Password` (8 символів без цифр).<br>4. Перевести фокус на інший елемент форми. | `By.xpath("//span[text()='Admin']")`, `By.xpath("//button[normalize-space()='Add']")`, `By.xpath("//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input")`, `By.cssSelector(".oxd-input-field-error-message")` | Під полем `Password` з'являється повідомлення валідації `Your password must contain minimum 1 number` | `ValidationUiTest#addUserWithPasswordWithoutDigitsShowsValidationError` | Passed |
| TC-021 | F-140, BL-80 | 1. Увійти як `Admin` / `admin123`.<br>2. Перейти в модуль `Recruitment` → натиснути `+ Add`.<br>3. Ввести `First Name = Ivan`, `Last Name = Franko`.<br>4. У поле `Email` ввести `ivan_franko@test` (без доменної зони) та натиснути `Save`. | `By.xpath("//span[text()='Recruitment']")`, `By.name("firstName")`, `By.name("lastName")`, `By.xpath("//label[text()='Email']/ancestor::div[contains(@class,'oxd-input-group')]//input")`, `By.cssSelector(".oxd-input-field-error-message")` | Форма не зберігається; під полем `Email` з'являється помилка формату (`Expected format: admin@example.com`) | `ValidationUiTest#addCandidateWithInvalidEmailShowsFormatError` | Passed |
