# Тест-кейси модульного тестування — П3 «Персонал+» (Sprint 4)

* **Об'єкт тестування:** модуль бізнес-логіки `ua.kpi.tzyapz.personal` v1.0.0 (`LeaveCalculator`, `PayrollCalculator`, `EmployeeValidator`)
* **Специфікація:** розділ 5 `SRS_03_Персонал_плюс.html` (правила `BL-10` — `BL-80`)
* **Тест-аналітик:** Ходячих Нікіта (група ФБ-42)
* **Інженер з тестування (SDET):** Кудря Кіріл
* **Керівник тестування (Test Lead):** Сиротін Максим

---

## 1. Модуль `LeaveCalculator` (`BL-10` — `BL-40`)

Константи специфікації: `ACCRUAL_PER_MONTH = 2`, `MAX_ACCRUAL_PER_YEAR = 24`, `MAX_CARRY_OVER = 5`.

| ID | Вимога | Метод | Тип тесту | Вхідні дані | Очікуваний результат (Expected Result) | Статус рев'ю |
|---|---|---|---|---|---|---|
| TC-01 | BL-10 | `accrued(monthsWorked)` | Граничний | `monthsWorked = 0` | `0` | Pending |
| TC-02 | BL-10 | `accrued(monthsWorked)` | Позитивний | `monthsWorked = 6` | `12` | Pending |
| TC-03 | BL-10 | `accrued(monthsWorked)` | Граничний | `monthsWorked = 12` | `24` | Pending |
| TC-04 | BL-10 | `accrued(monthsWorked)` | Граничний (ліміт) | `monthsWorked = 13` | `24` (не перевищує `MAX_ACCRUAL_PER_YEAR`) | Pending |
| TC-05 | BL-10 | `accrued(monthsWorked)` | Негативний | `monthsWorked = -1` | Виняток `IllegalArgumentException` | Pending |
| TC-06 | BL-20 | `carryOver(unused)` | Граничний | `unused = 0` | `0` | Pending |
| TC-07 | BL-20 | `carryOver(unused)` | Позитивний | `unused = 3` | `3` | Pending |
| TC-08 | BL-20 | `carryOver(unused)` | Граничний | `unused = 5` | `5` | Pending |
| TC-09 | BL-20 | `carryOver(unused)` | Граничний (ліміт) | `unused = 6` | `5` (обмежується `MAX_CARRY_OVER`) | Pending |
| TC-10 | BL-20 | `carryOver(unused)` | Негативний | `unused = -1` | Виняток `IllegalArgumentException` | Pending |
| TC-11 | BL-30 | `durationDays(start, end)` | Граничний (1 будній день) | `start = 2026-11-16` (Пн), `end = 2026-11-16` (Пн) | `1` (обидві межі включно) | Pending |
| TC-12 | BL-30 | `durationDays(start, end)` | Граничний (1 вихідний день) | `start = 2026-11-14` (Сб), `end = 2026-11-14` (Сб) | `0` | Pending |
| TC-13 | BL-30 | `durationDays(start, end)` | Позитивний (повний тиждень) | `start = 2026-11-16` (Пн), `end = 2026-11-20` (Пт) | `5` | Pending |
| TC-14 | BL-30 | `durationDays(start, end)` | Позитивний (із вихідними) | `start = 2026-11-13` (Пт), `end = 2026-11-16` (Пн) | `2` (Сб та Нд не враховуються) | Pending |
| TC-15 | BL-30 | `durationDays(start, end)` | Негативний (`start > end`) | `start = 2026-11-20`, `end = 2026-11-16` | Виняток `IllegalArgumentException` | Pending |
| TC-16 | BL-30 | `durationDays(start, end)` | Негативний (`null`) | `start = null`, `end = 2026-11-16` | Виняток `IllegalArgumentException` | Pending |
| TC-17 | BL-40 | `canRequest(balance, duration)` | Граничний (`duration = 0`) | `balance = 5`, `duration = 0` | `false` | Pending |
| TC-18 | BL-40 | `canRequest(balance, duration)` | Граничний (`duration = 1`) | `balance = 5`, `duration = 1` | `true` | Pending |
| TC-19 | BL-40 | `canRequest(balance, duration)` | Граничний (`duration == balance`) | `balance = 5`, `duration = 5` | `true` | Pending |
| TC-20 | BL-40 | `canRequest(balance, duration)` | Граничний (`duration > balance`) | `balance = 5`, `duration = 6` | `false` | Pending |

---

## 2. Модуль `PayrollCalculator` (`BL-50` — `BL-70`)

Константи специфікації: `PIT_RATE = 0.18` (18%), `MILITARY_RATE = 0.05` (5%), `HOURS_PER_MONTH = 168`, `OVERTIME_COEF = 1.5`, округлення `HALF_UP` до 2 знаків.

