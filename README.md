# Selenium Automation Project - Hybrid Framework

This project is an automated testing framework built using **Selenium**, **TestNG**, **Page Object Model (POM)**, **Data-Driven Testing**, **Extent Reports**, and **TestNG Listeners**. 
It is designed for automating web application testing with advanced features like **screenshots** on failure, **detailed reports**, and **modular test organization**.

## Key Features

- **Page Object Model (POM)**: Decouples UI elements from test logic, making maintenance and scalability easier.
- **TestNG**: Manages test execution and generates test reports.
- **Extent Reports**: Produces rich HTML reports with test results and logs.
- **TestNG Listeners**: Captures test events like success, failure, and screenshots.
- **Screenshots on Failure**: Automatically captures screenshots on test failures for better debugging.

## Project Structure
### Explanation of Key Directories and Files:
- **/main/java/drivermanagerfactory**: Contains classes for managing WebDriver initialization and setup.
- **/main/java/listeners**: Custom TestNG listeners for tracking and logging test execution events.
- **/main/java/pageobjects**: Page Object Model (POM) classes for representing web pages.
- **/main/java/testdata**: Test data files used for data-driven testing (Excel, CSV, etc.).
- **/main/java/testsetup**: Classes for configuring test environments and global setup.
- **/main/java/testutils**: Utility classes like browser actions, Extent Reports integration, and waits.
- **/test/java/testcases**: Test scripts that implement the actual test logic and interact with page objects.

- **/target/screenshots**: Folder where screenshots are saved for failed tests.
- **/target/test-output**: Contains test execution reports, including Extent Reports.

---
## Prerequisites

- **Java 8 or higher**
- **Maven**
- **Selenium WebDriver**
- **ChromeDriver** (or other relevant WebDriver)
- **TestNG**

## Setup Instructions

### Key Points:
- **Hybrid Framework**: Emphasizes modular design with POM, data-driven approach, and listeners.
- **Test Reports**: Detailed reports using Extent Reports and automatic screenshot capture for failures.
- **Simple Setup**: Instructions for setting up Java, Maven, WebDriver, and running tests.
- **Clean and Concise**: The README provides a clear structure to help new developers set up and contribute to the project