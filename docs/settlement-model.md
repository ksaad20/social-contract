````markdown
# Settlement Model

## 1. Purpose

The settlement model defines how Social Contract Android converts recorded cultivation results and agreed party shares into a transparent financial settlement.

The settlement process should be:

- Deterministic
- Reproducible
- Traceable
- Validated
- Independent of the user interface

The fundamental calculation is:

```text
Harvest Value
      -
Cultivation Costs
      =
Net Result
````

The contractual share rules are then applied to the resulting amount according to the agreement.

---

## 2. Settlement Inputs

A settlement depends on several inputs:

```text
Contract
    +
Harvest
    +
Expenses
    +
Settlement Shares
    ↓
Settlement Calculation
```

The minimum financial inputs are:

```text
Harvest quantity
Unit price
Cultivation expenses
Cultivator share
Landowner share
```

Additional contractual rules may be introduced later.

---

## 3. Settlement Components

A settlement can be represented conceptually as:

```text
Settlement
├── id
├── contractId
├── grossRevenue
├── totalCultivationCost
├── netResult
├── cultivatorSharePercent
├── landownerSharePercent
├── cultivatorAmount
├── landownerAmount
├── currency
├── calculatedAt
└── status
```

Calculated values should remain traceable to their source inputs.

---

## 4. Gross Revenue

Gross revenue represents the monetary value of the realized harvest.

For a simple single-product harvest:

```text
Gross Revenue
=
Harvest Quantity × Unit Sale Price
```

Example:

```text
Harvest quantity = 5,000 kg
Unit price       = BDT 45/kg

Gross revenue
=
5,000 × 45
=
BDT 225,000
```

If a cultivation produces multiple harvest batches, the model can aggregate their realized values.

```text
Gross Revenue
=
Σ Harvest Batch Value
```

---

## 5. Cultivation Cost

Total cultivation cost is the sum of validated expense records.

```text
Total Cultivation Cost
=
Σ Expense Amount
```

Example:

```text
Labor             BDT 10,000
Fertilizer         BDT 5,000
Seed               BDT 3,000
Irrigation         BDT 2,000
Transportation     BDT 1,000
--------------------------------
Total              BDT 21,000
```

The settlement calculator should use the calculated total rather than a manually entered total.

---

## 6. Net Result

For the basic profit-sharing model:

```text
Net Result
=
Gross Revenue - Total Cultivation Cost
```

Example:

```text
Gross revenue       BDT 225,000
Cultivation costs   BDT 21,000
--------------------------------
Net result          BDT 204,000
```

A positive result represents profit.

A negative result represents a loss.

A zero result represents break-even.

---

## 7. Profit

When the net result is positive:

```text
Profit = Net Result
```

For example:

```text
Net result = BDT 204,000

Profit = BDT 204,000
```

The application should not use rounded display values as calculation inputs.

---

## 8. Loss

A cultivation may produce a negative net result.

Example:

```text
Gross revenue       BDT 80,000
Cultivation costs   BDT 100,000
--------------------------------
Net result         -BDT 20,000
```

The application should preserve the negative result.

It should not automatically convert:

```text
-BDT 20,000
```

into:

```text
BDT 0
```

unless the contract explicitly defines such a rule.

---

## 9. Settlement Shares

For the MVP, a two-party contract uses:

```text
cultivatorSharePercent
landownerSharePercent
```

The basic invariant is:

```text
Cultivator Share + Landowner Share = 100%
```

For example:

```text
Cultivator = 60%
Landowner  = 40%
```

The shares must also satisfy:

```text
0% ≤ share ≤ 100%
```

---

## 10. Share Validation

Settlement should not proceed when the shares are invalid.

Invalid:

```text
Cultivator = 60%
Landowner  = 30%

Total = 90%
```

Invalid:

```text
Cultivator = 70%
Landowner  = 40%

Total = 110%
```

Valid:

```text
Cultivator = 60%
Landowner  = 40%

Total = 100%
```

This rule should be enforced by the share validation component before settlement.

---

## 11. Party Settlement

For a positive net result:

```text
Cultivator Amount
=
Net Result × Cultivator Share / 100