| ID | Вимога | Метод | Тип тесту | Вхідні дані | Очікуваний результат (Expected Result) | Статус рев'ю |
|---|---|---|---|---|---|---|
| TC-21 | BL-50 | `net(gross)` | Граничний | `gross = 0.00` | `0.00` | Pending |
| TC-22 | BL-50 | `net(gross)` | Позитивний | `gross = 10000.00` | `7700.00` (`10000 - 1800 ПДФО - 500 ВЗ`) | Pending |
| TC-23 | BL-50 | `net(gross)` | Позитивний (округлення `HALF_UP`) | `gross = 12345.67` | `9506.17` (`9506.1659` округлено `HALF_UP`) | Pending |
| TC-24 | BL-50 | `net(gross)` | Негативний | `gross = -100.00` або `null` | Виняток `IllegalArgumentException` | Pending |
| TC-25 | BL-60 | `hourlyRate(monthly)` | Граничний | `monthly = 0.00` | `0.00` | Pending |
| TC-26 | BL-60 | `hourlyRate(monthly)` | Позитивний (точне ділення) | `monthly = 16800.00` | `100.00` | Pending |
| TC-27 | BL-60 | `hourlyRate(monthly)` | Граничний (округлення `HALF_UP`) | `monthly = 20000.00` | `119.05` (`20000 / 168 = 119.0476...` -> `HALF_UP`) | Pending |
| TC-28 | BL-60 | `hourlyRate(monthly)` | Негативний | `monthly = -1.00` або `null` | Виняток `IllegalArgumentException` | Pending |
| TC-29 | BL-70 | `overtimePay(monthly, hours)` | Граничний (`0` годин) | `monthly = 16800.00`, `hours = 0` | `0.00` | Pending |
| TC-30 | BL-70 | `overtimePay(monthly, hours)` | Позитивний (дробові години) | `monthly = 16800.00`, `hours = 2.5` | `375.00` (`100.00 * 1.5 * 2.5`) | Pending |
| TC-31 | BL-70 | `overtimePay(monthly, hours)` | Негативний | `monthly = 16800.00`, `hours = -1` | Виняток `IllegalArgumentException` | Pending |

---

## 3. Модуль `EmployeeValidator` (`BL-80`)

Константи специфікації: `MIN_AGE_YEARS = 16`, `TAX_ID_LENGTH = 10`.

| ID | Вимога | Метод | Тип тесту | Вхідні дані | Очікуваний результат (Expected Result) | Статус рев'ю |
|---|---|---|---|---|---|---|
| TC-32 | BL-80 | `isValidEmail(email)` | Позитивний | `"ivan.petrenko@personal.plus"` | `true` | Pending |
| TC-33 | BL-80 | `isValidEmail(email)` | Негативний (без зони / `@` / `null`) | `"ivan@localhost"`, `""`, `null` | `false` для кожного випадку | Pending |
| TC-34 | BL-80 | `isValidTaxId(taxId)` | Позитивний (рівно 10 цифр) | `"1234567890"` | `true` | Pending |
| TC-35 | BL-80 | `isValidTaxId(taxId)` | Граничний (9 цифр) | `"123456789"` | `false` | Pending |
| TC-36 | BL-80 | `isValidTaxId(taxId)` | Граничний (11 цифр) | `"12345678901"` | `false` | Pending |
| TC-37 | BL-80 | `isValidTaxId(taxId)` | Негативний (літери / `null`) | `"12345abcde"`, `""`, `null` | `false` | Pending |
| TC-38 | BL-80 | `isHireDateValid(hireDate, today)` | Позитивний (дата в минулому) | `hireDate = 2026-11-11`, `today = 2026-11-12` | `true` | Pending |
| TC-39 | BL-80 | `isHireDateValid(hireDate, today)` | Граничний (сьогодні) | `hireDate = 2026-11-12`, `today = 2026-11-12` | `true` | Pending |
| TC-40 | BL-80 | `isHireDateValid(hireDate, today)` | Граничний (завтра) | `hireDate = 2026-11-13`, `today = 2026-11-12` | `false` | Pending |
| TC-41 | BL-80 | `isHireDateValid(hireDate, today)` | Негативний (`null`) | `hireDate = null`, `today = 2026-11-12` | Виняток `IllegalArgumentException` | Pending |
| TC-42 | BL-80 | `isOldEnough(birthDate, hireDate)` | Граничний (рівно 16 років) | `birthDate = 2010-11-12`, `hireDate = 2026-11-12` | `true` | Pending |
| TC-43 | BL-80 | `isOldEnough(birthDate, hireDate)` | Граничний (16 років без 1 дня) | `birthDate = 2010-11-13`, `hireDate = 2026-11-12` | `false` | Pending |
| TC-44 | BL-80 | `isOldEnough(birthDate, hireDate)` | Негативний (`null`) | `birthDate = null`, `hireDate = 2026-11-12` | Виняток `IllegalArgumentException` | Pending |
| TC-45 | BL-80 | `validate(...)` | Позитивний (усі поля коректні) | `email="ivan@personal.plus"`, `taxId="1234567890"`, `birth=2000-01-01`, `hire=2026-11-12`, `today=2026-11-12` | Порожній список `[]` | Pending |
| TC-46 | BL-80 | `validate(...)` | Негативний (помилки в усіх полях) | `email="bad"`, `taxId="123"`, `birth=2012-01-01`, `hire=2026-12-01`, `today=2026-11-12` | Список `["EMAIL", "TAX_ID", "HIRE_DATE", "AGE"]` | Pending |
