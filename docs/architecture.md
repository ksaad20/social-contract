````markdown
# Architecture

## 1. Overview

Social Contract Android is an offline-first Android application for recording, calculating, validating, documenting, and settling land-share cultivation agreements.

The application is designed around a small set of explicit domain concepts:

- Parties
- Land
- Cultivation
- Contracts
- Expenses
- Harvests
- Settlement
- Evidence
- Contract documents

The architecture separates user interface concerns from business logic and persistence so that financial calculations and contract rules can be tested independently of Android UI components.

---

## 2. Architectural Principles

### 2.1 Offline-first

Core contract functionality must work without an internet connection.

The application should not require network access to:

- Create a party
- Register land
- Create a cultivation contract
- Record cultivation expenses
- Record harvest information
- Calculate revenue
- Calculate profit
- Calculate settlement
- Validate contract information
- Export contract data

Network functionality, if introduced later, should be additive rather than a dependency of the core application.

### 2.2 Explicit domain logic

Financial calculations must remain deterministic and independent of UI code.

For example:

```text
Harvest value = Harvest quantity × Unit price

Total cultivation cost =
    Labor
    + Fertilizer
    + Seed
    + Irrigation
    + Communication/transportation
    + Other costs

Net profit =
    Harvest value - Total cultivation cost

Party settlement =
    Net profit × Party share percentage
````

The application should not calculate these values directly inside screens.

### 2.3 Separation of concerns

The application is divided conceptually into:

```text
Presentation
    ↓
Use Cases
    ↓
Domain
    ↓
Repositories
    ↓
Database
```

PDF and CSV generation are treated as output services rather than domain logic.

---

## 3. Project Structure

The Android application follows the standard Android source-set structure:

```text
app/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── socialcontract/
    │   ├── res/
    │   └── AndroidManifest.xml
    │
    └── test/
        └── java/
            └── com/
                └── socialcontract/
```

Documentation is maintained separately:

```text
docs/
├── index.html
├── architecture.md
├── contract-model.md
├── expense-model.md
├── settlement-model.md
├── evidence-model.md
└── legal-research.md
```

---

## 4. Presentation Layer

The presentation layer contains Android UI components such as screens and navigation.

Examples include:

```text
AddPartyScreen
PartyDetailScreen

LandListScreen
LandDetailScreen

AddCultivationScreen
```

Presentation components should:

* Display application state
* Collect user input
* Invoke appropriate use cases
* Display validation errors
* Display calculation results
* Avoid implementing core financial calculations directly

A screen should not independently calculate settlement or total cultivation costs.

---

## 5. Domain Layer

The domain layer represents the application's core concepts and rules.

Representative domain objects include:

```text
Party
Land
Cultivation
Contract
Expense
Harvest
Settlement
Evidence
```

The domain layer should remain as independent from Android-specific APIs as practical.

This allows business rules to be tested using ordinary JVM unit tests.

---

## 6. Calculation Layer

Calculations are isolated into dedicated components.

Examples:

```text
CostCalculator
HarvestValueCalculator
SettlementCalculator
ShareValidator
```

Responsibilities:

### CostCalculator

Calculates total cultivation expenditure from individual cost categories.

### HarvestValueCalculator

Calculates the monetary value of harvested production.

### SettlementCalculator

Calculates the monetary entitlement of each participating party.

### ShareValidator

Ensures that settlement shares are valid.

For a two-party contract:

```text
Cultivator share + Landowner share = 100%
```

and neither share may be outside:

```text
0% ≤ share ≤ 100%
```

---

## 7. Validation Layer

Validation components enforce required data constraints before persistence or settlement.

Examples:

```text
ContractValidator
ExpenseValidator
```

Validation should occur before invalid data is accepted into the persistent domain state.

Validation is separate from UI presentation so that the same rules can be tested independently.

---

## 8. Use-Case Layer

Use cases represent application actions.

Examples include:

```text
AddPartyUseCase
AddLandUseCase
CalculateProfitUseCase
CalculateSettlementUseCase
```

A use case coordinates domain operations without placing business rules inside UI screens.

For example:

```text
CalculateSettlementUseCase
    ↓
retrieve contract
    ↓
