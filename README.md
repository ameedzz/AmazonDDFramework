# 🛒 Amazon Data-Driven Test Automation Framework
### STA Mini Project 9a — Selenium + TestNG + Apache POI

![Build](https://img.shields.io/badge/Build-Passing-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.9.13-red)
![Tests](https://img.shields.io/badge/Tests-12%20Passed-brightgreen)

---

## 📋 Overview

A **Data-Driven Test Automation Framework** built using **Selenium WebDriver**, **TestNG**, and **Apache POI** that automates product search testing on **Amazon India (amazon.in)**.

The framework reads test data from an Excel file and runs 12 automated search test cases across various product categories, generating detailed **ExtentReports HTML dashboards** after each run.

---

## ✅ Test Results

| Metric | Value |
|--------|-------|
| Total Tests | 12 |
| Tests Passed | ✅ 12 |
| Tests Failed | ❌ 0 |
| Tests Skipped | ⏭ 0 |
| Pass Rate | 100% |
| Total Time | ~8 minutes |

---

## 🏗️ Project Structure

```
AmazonDDFramework/
│
├── 📄 pom.xml                          ← Maven dependencies & build config
│
├── 📁 src/
│   ├── main/java/com/framework/
│   │   ├── base/
│   │   │   └── BaseTest.java           ← Browser setup/teardown (Brave/Chrome)
│   │   ├── pages/
│   │   │   └── AmazonHomePage.java     ← Page Object for amazon.in
│   │   └── utils/
│   │       ├── ExcelUtils.java         ← Apache POI Excel reader
│   │       ├── TestDataProvider.java   ← TestNG DataProvider
│   │       ├── ExtentReportManager.java← HTML report generator
│   │       └── ScreenshotUtils.java    ← Screenshot capture on failure
│   │
│   └── test/
│       ├── java/com/framework/tests/
│       │   └── AmazonSearchTest.java   ← Main test class (12 test cases)
│       └── resources/
│           ├── testng.xml              ← TestNG suite configuration
│           └── log4j2.xml             ← Logging configuration
│
├── 📁 testdata/
│   └── AmazonTestData.xlsx            ← Excel test data (12 search keywords)
│
├── 📁 dashboard/
│   └── index.html                     ← Visual test dashboard
│
├── 📁 reports/                        ← Auto-generated after test run
│   └── AmazonTestReport_*.html        ← ExtentReports HTML report
│
└── 📁 logs/
    └── amazon-framework.log           ← Execution logs
```

---

## 🧪 Test Data (AmazonTestData.xlsx)

The framework is completely data-driven — all test inputs come from Excel:

| # | Search Keyword | Category | Expected | Min Results |
|---|----------------|----------|----------|-------------|
| 1 | iPhone 15 | Electronics | pass | 5 |
| 2 | Samsung Galaxy S24 | Electronics | pass | 5 |
| 3 | laptop | Computers | pass | 10 |
| 4 | Nike shoes | Footwear | pass | 5 |
| 5 | boAt earphones | Electronics | pass | 5 |
| 6 | kurta | Clothing | pass | 10 |
| 7 | pressure cooker | Kitchen | pass | 5 |
| 8 | Harry Potter books | Books | pass | 3 |
| 9 | yoga mat | Sports | pass | 5 |
| 10 | face wash | Beauty | pass | 10 |
| 11 | xyzabc123notaproduct | Invalid | fail | 0 |
| 12 | wireless mouse | Computers | pass | 5 |

> 💡 To add new test cases, simply add rows to `testdata/AmazonTestData.xlsx` — no code changes needed!

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 21 (Temurin) | Programming language |
| Selenium WebDriver | 4.18.1 | Browser automation |
| TestNG | 7.9.0 | Test framework |
| Apache POI | 5.2.5 | Excel data reading |
| ExtentReports | 5.1.1 | HTML report generation |
| WebDriverManager | 5.7.0 | Auto ChromeDriver setup |
| Log4j2 | 2.22.1 | Logging |
| Maven | 3.9.13 | Build & dependency management |

---

## ⚙️ Prerequisites

Before running this project, ensure you have:

- ✅ **Java 21** (Eclipse Temurin) — [Download](https://adoptium.net/temurin/releases/)
- ✅ **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi)
- ✅ **IntelliJ IDEA** (Community) — [Download](https://www.jetbrains.com/idea/download/)
- ✅ **Brave Browser** (or Google Chrome) — [Download](https://brave.com)
- ✅ Internet connection (for Amazon.in access)

---

## 🚀 Setup & Installation

### Step 1 — Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/AmazonDDFramework.git
cd AmazonDDFramework
```

### Step 2 — Open in IntelliJ IDEA
1. Launch IntelliJ IDEA
2. Click **File → Open** → Select the `AmazonDDFramework` folder
3. Click **Trust Project** when prompted
4. Wait for Maven to import dependencies (1–3 minutes)

### Step 3 — Configure Browser
If using **Brave Browser**, verify the path in `BaseTest.java`:
```java
options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
```
If using **Chrome**, remove the `setBinary` line.

### Step 4 — Mark Resources Folder
1. Right-click `src/test/resources`
2. Select **Mark Directory As → Test Resources Root**

### Step 5 — Verify SDK
1. Press `Ctrl+Alt+Shift+S` → Project Structure
2. Set SDK to **temurin-21**

---

## ▶️ Running Tests

### Via Maven (Recommended)
```bash
mvn clean test
```

### Via IntelliJ
Right-click `src/test/resources/testng.xml` → **Run 'testng.xml'**

### Run Specific Test
```bash
mvn clean test -Dtest=AmazonSearchTest
```

### Run with Different Browser
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

---

## 📊 Reports

After running tests, reports are generated automatically:

```
reports/
└── AmazonTestReport_2026-03-08_21-53-24.html  ← Open in browser
```

Open the report:
```bash
# Windows
start reports\AmazonTestReport_*.html
```

The report shows:
- ✅ Pass/Fail status for each test
- 📸 Screenshots on failure
- 📋 Step-by-step execution logs
- ⏱️ Timeline of test execution
- 📊 Donut chart of results

---

## 🔑 Key Features

- **Data-Driven** — Test data from Excel, zero code changes for new tests
- **Anti-Bot Detection** — Configured to bypass Amazon's automation detection
- **URL Fallback Search** — Uses direct URL search if search box fails
- **Auto Screenshots** — Captures screenshots on test failure
- **Detailed Reports** — Beautiful ExtentReports HTML dashboard
- **Multi-Browser** — Supports Chrome, Brave, Firefox, Edge
- **Logging** — Complete Log4j2 execution logs
- **Thread-Safe** — ThreadLocal WebDriver for parallel execution

---

## 📁 Framework Architecture

```
Test Data (Excel)
      ↓
TestDataProvider (@DataProvider)
      ↓
AmazonSearchTest (@Test)
      ↓
AmazonHomePage (Page Object)
      ↓
Selenium WebDriver (Brave/Chrome)
      ↓
Amazon.in
      ↓
ExtentReports (HTML Dashboard)
```

---

## 🐛 Troubleshooting

| Error | Fix |
|-------|-----|
| `mvn not recognized` | Add Maven bin to PATH environment variable |
| `JAVA_HOME not set` | Set JAVA_HOME to JDK 21 folder |
| `No POM in directory` | Run `cd AmazonDDFramework` first, then `mvn clean test` |
| `Browser not found` | Verify Brave path in `BaseTest.java` |
| `NoClassDefFoundError` | Use `mvn clean test` instead of IntelliJ run button |

---

## 👨‍💻 Author

**STA Mini Project**
Data-Driven Framework using Selenium + TestNG + Apache POI
Amazon India (amazon.in) · March 2026

By
**Abdul Hameed M**

---
