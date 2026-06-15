# Common follow-ups

Extension questions interviewers ask after your initial design.

## "What if pricing rules change?"

- Extract `PricingStrategy` interface.
- New rules = new strategy class; no change to `Order` or checkout flow.

## "What if we add a new vehicle type in Parking Lot?"

- `Vehicle` hierarchy + `ParkingSpot.canFit(vehicle)`.
- Factory for spot assignment if creation logic grows.

## "What if we need multiple elevators / floors?"

- Elevator as State machine per car; `Scheduler` mediates requests (Mediator or Strategy for dispatch algo).

## "What if Splitwise supports different split types?"

- Strategy per split type: equal, exact, percentage.
- `Expense` holds list of `(user, amount)` from chosen strategy.

## "What if logging needs file + console + remote?"

- Chain of Responsibility or composite Logger wrapping multiple appenders (Decorator-like).

## "How do you test this?"

- Inject interfaces (DIP) → mock repositories, strategies, factories.
- State/Strategy: test each class in isolation.

## "How do you handle concurrency?"

- Singleton: thread-safe variants (see singleton folder when implemented).
- Shared resources: locks on spot booking, seat selection, rate limiter token bucket.