Landowner Amount
=
Net Result × Landowner Share / 100
```

Example:

```text
Net result = BDT 204,000

Cultivator = 60%
Landowner  = 40%

Cultivator:
204,000 × 0.60
= BDT 122,400

Landowner:
204,000 × 0.40
= BDT 81,600
```

The two settlement amounts equal the net result:

```text
122,400 + 81,600
=
204,000
```

---

## 12. Settlement Invariant

For a valid positive-profit settlement:

```text
Cultivator Amount + Landowner Amount
=
Net Result
```

This is a critical integrity check.

If the values do not reconcile, the application should not silently produce a final settlement.

---

## 13. Break-Even Settlement

If:

```text
Gross Revenue = Total Cultivation Cost
```

then:

```text
Net Result = 0
```

Therefore:

```text
Cultivator Amount = 0
Landowner Amount = 0
```

The application should explicitly identify this as a break-even result rather than treating it as missing data.

---

## 14. Loss Settlement

A negative net result requires special treatment.

Example:

```text
Net result = -BDT 20,000
```

The application should distinguish:

```text
Financial result
```

from:

```text
Contractual loss allocation
```

The share percentages alone should not be assumed to define how losses are borne unless the contract explicitly states that they apply to losses.

Therefore, the MVP should preserve the negative financial result and require an explicit contractual rule before automatically assigning monetary losses.

---

## 15. Cost Sharing vs Profit Sharing

These are separate concepts.

A contract may specify:

```text
Profit sharing:
Cultivator 60%
Landowner 40%
```

while costs may be:

```text
Cultivator 50%
Landowner 50%
```

Therefore, the application should not assume that profit-sharing percentages automatically determine cost-sharing responsibilities.

Future versions may explicitly model:

```text
Cost allocation
Profit allocation
Loss allocation
```

as separate contractual terms.

---

## 16. Settlement Status

A settlement should have a lifecycle state.

A possible MVP model is:

```text
CALCULATED
CONFIRMED
PAID
VOIDED
```

### CALCULATED

The application has produced a settlement result.

### CONFIRMED

The parties or authorized user have confirmed the result.

### PAID

The settlement has been recorded as paid.

### VOIDED

The settlement is no longer considered valid.

The exact status model may be simplified for the first release.

---

## 17. Settlement Confirmation

Calculation and confirmation should remain separate.

```text
Calculation
    ↓
Review
    ↓
Confirmation
    ↓
Payment
```

A calculated amount should not automatically be represented as money that has already changed hands.

This distinction is important for financial records.

---

## 18. Payment Record

A future implementation may attach payment information to a confirmed settlement.

Possible fields include:

```text
paymentDate
paymentMethod
transactionReference
amountPaid
recipient
payer
evidence
```

Payment evidence may include:

* Bank transfer receipt
* Mobile financial service transaction
* Cash acknowledgement
* Signed receipt

The MVP may record payment information without implementing full payment integration.

---

## 19. Rounding

Monetary calculations must define a consistent rounding policy.

For example:

```text
Calculated amount
        ↓
Currency precision
        ↓
Rounded settlement amount
```

Rounding should occur consistently and preferably at the final monetary output stage rather than repeatedly throughout intermediate calculations.

The exact precision should follow the currency model used by the application.

---

## 20. Monetary Precision

Financial calculations should avoid unnecessary binary floating-point arithmetic.

Preferred representations include:

```text
Integer minor currency units
```

or:

```text
Decimal arithmetic
```

For Bangladesh Taka, an integer-minor-unit representation could use poisha where appropriate.

The same monetary representation must be used consistently by:

* CostCalculator
* HarvestValueCalculator
* CalculateProfitUseCase
* SettlementCalculator
* CalculateSettlementUseCase
* PDF export
* CSV export

---

## 21. Settlement Calculation Flow

The complete calculation flow is:

```text
Harvest Records
      ↓
HarvestValueCalculator
      ↓
Gross Revenue
      ↓
Expense Records
      ↓
CostCalculator
      ↓
