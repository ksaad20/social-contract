````markdown
# Expense Model

## 1. Purpose

The expense model defines how Social Contract Android records, categorizes, validates, calculates, and presents monetary costs associated with land cultivation.

The model is designed to preserve an auditable relationship between:

```text
Expense
   ↓
Cultivation
   ↓
Contract
   ↓
Settlement
````

Every cultivation expense should be independently identifiable rather than hidden inside a single total.

---

## 2. Expense Concept

An expense represents a monetary cost incurred in connection with cultivation.

Examples include:

* Labor
* Fertilizer
* Seed
* Irrigation
* Communication
* Transportation
* Machinery
* Pesticides
* Land preparation
* Harvesting
* Storage
* Other cultivation-related costs

A typical expense contains:

```text
Expense
├── id
├── cultivationId
├── category
├── amount
├── currency
├── description
├── date
└── evidence
```

---

## 3. Expense Identity

Every expense should have a stable internal identifier.

```text
id
```

The identifier should remain stable after creation.

This permits an expense to be referenced by:

* Evidence
* Calculations
* Exports
* Audit records
* Contract records

---

## 4. Cultivation Association

Every cultivation expense should be associated with the relevant cultivation record.

Conceptually:

```text
Cultivation
└── Expenses
    ├── Expense 1
    ├── Expense 2
    ├── Expense 3
    └── ...
```

This prevents expenses from becoming detached from the cultivation activity that generated them.

Where the cultivation itself belongs to a contract, the relationship becomes:

```text
Contract
   ↓
Cultivation
   ↓
Expense
```

---

## 5. Expense Categories

The MVP should support explicit expense categories.

Recommended categories include:

```text
LABOR
FERTILIZER
SEED
IRRIGATION
COMMUNICATION
TRANSPORTATION
MACHINERY
PESTICIDE
LAND_PREPARATION
HARVESTING
STORAGE
OTHER
```

Categories should be represented consistently throughout the application.

The user interface may display human-readable names while the domain model retains stable identifiers.

---

## 6. Labor Costs

Labor costs represent payments or liabilities associated with human work.

Examples:

* Land preparation
* Planting
* Weeding
* Irrigation work
* Harvesting
* Loading
* Processing

Where practical, labor expenses may later be expanded into:

```text
worker count
days worked
hours worked
rate per day
rate per hour
total amount
```

For the MVP, the final monetary amount is sufficient.

---

## 7. Fertilizer Costs

Fertilizer expenses include purchases or application-related costs.

A detailed implementation may record:

```text
fertilizer type
quantity
quantity unit
unit price
total amount
```

For example:

```text
Urea
Quantity: 50 kg
Unit price: BDT 30/kg
Total: BDT 1,500
```

The detailed quantity information should not replace the monetary expense record.

---

## 8. Seed Costs

Seed expenses may include:

* Seed purchase
* Seed treatment
* Seed transportation
* Other directly attributable seed costs

A detailed seed record may eventually contain:

```text
seed type
quantity
quantity unit
unit price
total amount
```

---

## 9. Irrigation Costs

Irrigation costs may include:

* Water charges
* Pump operation
* Fuel or electricity
* Irrigation labor
* Equipment rental
* Maintenance directly associated with irrigation

The expense model should allow irrigation expenses to be recorded independently.

---

## 10. Communication Costs

Communication expenses may include costs directly associated with managing the cultivation activity.

Examples:

* Mobile communication
* Contract-related communication
* Coordination costs
* Data services used specifically for cultivation management

The application should avoid automatically assigning ordinary personal communication costs to a cultivation contract unless the user explicitly records them as cultivation expenses.

---

## 11. Transportation Costs

Transportation expenses may include:

* Transporting seeds
* Transporting fertilizer
* Transporting harvested crops
* Transporting equipment
* Travel directly associated with cultivation operations

The expense should be recorded with enough context to explain its relationship to cultivation.

---

## 12. Machinery Costs

Machinery expenses may include:

* Equipment rental
* Tractor usage
* Pump usage
* Maintenance
* Fuel
* Operator costs

If machinery is shared across multiple cultivation activities, allocation rules may be required in a future version.

The MVP should record the amount explicitly assigned to the cultivation activity.

---

## 13. Other Costs

The `OTHER` category exists for legitimate cultivation costs that do not fit the predefined categories.

An `OTHER` expense should normally include a description.

Example:

```text
Category: OTHER
Amount: BDT 2,000
Description: Temporary storage fee
```

This prevents unexplained miscellaneous amounts from entering the financial calculation.

---

## 14. Monetary Representation

Expense amounts should be represented using a numeric type suitable for monetary calculations.

The application should avoid binary floating-point arithmetic for financial values where exact currency precision is required.

A production implementation should prefer:

```text
integer minor units
```

or a decimal representation.

For example, BDT 1,250.50 could be represented internally as:

```text
125050 poisha
```

if the application's monetary model uses Bangladesh Taka minor units.

The displayed amount would then be:

```text
BDT 1,250.50
```

The exact implementation should remain consistent throughout the application.

---

## 15. Currency

Every expense should identify its currency.

Example:

```text
currency = BDT
```

The application should not silently convert currencies.

If multi-currency support is introduced, conversion should be explicit and should preserve:

* Original amount
* Original currency
* Conversion rate
* Converted amount
* Conversion date or source

For the MVP, a single configured currency may be sufficient.

---

## 16. Expense Date

An expense should have a date representing when the cost was incurred or recorded.

Example:

```text
2026-08-12
```

Dates are useful for:

* Chronological records
* Auditing
* Expense analysis
* Contract documentation
* Future reporting

The application should distinguish the transaction date from the date the expense was entered if both are required.

---

## 17. Description

A description provides contextual information that cannot be represented by structured fields.

Examples:

```text
Field preparation labor
50 kg urea fertilizer
Diesel for irrigation pump
Transport to local market
```

Descriptions should supplement structured fields rather than replace them.

---

## 18. Evidence

Expenses may have supporting evidence.

Examples:

* Receipts
* Invoices
* Photographs
* Payment confirmations
* Written acknowledgements

Conceptually:

```text
Expense
├── Financial data
└── Evidence
    ├── Receipt
    ├── Photograph
    └── Payment record
