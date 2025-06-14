# Java Stream Pipeline Projects – License Validation and Seat Allocation

## Overview

This project contains two Java programs (`Pass.java` and `Distinction.java`) that demonstrate the use of **Java Stream pipelines** to process structured text data efficiently. Each task is implemented using a single functional pipeline, with no external libraries or helper methods beyond the Java Standard Library.

---

## 📄 Task 1: License Validation (`Pass.java`)

### Objective

Validate a list of driver's licenses from a batch file. Each license is a record of key-value pairs (fields), separated by spaces or newlines, with records separated by blank lines.

### Requirements

Each license must contain **at least 7 of the 8 required fields** (excluding `state`, which is optional). A valid license must also pass these field-specific checks:

- **Age Check**: License holder must be **at least 21 years old**.
- **Issue Date**: Cannot be in the future or older than 10 years from the current year.
- **Expiry Date**: Must not be expired or more than 10 years into the future.
- **Height**: Must be either:
  - 150–193 cm, or
  - 59–76 in
- **Hair Color**: Must be a valid HTML hex color code (e.g., `#123abc`).
- **Eye Color**: Must be one of: `amber`, `blue`, `brown`, `gray`, `green`, `hazel`, `other`.
- **USMCA License Number**: Must be a 9-digit decimal number.

### Output

- Prints each validated license as a formatted `Map`.
- Separates records with a dashed line (`-`).
- At the end, prints the total count of valid licenses with a double line (`=`).

---

## 🚌 Task 2: Seat Allocation (`Distinction.java`)

### Objective

Decode seat assignment codes on a set of **eight double-decker coaches** using **binary space partitioning** and determine:

1. The **missing seat ID** (your seat).
2. The **highest seat ID** among the tickets.

### Decoding Rules

Each ticket is a 10-character code:

- First 3 chars: **Coach** (0–7) — using `L` and `R`
- 4th char: **Deck** (0 = down, 1 = up) — using `D` and `U`
- Next 4 chars: **Row** (0–15) — using `F` and `B`
- Last 2 chars: **Column** (0–3) — using `L` and `R`

### Seat ID Formula

seatID = coach * 128 + deck * 64 + row * 4 + column

### Special Notes

Some seats are **intentionally unavailable** due to:
- Driver seats
- Washrooms (back left of lower deck)
- Stairs (back row, center of both decks)

### Output

- Identifies and prints the **missing seat** (your seat) with full breakdown (coach, deck, row, column, seat ID).
- Prints the **highest seat ID** found.

---

## 🛠️ Technologies Used

- Java 17
- Java NIO (`Files`, `Paths`)
- Java Streams API
- Functional programming constructs (lambdas, collectors)

---

## 📁 Project Structure


