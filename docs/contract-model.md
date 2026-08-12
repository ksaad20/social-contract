````markdown id="r7m4qx"
# Contract Model

## 1. Purpose

The contract model defines the structure of a land-share cultivation agreement within Social Contract Android.

A contract connects:

- Land
- Landowner
- Cultivator
- Crop
- Cultivation period
- Cultivation costs
- Harvest
- Revenue
- Settlement shares
- Evidence
- Contract status

The model is designed to preserve a clear distinction between **recorded facts**, **agreed terms**, and **calculated values**.

---

## 2. Contract Identity

Every contract should have a stable internal identifier and a human-readable contract number.

```text
id
contractNumber
````

### `id`

The internal application identifier.

It should remain stable throughout the lifetime of the contract.

### `contractNumber`

The human-readable identifier presented to users and included in exported documents.

Example:

```text
SC-2026-001
```

---

## 3. Parties

A minimum cultivation contract normally identifies two primary parties:

```text
Landowner
Cultivator
```

The contract should reference parties by stable identifiers rather than duplicating their complete records.

Conceptually:

```text
Contract
├── landownerPartyId
└── cultivatorPartyId
```

This allows party information to be maintained independently while preserving the relationship with the contract.

---

## 4. Land

A contract must identify the land on which cultivation occurs.

The contract references the land record:

```text
landId
```

The land record should contain the relevant land information, such as:

* Description
* Area
* Area unit
* Location information
* Ownership information
* Optional evidence

The contract should not silently overwrite the underlying land record.

---

## 5. Crop

The contract identifies the crop being cultivated.

Example:

```text
cropName = "Rice"
```

The crop name is part of the contract because it describes the cultivation agreement applicable during that contract period.

---

## 6. Cultivation Period

A contract may contain:

```text
startDate
endDate
cultivationDurationDays
```

The dates establish the intended cultivation period.

The duration may be recorded explicitly when the agreement specifies a fixed number of days.

Example:

```text
Start: 01 Aug 2026
End:   29 Nov 2026
Duration: 120 days
```

If both dates and duration are stored, the application should avoid silently producing contradictory values.

---

## 7. Settlement Shares

The contract records the agreed distribution of the relevant settlement amount.

For a two-party agreement:

```text
cultivatorSharePercent
landownerSharePercent
```

The normal invariant is:

```text
cultivatorSharePercent
+
landownerSharePercent
=
100%
```

Both values must be within:

```text
0% ≤ share ≤ 100%
```

The application should validate this agreement before allowing settlement.

---

## 8. Cultivation Costs

Cultivation expenses should be recorded individually rather than as one unexplained total.

Typical categories include:

```text
Labor
Fertilizer
Seed
Irrigation
Communication
Transportation
Machinery
Pesticides
Other
```

A contract may therefore have many expense records:

```text
Contract
└── Expenses
    ├── Labor
    ├── Fertilizer
    ├── Seed
    ├── Irrigation
    ├── Communication
    ├── Transportation
    └── Other
```

The total cultivation cost is calculated from the underlying records.

It should not be treated as an independently editable value unless the application explicitly supports an adjustment mechanism.

---

## 9. Harvest

Harvest information should distinguish physical production from its monetary valuation.

Example:

```text
harvestQuantity = 5000
yieldUnit = "kg"
unitPrice = 45
```

The harvest value is calculated as:

```text
Harvest value
=
Harvest quantity × Unit price
```

Therefore:

```text
5000 kg × BDT 45/kg
=
BDT 225,000
```

The quantity and unit price should remain separately traceable.

---

## 10. Revenue

Expected revenue and realized revenue should not be confused.

### Expected revenue

An estimate made before or during cultivation.

### Realized revenue

Revenue actually obtained from the harvest or sale.

If both are supported, they should have separate fields or records.

The application should never present an estimate as an actual transaction.

---

## 11. Profit

For a simple cultivation contract:

```text
Net profit
=
Realized harvest value
-
Total cultivation cost
```

Where:

```text
Total cultivation cost
=
Σ individual expense amounts
```

The resulting profit is a calculated value.

It should be possible to reproduce the result from the underlying records.

---

## 12. Settlement

Settlement converts the agreed share percentages into monetary amounts.

For net profit `P`:

```text
Cultivator settlement
=
P × cultivatorSharePercent / 100