```

Evidence should remain associated with the specific expense whenever possible.

---

## 19. Validation Rules

An expense should satisfy the following basic rules:

```text
Category is not blank.

Amount is not negative.

Currency is not blank.

Description may be optional for predefined categories.

Description should normally be present for OTHER.

The associated cultivation exists.

The expense identifier is unique.
```

A zero-value expense may be rejected by the UI and validation layer because it does not represent a meaningful monetary transaction.

If zero-value records are required for accounting purposes in a future version, that should be an explicit design decision.

---

## 20. Total Cultivation Cost

The total cultivation cost is calculated from individual expense records.

```text
Total cultivation cost
=
Σ expense.amount
```

For example:

```text
Labor             BDT 10,000
Fertilizer         BDT 5,000
Seed               BDT 3,000
Irrigation         BDT 2,000
Transportation     BDT 1,000
--------------------------------
Total              BDT 21,000
```

The total should be calculated rather than manually entered.

---

## 21. Expense Corrections

An incorrect expense should not be silently overwritten when historical traceability is important.

Possible approaches include:

```text
Edit with audit history
```

or:

```text
Original expense
       ↓
Correction record
```

The MVP may allow controlled editing while keeping the architecture compatible with future audit functionality.

---

## 22. Expense Deletion

Deletion should be treated carefully because expenses affect profit and settlement.

If an expense has already been included in a completed settlement, destructive deletion can invalidate the historical calculation.

A future audit-oriented implementation should prefer:

```text
VOIDED
```

or another non-destructive state over permanent deletion.

---

## 23. Expense Calculation Flow

The financial flow is:

```text
Individual expenses
       ↓
Category validation
       ↓
Amount validation
       ↓
Expense persistence
       ↓
CostCalculator
       ↓
Total cultivation cost
       ↓
Profit calculation
       ↓
Settlement calculation
```

The `CostCalculator` should not need to know where an expense came from.

It should operate on validated expense data.

---

## 24. Example Expense Set

A cultivation may contain:

```text
Expense 001
Category: Labor
Amount: BDT 10,000

Expense 002
Category: Fertilizer
Amount: BDT 5,000

Expense 003
Category: Seed
Amount: BDT 3,000

Expense 004
Category: Irrigation
Amount: BDT 2,000

Expense 005
Category: Transportation
Amount: BDT 1,000
```

The calculated result is:

```text
Total cultivation cost = BDT 21,000
```

This value can then be passed to the profit calculation.

---

## 25. Relationship to Profit

Given:

```text
Harvest value = BDT 225,000
Total cultivation cost = BDT 21,000
```

The application calculates:

```text
Net profit
=
225,000 - 21,000
=
BDT 204,000
```

The expense model therefore forms one of the primary inputs to settlement.

---

## 26. Relationship to Settlement

Settlement should consume the calculated financial result rather than independently reconstructing expenses.

Conceptually:

```text
Expenses
   ↓
CostCalculator
   ↓
Total Cost
   ↓
Profit Calculator
   ↓
Net Profit
   ↓
SettlementCalculator
   ↓
Party Shares
```

This keeps financial logic centralized and reduces the risk of inconsistent calculations between screens.

---

## 27. Export Representation

CSV export should expose enough information to reconstruct the expense record.

Recommended fields include:

```text
expense_id
contract_id
cultivation_id
date
category
amount
currency
description
```

Where available, additional structured fields may be included.

The export should clearly distinguish individual expenses from calculated totals.

---

## 28. PDF Representation

A contract PDF may summarize expenses in a table:

```text
Category          Amount
--------------------------------
Labor             BDT 10,000
Fertilizer         BDT 5,000
Seed               BDT 3,000
Irrigation         BDT 2,000
Transportation     BDT 1,000
--------------------------------
Total              BDT 21,000
```

The document should make clear that the total is derived from the listed expenses.

---

## 29. Testing Requirements

Expense-related tests should cover:

```text
Valid expense
Blank category
Negative amount
Zero amount
Blank currency
Decimal amount
Large amount
Optional description
OTHER category
Multiple expense categories
Total cost calculation
```

The tests should be independent of Android UI.

---

## 30. Design Objective

The expense model should answer:

> What did the cultivation actually cost, what was each cost for, and what evidence supports each recorded expense?

The model therefore prioritizes:

1. Traceability
2. Explicit categories
3. Monetary accuracy
4. Reproducible totals
5. Evidence association
6. Compatibility with future auditing

```
```

