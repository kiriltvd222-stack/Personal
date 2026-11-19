# Спринт 4 «Автоматизація: JUnit 5 і Selenium WebDriver». Інкремент

**Команда:** Personal QA Team (група ФБ-42)
**Продукт:** П3 «Персонал+» (модуль `ua.kpi.tzyapz.personal` v1.0.0 + demo OrangeHRM OS 5.9)
**Репозиторій автотестів:** `docs/sprint4/qa-personal/`

---

## 1. Нормативи (команда з 3 осіб) — План / Факт

| Норматив (§6) | План | Факт | Статус |
| :--- | :---: | :---: | :---: |
| Кількість юніт-тест-методів (JUnit 5) | ≥ 12 | **28** (53 запуски з урахуванням параметризованих) | Виконано |
| Покриття вимог бізнес-логіки SRS | BL-10 — BL-80 | **100%** (`LeaveCalculator`, `PayrollCalculator`, `EmployeeValidator`) | Виконано |
| Сценарії Selenium IDE (`.side` + експорт) | 1–2 | **2** (`TC-001`, `TC-002`) | Виконано |
| Автоматизовані UI-сценарії (WebDriver) | ≥ 4 | **6** (`AuthUiTest`, `ValidationUiTest`) | Виконано |
| Зареєстровані дефекти модуля (`module`) | ≥ 3 | **4** (Issues #8, #9, #10, #11) | Виконано |

---

## 2. Матриця покриття бізнес-логіки (SRS BL-10 — BL-80)

| Клас модуля | Покриті вимоги | Тест-клас | Тест-методів / запусків | Відповідальний |
| :--- | :--- | :--- | :---: | :--- |
| `LeaveCalculator` | BL-10, BL-20, BL-30, BL-40 | `LeaveCalculatorTest` | 7 / 16 | Ходячих Нікіта |
| `PayrollCalculator` | BL-50, BL-60, BL-70 | `PayrollCalculatorTest` | 6 / 15 | Кудря Кіріл |
| `EmployeeValidator` | BL-80 | `EmployeeValidatorTest` | 15 / 22 | Сиротін Максим |

Детальна матриця тест-кейсів (`TC-01` — `TC-46`): `docs/sprint4/test-cases.md`.

---

## 3. UI-сценарії (Selenium WebDriver)

Детальний опис кроків та локаторів: `docs/sprint4/qa-personal/docs/ui-scenarios.md`.
* `AuthUiTest`: `TC-001` (вхід/вихід), `TC-002` (невірний пароль), `TC-003` (валідація порожнього поля).
* `ValidationUiTest`: `TC-005`, `TC-006`, `TC-021`.

---

## 4. Підсумок прогону (Surefire Summary)

Повний протокол: `docs/sprint4/qa-personal/docs/surefire-summary.md`.
* **Юніт-тести (`mvn clean test`):** `Tests run: 53, Failures: 9, Errors: 0, Skipped: 0` (44 Passed, 9 Failures).
* **UI-тести (`-DexcludedGroups=none`):** `Tests run: 6, Failures: 0, Errors: 0, Skipped: 0` (`BUILD SUCCESS`).

---

## 5. Зареєстровані дефекти модуля v1 (GitHub Issues, мітка `module`)

| Issue / ID | Вимога | Метод і тест, що падає | Severity | Короткий опис |
| :--- | :---: | :--- | :---: | :--- |
| **#8 (`BUG-007`)** | BL-50 | `PayrollCalculatorTest#netCalculatesCorrectlyForValidGross` | Major | ВЗ 5% рахується від суми після ПДФО замість `gross`. |
| **#9 (`BUG-008`)** | BL-60 | `PayrollCalculatorTest#hourlyRateCalculatesCorrectly` | Minor | Округлення `RoundingMode.DOWN` замість `HALF_UP`. |
| **#10 (`BUG-009`)** | BL-80 | `EmployeeValidatorTest#isValidTaxIdReturnsFalseForNineDigits` | Major | `isValidTaxId` пропускає коди коротші за 10 цифр (`<= 10`). |
| **#11 (`BUG-010`)** | BL-80 | `EmployeeValidatorTest#isOldEnoughReturnsFalseOneDayBeforeSixteen` | Major | `isOldEnough` порівнює лише роки без урахування місяця і дня. |

---

## 6. Висновок про стан модуля v1
Модуль бізнес-логіки `v1.0.0` **не готовий до релізу** без виправлення критичних розбіжностей із SRS: виявлено дефекти в усіх трьох класах (некоректне утримання військового збору, неправильне округлення ставки, пропуск 9-значного РНОКПП, неточна перевірка віку 16 років, відсутність ліміту 24 дні та виключення кінцевої дати відпустки). Написані автотести відтворюють кожен дефект і готові для регресійної перевірки версії `v2` у Спринті 5.
