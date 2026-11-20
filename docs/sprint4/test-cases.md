# Тест-кейси модульного тестування — П3 «Персонал+» (Sprint 4)

- **Об'єкт тестування:** модуль бізнес-логіки `ua.kpi.tzyapz.personal` v1.0.0 (`LeaveCalculator`, `PayrollCalculator`, `EmployeeValidator`)
- **Специфікація:** розділ 5 `SRS_03_Персонал_плюс.html` (правила `BL-10` — `BL-80`)
- **Тест-аналітик:** Ходячих Нікіта (група ФБ-42)
- **Інженер з тестування (SDET):** Кудря Кіріл
- **Керівник тестування (Test Lead):** Сиротін Максим

## 1. Модуль `LeaveCalculator` (`BL-10` — `BL-40`)

Константи специфікації: `ACCRUAL_PER_MONTH = 2`, `MAX_ACCRUAL_PER_YEAR = 24`, `MAX_CARRY_OVER = 5`.

| ID | Вимога | Метод | Тип тесту | Вхідні дані | Очікуваний результат (Expected Result) | Статус рев'ю |
|---|---|---|---|---|---|---|
| TC-01 | BL-10 | `LeaveCalculatorTest#accruedCalculatesWithYearlyCap` | Граничний | `monthsWorked = 0` | `0` | Reviewed (Кудря К.) |
| TC-02 | BL-10 | `LeaveCalculatorTest#accruedCalculatesWithYearlyCap` | Позитивний | `monthsWorked = 6` | `12` | Reviewed (Кудря К.) |
| TC-03 | BL-10 | `LeaveCalculatorTest#accruedCalculatesWithYearlyCap` | Граничний | `monthsWorked = 12` | `24` | Reviewed (Кудря К.) |
| TC-04 | BL-10 | `LeaveCalculatorTest#accruedCalculatesWithYearlyCap` | Граничний (ліміт) | `monthsWorked = 13` | `24` (не перевищує `MAX_ACCRUAL_PER_YEAR`) | Reviewed (Кудря К.) |
| TC-05 | BL-10 | `LeaveCalculatorTest#accruedNegativeThrows` | Негативний | `monthsWorked = -1` | Виняток `IllegalArgumentException` | Reviewed (Кудря К.) |
| TC-06 | BL-20 | `LeaveCalculatorTest#carryOverCapsAtMax` | Граничний | `unused = 0` | `0` | Reviewed (Кудря К.) |
| TC-07 | BL-20 | `LeaveCalculatorTest#carryOverCapsAtMax` | Позитивний | `unused = 3` | `3` | Reviewed (Кудря К.) |
| TC-08 | BL-20 | `LeaveCalculatorTest#carryOverCapsAtMax` | Граничний | `unused = 5` | `5` | Reviewed (Кудря К.) |
| TC-09 | BL-20 | `LeaveCalculatorTest#carryOverCapsAtMax` | Граничний (ліміт) | `unused = 6` | `5` (обмежується `MAX_CARRY_OVER`) | Reviewed (Кудря К.) |
| TC-10 | BL-20 | `LeaveCalculatorTest#carryOverNegativeThrows` | Негативний | `unused = -1` | Виняток `IllegalArgumentException` | Reviewed (Кудря К.) |
| TC-11 | BL-30 | `LeaveCalculatorTest#durationDaysCountsWorkingDaysInclusive` | Граничний (1 будній день) | `start = 2026-11-16` (Пн), `end = 2026-11-16` (Пн) | `1` (обидві межі включно) | Reviewed (Кудря К.) |
| TC-12 | BL-30 | `LeaveCalculatorTest#durationDaysCountsWorkingDaysInclusive` | Граничний (1 вихідний день) | `start = 2026-11-14` (Сб), `end = 2026-11-14` (Сб) | `0` | Reviewed (Кудря К.) |
| TC-13 | BL-30 | `LeaveCalculatorTest#durationDaysCountsWorkingDaysInclusive` | Позитивний (повний тиждень) | `start = 2026-11-16` (Пн), `end = 2026-11-20` (Пт) | `5` | Reviewed (Кудря К.) |
| TC-14 | BL-30 | `LeaveCalculatorTest#durationDaysCountsWorkingDaysInclusive` | Позитивний (із вихідними) | `start = 2026-11-13` (Пт), `end = 2026-11-16` (Пн) | `2` (Сб та Нд не враховуються) | Reviewed (Кудря К.) |
| TC-15 | BL-30 | `LeaveCalculatorTest#durationDaysInvalidDatesThrow` | Негативний (`start > end`) | `start = 2026-11-20`, `end = 2026-11-16` | Виняток `IllegalArgumentException` | Reviewed (Кудря К.) |
| TC-16 | BL-30 | `LeaveCalculatorTest#durationDaysInvalidDatesThrow` | Негативний (`null`) | `start = null`, `end = 2026-11-16` | Виняток `IllegalArgumentException` | Reviewed (Кудря К.) |
| TC-17 | BL-40 | `LeaveCalculatorTest#canRequestBoundaryChecks` | Граничний (`duration = 0`) | `balance = 5`, `duration = 0` | `false` | Reviewed (Кудря К.) |
| TC-18 | BL-40 | `LeaveCalculatorTest#canRequestBoundaryChecks` | Граничний (`duration = 1`) | `balance = 5`, `duration = 1` | `true` | Reviewed (Кудря К.) |
| TC-19 | BL-40 | `LeaveCalculatorTest#canRequestBoundaryChecks` | Граничний (`duration == balance`) | `balance = 5`, `duration = 5` | `true` | Reviewed (Кудря К.) |
| TC-20 | BL-40 | `LeaveCalculatorTest#canRequestBoundaryChecks` | Граничний (`duration > balance`) | `balance = 5`, `duration = 6` | `false` | Reviewed (Кудря К.) |

