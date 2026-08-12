````markdown
# Evidence Model

## 1. Purpose

The evidence model defines how Social Contract Android associates supporting records with contracts, parties, land, cultivation activities, expenses, harvests, and settlements.

Evidence exists to answer:

> What supports this recorded fact, transaction, agreement, or calculation?

The model is designed around traceability rather than merely storing files.

---

## 2. Evidence as a First-Class Record

Evidence should have its own identity and metadata.

Conceptually:

```text
Evidence
├── id
├── type
├── title
├── description
├── fileReference
├── mimeType
├── createdAt
└── capturedAt
````

An evidence record may reference a physical file stored locally on the device.

---

## 3. Evidence Types

The MVP should support broad evidence categories.

Recommended types include:

```text
DOCUMENT
PHOTO
RECEIPT
INVOICE
PAYMENT_RECORD
SIGNATURE
NOTE
OTHER
```

The application should use stable internal identifiers for these categories.

---

## 4. Evidence Relationships

Evidence should be associated with the entity it supports.

Possible relationships include:

```text
Contract
Party
Land
Cultivation
Expense
Harvest
Settlement
Payment
```

Conceptually:

```text
Contract
├── Evidence
├── Expenses
│   └── Evidence
├── Harvest
│   └── Evidence
└── Settlement
    └── Evidence
```

This allows evidence to remain contextually meaningful.

---

## 5. Contract Evidence

Contract-level evidence may include:

* Signed contract
* Photographs of signed pages
* Identification documents
* Land documents
* Written amendments
* Witness records
* Other supporting documents

Example:

```text
Contract SC-2026-001
└── Evidence
    ├── Signed agreement.pdf
    └── Land document.jpg
```

---

## 6. Party Evidence

Party-related evidence may include documents that establish or support information about a party.

Examples may include:

* Identification documents
* Contact verification
* Written acknowledgements
* Signature records

The application should avoid collecting unnecessary personal information.

Only information required for the application's intended contractual purpose should be stored.

---

## 7. Land Evidence

Land evidence may include:

* Land documents
* Maps
* Photographs
* Ownership records
* Measurement records
* Written descriptions

Land evidence should be associated with the land record when it supports the land itself.

If a particular document applies only to one contract, it may instead be associated with the contract.

---

## 8. Cultivation Evidence

Cultivation evidence may document activities such as:

* Land preparation
* Planting
* Fertilizer application
* Irrigation
* Crop growth
* Harvesting

Examples include photographs, notes, and activity records.

A future version may support chronological cultivation evidence:

```text
Cultivation
├── 01 Aug — Land preparation
├── 10 Aug — Planting
├── 25 Aug — Fertilizer application
└── 20 Nov — Harvest
```

---

## 9. Expense Evidence

Expense evidence is particularly important for financial traceability.

Examples:

```text
Expense
├── Receipt
├── Invoice
└── Payment record
```

For example:

```text
Expense:
Fertilizer
Amount: BDT 5,000

Evidence:
fertilizer_receipt.jpg
```

The application should make it possible to identify which expense a receipt supports.

---

## 10. Harvest Evidence

Harvest evidence may include:

* Harvest photographs
* Weighing records
* Buyer receipts
* Sale invoices
* Market records
* Payment evidence

Example:

```text
Harvest:
5,000 kg rice

Evidence:
weighing_record.jpg
buyer_receipt.pdf
```

Evidence should not automatically be interpreted as proof of a value unless the application explicitly records the relationship.

---

## 11. Settlement Evidence

Settlement evidence may include:

* Settlement acknowledgement
* Signed settlement document
* Payment receipt
* Bank transaction record
* Mobile financial service record
* Cash acknowledgement

Example:

```text
Settlement
├── Settlement calculation
├── Signed acknowledgement
└── Payment receipt
```

A payment record should remain distinguishable from the settlement calculation itself.

---

## 12. File Reference

The evidence record should reference the stored file rather than embedding the complete file contents inside the domain object.

Conceptually:

```text
fileReference
```

The reference may point to:

* Internal application storage
* MediaStore
* A document URI
* Another supported local storage mechanism

The exact Android storage implementation belongs to the infrastructure layer.

---

## 13. MIME Type

Evidence should retain the file's MIME type where available.

Examples:

```text
image/jpeg
image/png
application/pdf
text/plain
text/csv
```

MIME information helps the application determine how to display or share the evidence.

The application should not rely solely on file extensions.

---

## 14. Evidence Metadata

Recommended metadata includes:

```text
id
title
description
type
fileReference
mimeType
createdAt
capturedAt
```

Additional metadata may include:

```text
fileSize
checksum
source
```

These should only be added when they provide a concrete benefit.

---

## 15. Evidence Integrity

Evidence files may be important for later review.

A future implementation may calculate a cryptographic checksum:

```text
Evidence File
      ↓
SHA-256
      ↓
Stored checksum
```

If the file is later accessed, the checksum can be recalculated to detect modification.

The MVP does not need cryptographic verification for every file, but the model should remain compatible with it.

---

## 16. Evidence Immutability

Evidence associated with a finalized contract should generally be treated as historical records.

Instead of silently replacing a file:

```text
Old receipt
    ↓
replace
    ↓
New receipt
```

a future audit-oriented system should prefer:

```text
Original receipt
       ↓
Correction / replacement record
       ↓
