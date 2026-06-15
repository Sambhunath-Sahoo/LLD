# Low-Level Design (LLD)

Interview prep repo for design patterns, SOLID, and machine-coding problems — structured to match the design-pattern cheat sheet.

## How to use

1. Read `00-foundations/` once, then revisit before mocks.
2. For each pattern in `01-patterns/`, read the README, run the code, rewrite it from memory.
3. Code at least 3 problems in `02-machine-coding/` fully (Parking Lot, one state-machine problem, one Strategy-heavy problem).
4. Drill `03-interview-qa/` the night before.
5. Run through `04-revision/last-20-min-checklist.md` on interview day.

## Structure

```
├── 00-foundations/     # OOP pillars, SOLID, RecruitCRM example
├── 01-patterns/        # All 23 patterns + memory map & trigger tables
├── 02-machine-coding/  # Parking lot, elevator, splitwise, …
├── 03-interview-qa/    # Drills, SOLID violations, follow-ups
├── 04-revision/        # Last-minute checklist
└── resources/          # Boss sheet PDF
```

## Study log

Check off when you've written the code yourself (not just read it).

### Foundations
- [ ] OOP pillars — `00-foundations/oop-pillars.md`
- [ ] SOLID — `00-foundations/solid-principles.md`
- [ ] RecruitCRM example — `00-foundations/examples/recruit-crm/`

### Creational patterns
- [ ] Singleton
- [x] Factory — started 2026-06-15
- [ ] Builder
- [ ] Abstract Factory (recognize only)
- [ ] Prototype

### Structural patterns
- [ ] Adapter
- [ ] Decorator
- [ ] Facade
- [ ] Proxy
- [ ] Bridge
- [ ] Composite
- [ ] Flyweight

### Behavioral patterns
- [ ] Strategy
- [ ] Observer
- [ ] State
- [ ] Command
- [ ] Template Method
- [ ] Chain of Responsibility
- [ ] Iterator
- [ ] Mediator
- [ ] Memento
- [ ] Visitor
- [ ] Interpreter

### Machine coding
- [ ] Parking Lot
- [ ] Elevator System
- [ ] Splitwise
- [ ] Vending Machine
- [ ] Movie Booking
- [ ] Logging Framework
- [ ] Tic Tac Toe
- [ ] Snake and Ladder
- [ ] Library Management
- [ ] Rate Limiter

## Quick links

| Resource | Path |
|----------|------|
| Pattern memory map | [01-patterns/_memory-map.md](01-patterns/_memory-map.md) |
| Confusion pairs | [01-patterns/_confusion-pairs.md](01-patterns/_confusion-pairs.md) |
| Trigger table | [01-patterns/_trigger-table.md](01-patterns/_trigger-table.md) |
| Boss sheet (PDF) | [resources/LLD_Design Pattern Boss sheet.pdf](resources/LLD_Design%20Pattern%20Boss%20sheet.pdf) |
| Mistakes log | [03-interview-qa/mistakes-log.md](03-interview-qa/mistakes-log.md) |

## Quick run

```bash
cd 01-patterns/_01_creational/factory && mkdir -p out

# Notification
javac -d out notification/common/*.java notification/simplefactory/*.java && java -cp out notification.simplefactory.Main
javac -d out notification/common/*.java notification/factorymethod/*.java && java -cp out notification.factorymethod.Main

# Cab
javac -d out cab/common/*.java cab/simplefactory/*.java && java -cp out cab.simplefactory.Main
javac -d out cab/common/*.java cab/simplefactory/*.java cab/factorymethod/*.java && java -cp out cab.factorymethod.Main
```