## 2. Модуль `PayrollCalculator` (`BL-50` — `BL-70`)

Константи специфікації: `PIT_RATE = 0.18` (18%), `MILITARY_RATE = 0.05` (5%), `HOURS_PER_MONTH = 168`, `OVERTIME_COEF = 1.5`, округлення `HALF_UP` до 2 знаків.

| ID | Вимога | Метод | Тип тесту | Вхідні дані | Очікуваний результат (Expected Result) | Статус рев'ю |
|---|---|---|---|---|---|---|
| TC-21 | BL-50 | `PayrollCalculatorTest#netCalculatesCorrectlyForValidGross` | Граничний | `gross = 0.00` | `0.00` | Reviewed (Сиротін М.) |
| TC-22 | BL-50 | `PayrollCalculatorTest#netCalculatesCorrectlyForValidGross` | Позитивний | `gross = 10000.00` | `7700.00` (10000 - 1800 ПДФО - 500 ВЗ) | Reviewed (Сиротін М.) |
| TC-23 | BL-50 | `PayrollCalculatorTest#netCalculatesCorrectlyForValidGross` | Позитивний (округлення `HALF_UP`) | `gross = 12345.67` | `9506.17` (9506.1659 округлено `HALF_UP`) | Reviewed (Сиротін М.) |
| TC-24 | BL-50 | `PayrollCalculatorTest#netThrowsOnNegativeOrNull` | Негативний | `gross = -100.00` або `null` | Виняток `IllegalArgumentException` | Reviewed (Сиротін М.) |
| TC-25 | BL-60 | `PayrollCalculatorTest#hourlyRateCalculatesCorrectly` | Граничний | `monthly = 0.00` | `0.00` | Reviewed (Сиротін М.) |
| TC-26 | BL-60 | `PayrollCalculatorTest#hourlyRateCalculatesCorrectly` | Позитивний (точне ділення) | `monthly = 16800.00` | `100.00` | Reviewed (Сиротін М.) |
| TC-27 | BL-60 | `PayrollCalculatorTest#hourlyRateCalculatesCorrectly` | Граничний (округлення `HALF_UP`) | `monthly = 20000.00` | `119.05` (20000 / 168 = 119.0476... -> `HALF_UP`) | Reviewed (Сиротін М.) |
| TC-28 | BL-60 | `PayrollCalculatorTest#hourlyRateThrowsOnNegativeOrNull` | Негативний | `monthly = -1.00` або `null` | Виняток `IllegalArgumentException` | Reviewed (Сиротін М.) |
| TC-29 | BL-70 | `PayrollCalculatorTest#overtimePayCalculatesCorrectly` | Граничний (0 годин) | `monthly = 16800.00`, `hours = 0` | `0.00` | Reviewed (Сиротін М.) |
| TC-30 | BL-70 | `PayrollCalculatorTest#overtimePayCalculatesCorrectly` | Позитивний (дробові години) | `monthly = 16800.00`, `hours = 2.5` | `375.00` (100.00 * 1.5 * 2.5) | Reviewed (Сиротін М.) |
| TC-31 | BL-70 | `PayrollCalculatorTest#overtimePayThrowsOnNegativeHours` | Негативний | `monthly = 16800.00`, `hours = -1` | Виняток `IllegalArgumentException` | Reviewed (Сиротін М.) |