New receipt
```

This preserves the history of what was originally recorded.

---

## 17. Evidence Deletion

Deleting evidence may affect the ability to reconstruct a contract.

The application should therefore distinguish between:

```text
Remove from current view
```

and:

```text
Permanent deletion
```

For finalized contracts, permanent deletion should be restricted or explicitly confirmed.

The MVP may use normal local deletion while keeping the architecture compatible with future retention policies.

---

## 18. Evidence and Privacy

Evidence may contain sensitive information.

Examples:

* Identification documents
* Financial receipts
* Signatures
* Personal photographs
* Payment records

Therefore:

1. Evidence should remain private by default.
2. The application should not upload evidence without explicit user action.
3. Sharing should occur only through an intentional user action.
4. Exported contracts should include only intended evidence.
5. The application should avoid unnecessary duplication of evidence files.

---

## 19. Evidence and Sharing

Evidence may be shared using Android's standard sharing mechanisms.

The sharing flow should be:

```text
User
 ↓
Select evidence
 ↓
Choose Share
 ↓
Android system share sheet
 ↓
Selected application
```

The application should not silently transmit evidence to third-party services.

---

## 20. Evidence in PDF Contracts

A contract PDF may reference evidence without embedding every original file.

For example:

```text
Supporting Evidence

1. Signed contract
2. Land document
3. Fertilizer receipt
4. Harvest weighing record
5. Settlement acknowledgement
```

If evidence is embedded, the PDF generator should clearly distinguish the embedded copy from the original stored evidence.

---

## 21. Evidence in CSV Export

CSV is primarily structured data and should not normally contain binary evidence.

Instead, it may include:

```text
evidence_id
evidence_type
title
file_reference
mime_type
created_at
```

The exported references should be treated as identifiers or references, not guaranteed public URLs.

---

## 22. Evidence Association Model

A flexible association model can be represented as:

```text
Evidence
   │
   ├── contractId
   ├── partyId
   ├── landId
   ├── cultivationId
   ├── expenseId
   ├── harvestId
   └── settlementId
```

Only the relevant association should normally be populated.

A future implementation may instead use a dedicated association table:

```text
EvidenceAssociation
├── evidenceId
├── entityType
└── entityId
```

This allows one evidence item to support multiple entities without duplicating the file.

---

## 23. Evidence Versioning

Evidence may be revised.

A future versioning model could use:

```text
Evidence
├── id
├── version
├── previousVersionId
└── fileReference
```

This enables a chain:

```text
Version 1
    ↓
Version 2
    ↓
Version 3
```

The MVP does not need full version control, but evidence identifiers should be stable enough to support future expansion.

---

## 24. Evidence Timeline

Evidence can also form a chronological record.

Example:

```text
01 Aug 2026
Land preparation photograph

05 Aug 2026
Seed purchase receipt

10 Aug 2026
Planting photograph

20 Sep 2026
Fertilizer receipt

20 Nov 2026
Harvest weighing record

25 Nov 2026
Settlement acknowledgement
```

This timeline can provide useful context when reviewing a completed cultivation contract.

---

## 25. Evidence and Auditability

Evidence should support reconstruction of important events.

A reviewer should ideally be able to move from:

```text
Settlement
   ↓
Profit
   ↓
Expenses
   ↓
Individual expense
   ↓
Receipt
```

and:

```text
Settlement
   ↓
Revenue
   ↓
Harvest
   ↓
Harvest evidence
```

This creates a traceable chain from financial result to supporting records.

---

## 26. Evidence Validation

Before accepting evidence, the application should verify basic conditions:

```text
Evidence type is valid.

Title is not unnecessarily empty.

File reference exists.

File is accessible.

MIME type is compatible where known.

Associated entity exists.

File size is within application limits.
```

The exact file-size limit should be defined by the application's storage strategy.

---

## 27. Evidence Security

The application should avoid executing or interpreting uploaded files as code.

Evidence should be treated as data.

For example:

```text
PDF → document
JPEG → image
CSV → data
```

not as executable content.

When opening evidence through another application, Android's content-sharing mechanisms should be used appropriately.

---

## 28. Example Evidence Record

A conceptual record:

```text
Evidence
    ID:
        evidence-001

    Type:
        RECEIPT

    Title:
        Fertilizer purchase receipt

    Description:
        Receipt for 50 kg urea fertilizer

    File:
        content://...

    MIME type:
        image/jpeg

    Created:
        12 Aug 2026

    Associated expense:
        expense-003
```

This allows the receipt to be traced directly to the relevant expense.

---

## 29. Example Contract Evidence Graph

A complete contract may form the following relationship:

```text
Contract
│
├── Land
│   └── Land document
│
├── Parties
│   └── Supporting records
│
├── Cultivation
│   ├── Activity photographs
│   ├── Expenses
│   │   ├── Fertilizer receipt
│   │   ├── Seed receipt
│   │   └── Transport receipt
│   │
│   └── Harvest
│       ├── Weighing record
│       └── Buyer receipt
│
└── Settlement
    ├── Settlement calculation
    └── Payment acknowledgement
```

This is the intended evidence chain for the system.

---

## 30. Testing Requirements

Evidence-related tests should eventually cover:

```text
Evidence creation
Evidence type validation
File reference validation
Entity association
MIME type handling
Multiple evidence records
Evidence retrieval
Evidence deletion
Evidence replacement
Evidence export
```

Where file-system behavior is tested, Android-specific tests may be required in addition to JVM unit tests.

---

## 31. Design Objective

The evidence model should make the application's records more than assertions.

It should allow the user to establish a relationship between:

```text
What was agreed
       ↓
What happened
       ↓
What was paid
       ↓
What was calculated
       ↓
What evidence supports each event
```

The objective is therefore not simply to attach files to contracts.

The objective is to create a **traceable evidence structure** around the contractual and financial record.

```
```

