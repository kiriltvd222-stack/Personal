# Підсумковий звіт Maven Surefire — ПЗ «Персонал+» (Sprint 4)

* **Дата прогону:** 14.11.2026
* **Середовище:** GitHub Codespaces (Linux x64, Java 17+, Maven 3.9, Google Chrome Headless)
* **Виконавець (SDET):** Кудря Кіріл (група ФБ-42)

---

## 1. Модульне тестування (`PayrollCalculatorTest`)

Команда запуску: `mvn clean test` (тег `ui` виключено за замовчуванням).

* **Клас:** `ua.kpi.tzyapz.personal.PayrollCalculatorTest`
* **Всього запущено тестів:** 11 (`TC-21` — `TC-31`)
* **Успішно (Passed):** 8
* **Провалено через дефекти в коді v1 (Failures):** 3
* **Зареєстровані дефекти:**
  * **Issue #8 (`BUG-007`)** — `PayrollCalculator.net` (`BL-50`): військовий збір 5% помилково утримується із суми після відрахування ПДФО замість повної суми `gross` (провалені `TC-22`, `TC-23`).
  * **Issue #9 (`BUG-008`)** — `PayrollCalculator.hourlyRate` (`BL-60`): використано режим округлення `RoundingMode.DOWN` замість математичного `HALF_UP` (провалений `TC-27`).

---

## 2. UI-тестування (`AuthUiTest`, `ValidationUiTest`)

Команда запуску: `mvn test -Dtest="ua.kpi.tzyapz.personal.ui.*Test" -DexcludedGroups=none`

* **Класи:** `ua.kpi.tzyapz.personal.ui.AuthUiTest`, `ua.kpi.tzyapz.personal.ui.ValidationUiTest`
* **Всього запущено тестів:** 6 (`TC-001`, `TC-002`, `TC-003`, `TC-005`, `TC-006`, `TC-021`)
* **Успішно (Passed):** 6
* **Провалено (Failures):** 0
* **Помилок виконання (Errors):** 0
* **Результат збірки:** `BUILD SUCCESS`