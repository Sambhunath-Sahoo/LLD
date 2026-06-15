# Pattern memory map

Master table — pattern, type, hook phrase, what it does.

| Pattern | Type | Hook / one-liner | What it does |
|---------|------|------------------|--------------|
| **Singleton** | Creational | "exactly one instance" | Restricts a class to one global instance |
| **Factory Method** | Creational | "create without naming the class" | Subclasses decide which concrete product to instantiate |
| **Simple Factory** | Creational (idiom) | "one place picks the type" | Single class/function returns the right product by input |
| **Abstract Factory** | Creational | "families of related objects" | Creates groups of related products (UI kit, OS widgets) |
| **Builder** | Creational | "step-by-step, same result" | Separates construction of a complex object from its representation |
| **Prototype** | Creational | "clone instead of new" | Copy existing instances instead of constructing from scratch |
| **Adapter** | Structural | "incompatible interface" | Wraps one interface so client can use another |
| **Decorator** | Structural | "add behavior without subclassing" | Wraps object to stack responsibilities dynamically |
| **Facade** | Structural | "simple front for messy subsystem" | Single entry point hiding many classes |
| **Proxy** | Structural | "stand-in / lazy / access control" | Surrogate controlling access to real object |
| **Bridge** | Structural | "abstraction vs implementation vary independently" | Splits interface hierarchy from implementation hierarchy |
| **Composite** | Structural | "tree of parts and wholes" | Treat individual objects and compositions uniformly |
| **Flyweight** | Structural | "share intrinsic state" | Reuse shared state across many similar objects |
| **Strategy** | Behavioral | "swap algorithm at runtime" | Encapsulate interchangeable algorithms |
| **Observer** | Behavioral | "notify subscribers on change" | One-to-many dependency; subject broadcasts updates |
| **State** | Behavioral | "behavior changes with internal state" | Object delegates to state objects as mode changes |
| **Command** | Behavioral | "request as an object" | Encapsulate action + params; supports undo/queue |
| **Template Method** | Behavioral | "skeleton fixed, steps vary" | Base class defines flow; subclasses override steps |
| **Chain of Responsibility** | Behavioral | "pass along until someone handles" | Linked handlers; each can process or forward |
| **Iterator** | Behavioral | "traverse without exposing internals" | Sequential access without leaking collection structure |
| **Mediator** | Behavioral | "central hub, not mesh" | Objects talk through mediator instead of each other |
| **Memento** | Behavioral | "snapshot / undo state" | Save and restore object state without breaking encapsulation |
| **Visitor** | Behavioral | "new operation on existing hierarchy" | Double dispatch: add operations without changing element classes |
| **Interpreter** | Behavioral | "grammar as classes" | Define grammar + interpret sentences (DSL, rules engine) |
