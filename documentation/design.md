# **1. How We Improved the Design of the Software**
## **✅ 1.1 Modularized MVC Architecture (Controller → Service → Repository)**

Earlier, the controller methods contained mixed logic, validations, and DB operations.

## Improvements:

- Clearly separated logic into:
   - Controllers → Handle HTTP requests
   - Services → Business logic (slot checking, payments, etc.)
   - Repositories → Database interactions
- Reduced duplicate logic in multiple controllers
- Improved readability and code navigation

✅ Result:

Clean, layered architecture + easier debugging + maintainable code

## **✅ 1.2 Centralized Slot Availability & Reservation Logic**

Initially, slot availability was checked manually inside controllers.

## Improvements:

- Moved entire time-overlap logic into ReserveService
- Standardized slot availability through SlotService
- Introduced SlotDTO to avoid exposing internal entity structure
- Ensured consistent availability calculation across all pages

✅ Result:

More accurate slot updates + reusable logic across the app

## **✅ 1.3 Added Automated Test Coverage (JUnit + Mockito)**

Before refactoring, the system had no automated tests.

## Now we added:

- SlotServiceTest
- UserServiceTest
- ReserveServiceTest
- PaymentServiceTest
- FeedbackServiceTest
- SlotControllerTest
   
## Test coverage improvements:

- Repository mocks to avoid database dependency
- Service mocks for isolated business logic testing
- Time-based overlap test cases
- Edge cases for null users, empty slots, and invalid payments

## These improvements ensure:

- Functions behave deterministically
- Code complexity is validated
- Modules remain stable during future updates

✅ Result:

Reliable, testable, production-ready backend

## **✅ 1.4 Organized HTML Templates & Static Resources**

Earlier UI files were scattered with redundant HTML.

## Improvements:

- All HTML moved to /templates
- CSS, JS, and images placed inside /static
- Removed unnecessary tags and inline styling
- Ensured consistent page layout and formatting

✅ Result:

Cleaner UI templates + consistent user experience

## **✅ 1.5 Enhanced User Authentication Logic**

The initial authentication approach lacked verification handling.

## Improvements:

- Integrated Spring Security password encoding
- Improved login validation
- Added safety checks inside UserService

✅ Result:

More secure and reliable authentication flow

# **2. Design Patterns Used**
## ✅ 2.1 SOLID Principles
### S — Single Responsibility
Every component performs only one job:
```
UserService → User management 
SlotService → Slots & availability
ReserveService → Reservations
PaymentService → Payment handling
FeedbackService → Storing feedback
```
➡️ Improves clarity & reduces errors.

### O — Open/Closed
Services allow new features (like new payment modes)
without modifying existing logic. <br>
➡️ Encourages safer future enhancements.

### L — Liskov Substitution
Mockito mocks replace repositories/services without breaking logic. <br>
➡️ Ensures flexible testing.

### I — Interface Segregation
Each module has its own focused service and repository interface. <br>
➡️ No class depends on methods it doesn’t use.

### D — Dependency Inversion
Controllers depend on abstracted service layers instead of direct DB logic. <br>
➡️ Improves modularity + testability.

## ✅ 2.2 MVC Pattern (Model–View–Controller)

The Smart Parking System strongly follows the Spring Boot MVC architecture:

Model → Entities (User, Slots, Payment, Reservation, Feedback) <br>
View → HTML templates under /templates <br>
Controller → Handles routing and request flow <br>
Service → Business logic <br>
Repository → JPA database access <br>

✅ Result:

Robust, scalable, and easy-to-maintain structure

## ✅ 2.3 Observer Pattern (State Changes Reflected in Views)

Though not explicit, observer behavior emerges through:

- Slot status reloaded after reservation
- Updated payment states visible immediately
- Model updates passed to templates automatically
- When user actions occur:
  - Slot booking
  - Payment confirmation
  - Feedback submission

The UI reflects new data automatically.

✅ Result:

Dynamic system behavior with minimal coupling

### 3. Key Refactoring Done to Improve Design
## ✅ 3.1 Refactored Slot Availability Calculation

Old logic was duplicated and error-prone.

### Changes:
- Centralized overlap detection
- Unified time calculation
- Moved logic entirely to ReserveService

⭐ Result:

Cleaner controllers + consistent availability

## ✅ 3.2 Improved User & Authentication Flow

### Updates include:
- Encoded passwords
- User lookup improvements
- Safer null checks

⭐ Result:

More secure + reduced failure rate

## ✅ 3.3 Payment Module Redesign

- Introduced status enum
- Modularized transaction logic
- Moved DB operations to PaymentRepository
- Added test validations

⭐ Result:

More stable & predictable payment feature

## ✅ 3.4 UI Template Refactoring

- Removed redundant HTML elements
- Organized templates
- Minor layout consistency fixes
- Cleaned static resource usage

⭐ Result:

Improved readability + smoother user experience

## Final Summary

The Smart Parking System is now designed using:

- Clean MVC architecture
- SOLID principles
- Centralized business logic
- Automated testing
- Efficient reservation workflow
- Organized UI templates

These improvements make the system scalable, reliable, and easy to maintain.
