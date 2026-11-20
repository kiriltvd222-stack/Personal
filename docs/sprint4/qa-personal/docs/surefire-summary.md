# Підсумковий звіт Maven Surefire — ПЗ «Персонал+» (Sprint 4)

- **Дата прогону:** 20.11.2026
- **Середовище:** GitHub Codespaces (Linux x64, Java 17, Maven 3.9, Google Chrome Headless)
- **Команда:** Personal QA Team (група ФБ-42)

---

## 1. Модульне тестування (Unit Tests)

Команда запуску (з папки `docs/sprint4/qa-personal/`):
```bash
mvn clean test
```

- **Класи:** `LeaveCalculatorTest` (16 запусків), `PayrollCalculatorTest` (15 запусків), `EmployeeValidatorTest` (22 запуски)
- **Рядок Surefire:** `Tests run: 53, Failures: 9, Errors: 0, Skipped: 0`
- **Успішно (Passed):** 44
- **Провалено через дефекти модуля v1 (Failures):** 9

### Деталізація провалених юніт-тестів (дефекти v1)

| Тест-метод | Кейс / Вимога | Причина падіння (Actual vs Expected) | Дефект / Issue |
|---|---|---|---|
| `PayrollCalculatorTest#netCalculatesCorrectlyForValidGross[2]` | TC-22 (BL-50) | `expected: <7700.00> but was: <7790.00>` (ВЗ 5% утримується після ПДФО) | Issue #8 (`BUG-007`) |
| `PayrollCalculatorTest#netCalculatesCorrectlyForValidGross[3]` | TC-23 (BL-50) | `expected: <9506.17> but was: <9617.28>` (ВЗ 5% утримується після ПДФО) | Issue #8 (`BUG-007`) |
| `PayrollCalculatorTest#hourlyRateCalculatesCorrectly[3]` | TC-27 (BL-60) | `expected: <119.05> but was: <119.04>` (`RoundingMode.DOWN` замість `HALF_UP`) | Issue #9 (`BUG-008`) |
| `EmployeeValidatorTest#isValidTaxIdReturnsFalseForNineDigits` | TC-35 (BL-80) | `expected: <false> but was: <true>` (перевірка `length() <= 10` замість `== 10`) | Issue #10 (`BUG-009`) |
| `EmployeeValidatorTest#validateReturnsAllErrorCodesWhenAllFieldsInvalid` | TC-46 (BL-80) | `expected: <[EMAIL, TAX_ID, HIRE_DATE, AGE]> but was: <[EMAIL, HIRE_DATE, AGE]>` | Issue #10 (`BUG-009`) |
| `EmployeeValidatorTest#isOldEnoughReturnsFalseOneDayBeforeSixteen` | TC-43 (BL-80) | `expected: <false> but was: <true>` (порівняння лише років без місяця і дня) | Issue #11 (`BUG-010`) |
| `LeaveCalculatorTest#accruedCalculatesWithYearlyCap[5]` | TC-04 (BL-10) | `expected: <24> but was: <26>` (відсутній річний ліміт `MAX_ACCRUAL_PER_YEAR = 24`) | Дефект `LeaveCalculator` |
| `LeaveCalculatorTest#accruedCalculatesWithYearlyCap[6]` | TC-04 (BL-10) | `expected: <24> but was: <30>` (відсутній річний ліміт `MAX_ACCRUAL_PER_YEAR = 24`) | Дефект `LeaveCalculator` |
| `LeaveCalculatorTest#durationDaysCountsWorkingDaysInclusive` | TC-11, TC-13, TC-14 (BL-30) | `expected: <1>/<5>/<2> but was: <0>/<4>/<1>` (кінцева дата `end` не включається) | Дефект `LeaveCalculator` |

---

## 2. UI-тестування (Selenium WebDriver)

Команда запуску (з папки `docs/sprint4/qa-personal/`):
```bash
mvn test -Dtest="ua.kpi.tzyapz.personal.ui.*Test" -DexcludedGroups=none
```

- **Класи:** `AuthUiTest` (`TC-001`, `TC-002`, `TC-003`), `ValidationUiTest` (`TC-005`, `TC-006`, `TC-021`)
- **Рядок Surefire:** `Tests run: 6, Failures: 0, Errors: 0, Skipped: 0`
- **Результат збірки:** `BUILD SUCCESS`