## 3. Модуль `EmployeeValidator` (`BL-80`)

Константи специфікації: `MIN_AGE_YEARS = 16`, `TAX_ID_LENGTH = 10`.

| ID | Вимога | Метод | Тип тесту | Вхідні дані | Очікуваний результат (Expected Result) | Статус рев'ю |
|---|---|---|---|---|---|---|
| TC-32 | BL-80 | `EmployeeValidatorTest#isValidEmailReturnsTrueForValidAddress` | Позитивний | `"ivan.petrenko@personal.plus"` | `true` | Reviewed (Ходячих Н.) |
| TC-33 | BL-80 | `EmployeeValidatorTest#isValidEmailReturnsFalseForInvalidAddress` | Негативний (без зони / `@` / `null`) | `"ivan@localhost"`, `""`, `null` | `false` для кожного випадку | Reviewed (Ходячих Н.) |
| TC-34 | BL-80 | `EmployeeValidatorTest#isValidTaxIdReturnsTrueForTenDigits` | Позитивний (рівно 10 цифр) | `"1234567890"` | `true` | Reviewed (Ходячих Н.) |
| TC-35 | BL-80 | `EmployeeValidatorTest#isValidTaxIdReturnsFalseForNineDigits` | Граничний (9 цифр) | `"123456789"` | `false` | Reviewed (Ходячих Н.) |
| TC-36 | BL-80 | `EmployeeValidatorTest#isValidTaxIdReturnsFalseForElevenDigits` | Граничний (11 цифр) | `"12345678901"` | `false` | Reviewed (Ходячих Н.) |
| TC-37 | BL-80 | `EmployeeValidatorTest#isValidTaxIdReturnsFalseForLettersOrEmpty` | Негативний (літери / `null`) | `"12345abcde"`, `""`, `null` | `false` | Reviewed (Ходячих Н.) |
| TC-38 | BL-80 | `EmployeeValidatorTest#isHireDateValidReturnsTrueForPastDate` | Позитивний (дата в минулому) | `hireDate = 2026-11-11`, `today = 2026-11-12` | `true` | Reviewed (Ходячих Н.) |
| TC-39 | BL-80 | `EmployeeValidatorTest#isHireDateValidReturnsTrueForToday` | Граничний (сьогодні) | `hireDate = 2026-11-12`, `today = 2026-11-12` | `true` | Reviewed (Ходячих Н.) |
| TC-40 | BL-80 | `EmployeeValidatorTest#isHireDateValidReturnsFalseForFutureDate` | Граничний (завтра) | `hireDate = 2026-11-13`, `today = 2026-11-12` | `false` | Reviewed (Ходячих Н.) |
| TC-41 | BL-80 | `EmployeeValidatorTest#isHireDateValidThrowsOnNull` | Негативний (`null`) | `hireDate = null`, `today = 2026-11-12` | Виняток `IllegalArgumentException` | Reviewed (Ходячих Н.) |
| TC-42 | BL-80 | `EmployeeValidatorTest#isOldEnoughReturnsTrueForExactlySixteen` | Граничний (рівно 16 років) | `birthDate = 2010-11-12`, `hireDate = 2026-11-12` | `true` | Reviewed (Ходячих Н.) |
| TC-43 | BL-80 | `EmployeeValidatorTest#isOldEnoughReturnsFalseOneDayBeforeSixteen` | Граничний (16 років без 1 дня) | `birthDate = 2010-11-13`, `hireDate = 2026-11-12` | `false` | Reviewed (Ходячих Н.) |
| TC-44 | BL-80 | `EmployeeValidatorTest#isOldEnoughThrowsOnNull` | Негативний (`null`) | `birthDate = null`, `hireDate = 2026-11-12` | Виняток `IllegalArgumentException` | Reviewed (Ходячих Н.) |
| TC-45 | BL-80 | `EmployeeValidatorTest#validateReturnsEmptyListWhenAllFieldsValid` | Позитивний (усі поля коректні) | `email="ivan@personal.plus"`, `taxId="1234567890"`, `birth=2000-01-01`, `hire=2026-11-12`, `today=2026-11-12` | Порожній список `[]` | Reviewed (Ходячих Н.) |
| TC-46 | BL-80 | `EmployeeValidatorTest#validateReturnsAllErrorCodesWhenAllFieldsInvalid` | Негативний (помилки в усіх полях) | `email="bad"`, `taxId="123"`, `birth=2012-01-01`, `hire=2026-12-01`, `today=2026-11-12` | Список `["EMAIL", "TAX_ID", "HIRE_DATE", "AGE"]` | Reviewed (Ходячих Н.) |