retrieve financial information
    ↓
calculate revenue
    ↓
calculate cultivation costs
    ↓
calculate net profit
    ↓
validate shares
    ↓
calculate party settlement
    ↓
return settlement result
```

---

## 9. Repository Layer

Repositories provide an abstraction between application logic and persistence.

Examples include:

```text
PartyRepository
LandRepository
CultivationRepository
ContractRepository
```

Use cases should depend on repository abstractions rather than directly manipulating database implementation details.

This makes the persistence implementation replaceable.

---

## 10. Persistence Layer

The persistence layer is responsible for storing application data locally.

The database provider is responsible for creating or exposing the application's database.

Conceptually:

```text
DatabaseProvider
       │
       ├── Party data
       ├── Land data
       ├── Cultivation data
       ├── Contract data
       ├── Expense data
       ├── Harvest data
       └── Evidence data
```

Database implementation details should not leak into presentation components.

---

## 11. Contract Document Layer

Contracts can be exported into human-readable documents.

The application includes:

```text
PdfContractGenerator
ContractShareManager
CsvExportManager
```

### PdfContractGenerator

Creates a PDF representation of a contract.

### ContractShareManager

Uses Android's sharing mechanism to make generated contract documents available to other applications.

### CsvExportManager

Exports structured contract information for analysis, backup, or external processing.

These components should consume domain/application data rather than directly querying UI state.

---

## 12. Evidence Layer

Evidence is treated as a first-class concept rather than an informal attachment.

Evidence may eventually include:

* Photographs
* Receipts
* Documents
* Notes
* Transaction records
* Harvest records
* Expense records

Evidence should be associated with the relevant contract, cultivation activity, expense, harvest, or settlement event.

The architecture should permit evidence to be added without changing the fundamental contract model.

---

## 13. Data Flow

A typical contract workflow is:

```text
User
  ↓
Screen
  ↓
Use Case
  ↓
Validator
  ↓
Repository
  ↓
Database
```

For financial settlement:

```text
Contract
   +
Harvest
   +
Expenses
   +
Share agreement
        ↓
Calculation layer
        ↓
Profit
        ↓
Settlement
        ↓
PDF / CSV / UI
```

---

## 14. Testing Architecture

Business logic is tested independently from Android UI.

Tests are stored under:

```text
app/src/test/java/com/socialcontract/
```

Current test groups include:

```text
calculation/
├── CostCalculatorTest.kt
├── HarvestValueCalculatorTest.kt
├── SettlementCalculatorTest.kt
└── ShareValidatorTest.kt

validation/
├── ContractValidatorTest.kt
└── ExpenseValidatorTest.kt

domain/
└── ContractTest.kt
```

The primary objective is to ensure that deterministic financial and validation rules remain correct as the application evolves.

---

## 15. Security and Data Integrity

The application should follow these principles:

1. Never silently modify recorded financial values.
2. Preserve the original recorded expense amount.
3. Preserve the original recorded harvest quantity.
4. Validate settlement shares before settlement.
5. Keep contract identifiers stable.
6. Avoid destructive updates where historical records are required.
7. Clearly distinguish recorded values from calculated values.
8. Ensure exported documents correspond to the current contract state.
9. Avoid storing secrets or credentials in source code.
10. Keep sensitive contract information local unless the user explicitly chooses to share or synchronize it.

---

## 16. Architectural Evolution

The MVP should remain intentionally small.

Future capabilities may include:

```text
Cloud synchronization
Multi-device access
Digital signatures
Identity verification
Receipt capture
Geolocation
Advanced evidence management
Contract versioning
Audit trails
Dispute records
Analytics
```

These capabilities should be introduced without coupling the core calculation engine to external services.

The fundamental architecture should therefore remain:

```text
UI
 ↓
Use Cases
 ↓
Domain Rules
 ↓
Repositories
 ↓
Local Persistence
```

with document generation and external sharing operating as supporting services.

---

## 17. Design Goal

The central architectural objective is:

> Keep the contract record understandable, the financial calculations deterministic, the evidence traceable, and the application usable without requiring an external service.

This makes the MVP suitable as a foundation for progressively more sophisticated land-share cultivation contract management.

```
```