Total Cultivation Cost
      ↓
Profit Calculation
      ↓
Net Result
      ↓
ShareValidator
      ↓
SettlementCalculator
      ↓
Party Settlement
```

This keeps each responsibility isolated.

---

## 22. CalculateSettlementUseCase

The application-level settlement operation should coordinate the calculation.

Conceptually:

```text
CalculateSettlementUseCase
        │
        ├── Load Contract
        ├── Load Harvest
        ├── Load Expenses
        │
        ├── Calculate Revenue
        ├── Calculate Costs
        ├── Calculate Net Result
        ├── Validate Shares
        └── Calculate Settlement
```

The use case should return a structured result to the presentation layer.

---

## 23. Example Settlement

Consider:

```text
Contract:
    SC-2026-001

Gross revenue:
    BDT 225,000

Cultivation costs:
    BDT 21,000

Net result:
    BDT 204,000

Cultivator share:
    60%

Landowner share:
    40%
```

Calculated settlement:

```text
Cultivator:
BDT 122,400

Landowner:
BDT 81,600
```

Reconciliation:

```text
BDT 122,400
+
BDT 81,600
=
BDT 204,000
```

---

## 24. Settlement Report

A settlement report should make the calculation understandable without requiring the reader to inspect the application's source code.

A suitable summary is:

```text
Settlement Summary

Contract: SC-2026-001

Gross Revenue:          BDT 225,000
Cultivation Costs:      BDT 21,000
Net Result:             BDT 204,000

Cultivator Share:       60%
Cultivator Amount:      BDT 122,400

Landowner Share:        40%
Landowner Amount:       BDT 81,600
```

The report should clearly identify calculated values.

---

## 25. Evidence

Settlement evidence may include:

* Signed settlement acknowledgement
* Payment receipt
* Bank transaction record
* Mobile payment record
* Photograph of signed document
* Written acknowledgement

Evidence should be associated with the settlement or payment event rather than merely stored as an unrelated file.

---

## 26. Settlement Integrity

Before a settlement is finalized, the application should verify:

```text
Contract exists.

Relevant parties exist.

Harvest data is valid.

Expense data is valid.

Currency is consistent.

Share percentages are valid.

Financial calculations succeed.

Settlement amounts reconcile.

No required input is missing.
```

If validation fails, the application should report the problem instead of producing an apparently valid settlement.

---

## 27. Recalculation

A settlement should be reproducible.

If an underlying expense or harvest record changes before confirmation, the application should be able to recalculate the settlement.

Conceptually:

```text
Original inputs
      ↓
Calculation
      ↓
Settlement A

Updated inputs
      ↓
Recalculation
      ↓
Settlement B
```

Once a settlement has been confirmed or paid, changing the underlying data should be handled carefully to preserve historical integrity.

---

## 28. Auditability

A future audit-oriented implementation should preserve:

```text
Input records
Calculation timestamp
Calculation result
Contract version
Settlement status
Confirmation event
Payment event
Supporting evidence
```

This allows a reviewer to reconstruct why a particular settlement amount was produced.

---

## 29. Export

CSV export should expose structured settlement information.

Recommended fields include:

```text
settlement_id
contract_id
gross_revenue
total_cultivation_cost
net_result
cultivator_share_percent
landowner_share_percent
cultivator_amount
landowner_amount
currency
calculated_at
status
```

PDF export should present the same information in a human-readable format.

---

## 30. Testing Requirements

Settlement-related tests should cover:

```text
60/40 settlement
50/50 settlement
Decimal percentages
Zero profit
Positive profit
Negative result
100/0 settlement
0/100 settlement
Invalid shares
Share reconciliation
Monetary rounding
```

The tests should not depend on Android UI components.

---

## 31. Design Objective

The settlement model should allow a user to answer:

> How much was generated, how much was spent, what was the resulting financial outcome, what share was agreed, and how was each party's amount calculated?

The settlement is therefore not merely a final number.

It is a reproducible financial calculation derived from the contract, cultivation records, harvest records, expenses, and agreed allocation rules.

```
```