Landowner settlement
=
P × landownerSharePercent / 100
```

Example:

```text
Net profit = BDT 100,000

Cultivator = 60%
Landowner  = 40%

Cultivator = BDT 60,000
Landowner  = BDT 40,000
```

The settlement should retain enough information to explain how the result was calculated.

---

## 13. Negative Profit

A cultivation contract may produce a loss.

For example:

```text
Harvest value = BDT 80,000
Cultivation cost = BDT 100,000

Net profit = -BDT 20,000
```

The application should not silently convert a negative result into zero.

Instead, the financial state should explicitly represent the loss.

How that loss is allocated is a contractual matter and should not be inferred automatically unless the contract explicitly defines the rule.

---

## 14. Contract Status

A contract should have a lifecycle state.

The MVP uses:

```text
ACTIVE
COMPLETED
CANCELLED
```

### ACTIVE

The contract is currently in force or being managed.

### COMPLETED

The cultivation and associated settlement process has been completed.

### CANCELLED

The contract was terminated without normal completion.

Status transitions should be explicit.

---

## 15. Notes

Contracts may contain free-form notes for contextual information.

Example:

```text
Irrigation costs shared equally.
```

Notes are supplemental information and should not replace structured financial or contractual fields.

---

## 16. Evidence

A contract may have associated evidence.

Examples include:

* Signed documents
* Photographs
* Receipts
* Payment records
* Land documents
* Harvest records
* Written acknowledgements

Evidence should be linked to the relevant entity whenever possible.

For example:

```text
Contract
 ├── Evidence
 ├── Expenses
 │    └── Evidence
 └── Harvest
      └── Evidence
```

This creates a traceable record rather than a collection of unrelated files.

---

## 17. Recorded vs Calculated Data

The application should clearly distinguish three categories.

### Recorded data

Entered or imported from a real-world record.

Examples:

```text
Expense amount
Harvest quantity
Sale price
Party name
Land area
```

### Agreed data

Terms established by the parties.

Examples:

```text
Cultivator share
Landowner share
Cultivation period
Cost-sharing rules
```

### Calculated data

Derived from other information.

Examples:

```text
Total cultivation cost
Harvest value
Net profit
Settlement amount
```

Calculated values should be reproducible from their inputs.

---

## 18. Data Integrity Invariants

The contract model should enforce or validate the following basic conditions:

```text
Contract number is not blank.

Land reference exists.

Required party references exist.

Crop name is not blank.

Land area is positive.

Land area unit is not blank.

Settlement shares are between 0% and 100%.

Settlement shares total 100% for a two-party agreement.

Expense amounts are non-negative.

Harvest quantities are non-negative.

Unit prices are non-negative.
```

These rules should be tested independently from the user interface.

---

## 19. Example Contract

A conceptual contract may look like:

```text
Contract:
    Number: SC-2026-001

Land:
    Area: 2.5 acre
    Description: North field

Landowner:
    Rahman Ali

Cultivator:
    Abdul Karim

Crop:
    Rice

Cultivation:
    Start: 01 Aug 2026
    End: 29 Nov 2026
    Duration: 120 days

Settlement:
    Cultivator: 60%
    Landowner: 40%

Expenses:
    Labor: BDT 10,000
    Fertilizer: BDT 5,000
    Seed: BDT 3,000
    Irrigation: BDT 2,000
    Transportation: BDT 1,000

Harvest:
    Quantity: 5,000 kg
    Unit price: BDT 45/kg

Revenue:
    BDT 225,000

Total costs:
    BDT 21,000

Net profit:
    BDT 204,000
```

The final settlement would then be derived from the agreed shares.

---

## 20. Design Objective

The contract model should make it possible for a user to answer four questions at any time:

1. **What was agreed?**
2. **What actually happened?**
3. **What evidence supports it?**
4. **How was the final settlement calculated?**

A contract record is therefore more than a form.

It is the structured relationship between the parties, land, cultivation activity, financial records, evidence, and settlement outcome.

```
```

