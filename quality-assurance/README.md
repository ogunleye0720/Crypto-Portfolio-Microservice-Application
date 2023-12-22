# Quality Assurance for Crypto-Portfolio app
[![Full Test](https://github.com/kargolek/crypto-portfolio-microservices/workflows/qa-main-full-test/badge.svg)](https://kargolek.github.io/crypto-portfolio-microservices/main/)
[![Regression Test](https://github.com/kargolek/crypto-portfolio-microservices/workflows/qa-regression-test-chrome/badge.svg)](https://kargolek.github.io/crypto-portfolio-microservices/regression/chrome)
[![Regression Test](https://github.com/kargolek/crypto-portfolio-microservices/workflows/qa-regression-test-edge/badge.svg)](https://kargolek.github.io/crypto-portfolio-microservices/regression/edge)
[![Regression Test](https://github.com/kargolek/crypto-portfolio-microservices/workflows/qa-regression-test-firefox/badge.svg)](https://kargolek.github.io/crypto-portfolio-microservices/regression/firefox)

Welcome to the Quality Assurance section of the Crypto Portfolio Microservices project. This repository contains two essential modules, `qa-api` and `qa-ui`, dedicated to ensuring the reliability, functionality, and performance of the Crypto Portfolio application.

## Table of content
- [QA-API](#qa-api-modulehttpsgithubcomkargolekcrypto-portfolio-microservicestreemainquality-assuranceqa-api)
- [QA-UI](#qa-ui-modulehttpsgithubcomkargolekcrypto-portfolio-microservicestreemainquality-assuranceqa-ui)

## [QA-API Module](https://github.com/kargolek/crypto-portfolio-microservices/tree/main/quality-assurance/qa-api)

The `qa-api` module is designed to conduct comprehensive API testing for the Crypto Portfolio application. It encompasses a suite of tests that validate the functionality of various endpoints for **[crypto-price-service](https://github.com/kargolek/crypto-portfolio-microservices/tree/main/crypto-price-service)** and **[wallet-service](https://github.com/kargolek/crypto-portfolio-microservices/tree/main/wallet-service)**, data integrity, error handling, and the overall robustness of the API.

Key features and components of the `qa-api` module include:

- **Test Framework**: Built using well-established testing frameworks and libraries, such as **Cucumber, JUnit5, Maven Surefire and Allure**, to ensure structured and maintainable tests.
- **Endpoint Testing**: Thoroughly tests each API endpoint, covering both positive and negative scenarios to validate expected behavior.
- **Data Integrity**: Validates the accuracy and consistency of data returned by the API to ensure a reliable user experience.

## [QA-UI Module](https://github.com/kargolek/crypto-portfolio-microservices/tree/main/quality-assurance/qa-ui)

The `qa-ui` module focuses on conducting UI tests for the Crypto Portfolio application using Selenium 4 and JUnit 5. This module ensures that the user interface remains functional, visually appealing, and responsive across different browsers.

Highlighted aspects of the `qa-ui` module:

- **Selenium 4, JUnit 5 and Maven Surefire**: Utilizes Selenium 4, a powerful web testing framework, and JUnit 5, a widely used testing framework for Java applications.
- **Cross-Browser Testing**: Validates the compatibility of the application across multiple browsers, detecting any inconsistencies in behavior or layout.
- **Visual Regression Testing**: Detects any unintended visual changes between different versions of the application, safeguarding against design regressions.
- **Comprehensive Test Reports with Allure**: We employ the Allure framework to generate detailed test reports that offer an interactive visual representation of test results. These reports come enriched with attachments such as screenshots, video recordings, and browser logs. This feature enhances our ability to understand test outcomes and quickly diagnose issues.
- **Test parallelization**: By running tests in parallel mode, the execution time has been significantly reduced, resulting in a more efficient testing process and quicker feedback on the status of the crypto portfolio microservices.

Feel free to explore the `qa-api` and `qa-ui` directories for detailed information, test scripts, and resources related to the quality assurance process.