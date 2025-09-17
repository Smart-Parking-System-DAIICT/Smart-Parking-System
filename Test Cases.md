# Smart Parking — Test Cases

This document contains the canonical test cases for the Smart Parking project. Add this file to your repository (e.g., under `Documentation/Testing/` or project root) as `TestCases.md`.

---

## Table of Contents

1. [Overview](#overview)
2. [Example Data](#example-data)
3. [Test Cases (TC-01 to TC-09)](#test-cases)
4. [Execution & Evidence](#execution--evidence)
5. [Checklist for PR / Instructor Review](#checklist-for-pr--instructor-review)
6. [Notes & Adaptation Guidance](#notes--adaptation-guidance)

---

## Overview

These test cases cover core booking, payment, concurrency, cancellation, extension, expiry, and authorization scenarios for the Smart Parking system. Each test case contains:

* Purpose
* Preconditions / Test data
* Steps
* Expected outcome / Acceptance criteria
* Type (Unit / Integration / E2E)
* Priority

Use the cases for manual testing, or convert them into automated tests (JUnit + Mockito) as part of the CI pipeline.

---

## Example Data

* **User IDs:** `1`, `2`, `3`
* **Parking Lot IDs:** `10`, `11`, `12`
* **Slot IDs:** `101`, `102`, `103`
* **Booking IDs:** `1001`, `2001`, `3001`
* **Start Time Example:** `2025-09-18T09:00:00`
* **End Time Example:** `2025-09-18T11:00:00`

---

## Test Cases

### TC-01 — Create booking (happy path)

* **Purpose:** Verify a user can create a booking when a slot is available.
* **Type:** Integration / E2E
* **Priority:** High
* **Preconditions:** Logged-in user; lot `L1` has a free slot `S1`.
* **Steps:** Call `createBooking` with valid start/end times.
* **Expected:** Booking returned with `bookingId`, `status=CONFIRMED`; slot `S1` marked `occupied=true`; booking `amount` ≥ 0.

---

### TC-02 — Prevent overlapping bookings for same slot (concurrency)

* **Purpose:** Ensure only one of concurrent overlapping booking attempts succeeds for the same slot/time.
* **Type:** Integration / Concurrency
* **Priority:** High
* **Preconditions:** Two users attempt bookings for same slot or same lot/time window.
* **Steps:** Submit two near-simultaneous `createBooking` requests for overlapping window.
* **Expected:** Only one request succeeds (`CONFIRMED`); other fails with `No available slots` or equivalent; DB does not contain two overlapping bookings for same slot.

---

### TC-03 — Create booking when no slots available

* **Purpose:** Verify request fails when lot is full.
* **Type:** Integration
* **Priority:** High
* **Preconditions:** Lot `L3` has 0 free slots.
* **Steps:** Call `createBooking` for `L3`.
* **Expected:** Error/exception `No available slots`; no booking persisted; no slot changes.

---

### TC-04 — Cancel booking frees slot

* **Purpose:** Cancelling a booking updates booking status and frees associated slot.
* **Type:** Integration
* **Priority:** High
* **Preconditions:** Booking `B1` with `slotId=S4` exists and is `CONFIRMED`; slot `S4.occupied=true`.
* **Steps:** Call `cancelBooking(B1.id)` as owner (or admin).
* **Expected:** Booking status becomes `CANCELLED`; slot `S4.occupied=false`; both persisted.

---

### TC-05 — Extend booking within available window

* **Purpose:** Allow user to extend booking end-time if no conflict.
* **Type:** Integration
* **Priority:** Medium
* **Preconditions:** Booking `B2` exists 09:00–11:00; no conflicting bookings after 11:00.
* **Steps:** Call `extendBooking(B2.id, newEndTime=12:00)`.
* **Expected:** Booking `endTime` updated and saved; `amount` updated if pricing recalculated.

---

### TC-06 — Reject extension when conflicting booking exists

* **Purpose:** Prevent extension if another booking conflicts with requested extension.
* **Type:** Integration
* **Priority:** Medium
* **Preconditions:** Booking `B2` exists; another booking `B3` exists that conflicts with proposed extension.
* **Steps:** Attempt extension to conflicting time.
* **Expected:** Extension rejected with informative error; original booking unchanged.

---

### TC-07 — Payment processing and amount calculation

* **Purpose:** Verify correct charge calculation and payment status handling.
* **Type:** Integration / E2E
* **Priority:** High
* **Preconditions:** Booking with amount due; payment gateway mocked/sandbox.
* **Steps:** Trigger payment for booking.
* **Expected:** Amount matches pricing rules; on payment success booking/payment status `PAID`; on failure payment status `FAILED` and booking handled per policy.

---

### TC-08 — Reservation expiry / auto-release (no check-in)

* **Purpose:** Auto-release reserved slot if user does not check in within grace period.
* **Type:** Integration (Scheduler)
* **Priority:** Medium
* **Preconditions:** Reserved booking `B4` with start time soon and grace period configured.
* **Steps:** Do not check-in; advance time past `startTime + gracePeriod`; run expiry job.
* **Expected:** Booking becomes `EXPIRED` or `CANCELLED`; slot freed; logs/notifications created.

---

### TC-09 — Authorization: only owner (or admin) can cancel booking

* **Purpose:** Ensure only booking owner or admin can cancel bookings.
* **Type:** Security / Integration
* **Priority:** High
* **Preconditions:** Booking `B5` owned by `U7`; `U8` is non-owner, non-admin.
* **Steps:** Attempt `cancelBooking(B5.id)` as `U8`.
* **Expected:** Request denied (403/Unauthorized); booking and slot unchanged.

---

