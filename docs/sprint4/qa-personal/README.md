# Автотести продукту П3 «Персонал+» (JUnit 5 + Selenium WebDriver 4)

Maven-проєкт автоматизованого тестування модуля бізнес-логіки `ua.kpi.tzyapz.personal` (версія v1.0.0) та веб-інтерфейсу demo-стенду OrangeHRM OS 5.9 (Спринт 4, команда **Personal QA Team**, група ФБ-42).

## 1. Вимоги до середовища
- **JDK:** 17 (Eclipse Temurin / OpenJDK)
- **Apache Maven:** 3.9+
- **Браузер:** Google Chrome (для UI-тестів у headless-режимі; драйвер завантажується автоматично через Selenium Manager)

## 2. Структура проєкту
```text
qa-personal/
├── pom.xml                                              # JDK 17, JUnit 5.10.2, Selenium 4.21.0, Surefire 3.2.5
├── README.md
├── .gitignore
├── docs/
│   ├── surefire-summary.md                              # Протокол запуску юніт- та UI-тестів
│   └── ui-scenarios.md                                  # Опис UI-сценаріїв, кроків і локаторів
├── selenium-ide/
│   ├── personal.side                                    # Проєкт Selenium IDE (TC-001, TC-002)
│   └── export/                                          # Експортовані Java-класи з Selenium IDE
└── src/
    ├── main/java/ua/kpi/tzyapz/personal/                # Модуль бізнес-логіки v1 (без змін)
    │   ├── EmployeeValidator.java
    │   ├── LeaveCalculator.java
    │   └── PayrollCalculator.java
    └── test/java/ua/kpi/tzyapz/personal/
        ├── EmployeeValidatorTest.java                   # Юніт-тести BL-80 (Сиротін Максим)
        ├── LeaveCalculatorTest.java                     # Юніт-тести BL-10..BL-40 (Ходячих Нікіта)
        ├── PayrollCalculatorTest.java                   # Юніт-тести BL-50..BL-70 (Кудря Кіріл)
        └── ui/                                          # UI-тести Selenium WebDriver (Кудря Кіріл)
            ├── BaseUiTest.java
            ├── AuthUiTest.java
            └── ValidationUiTest.java
