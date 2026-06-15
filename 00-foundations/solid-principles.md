# SOLID Principles

Five rules for writing code that is easier to change, test, and extend. Each section follows the same format:

1. **Plain explanation** — what the rule means
2. **Bad code** — what goes wrong
3. **Good code** — the fix
4. **What changed → how it improves** — compare side by side

---

## S — Single Responsibility Principle (SRP)

**In one sentence:** One class = one job. If you need to describe a class with "and", it probably does too much.

**Rule:** A class should have only **one reason to change**.

---

### Bad — one class does too many jobs

```java
class User {
    private String name;
    private String email;

    User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    String getName() { return name; }

    void saveToDatabase() {
        System.out.println("Saving " + name + " to MySQL...");
    }

    void sendWelcomeEmail() {
        System.out.println("Sending welcome email to " + email + "...");
    }
}
```

**What goes wrong:**
- Change database from MySQL → Postgres? Edit `User`.
- Change email provider? Edit `User` again.
- `User` is supposed to represent a person, but it also knows SQL and SMTP.

**Reasons to change:** (1) user data rules, (2) database, (3) email — **three reasons** = SRP violation.

---

### Good — each class has one job

```java
class User {
    private String name;
    private String email;

    User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    String getName() { return name; }
    String getEmail() { return email; }
}

class UserRepository {
    void save(User user) {
        System.out.println("Saving " + user.getName() + " to database...");
    }
}

class EmailService {
    void sendWelcome(User user) {
        System.out.println("Sending welcome email to " + user.getEmail() + "...");
    }
}
```

---

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| Removed `saveToDatabase()` from `User` | `User` only holds user data — change DB without touching `User` |
| Removed `sendWelcomeEmail()` from `User` | Switch email provider? Only edit `EmailService` |
| Added `UserRepository` and `EmailService` | Each file has one clear purpose — easier to find and test |

**Takeaway:** Split by **reason to change**, not by "what feels related".

---

## O — Open-Closed Principle (OCP)

**In one sentence:** Add new features by writing **new code**, not by editing **old code** that already works.

**Rule:** Open for **extension**, closed for **modification**.

---

### Bad — every new shape means editing the calculator

```java
class Rectangle {
    double width, height;
    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
}

class Circle {
    double radius;
    Circle(double radius) { this.radius = radius; }
}

class AreaCalculator {
    double calculateRectangleArea(Rectangle rect) {
        return rect.width * rect.height;
    }

    double calculateCircleArea(Circle circle) {
        return 3.14 * circle.radius * circle.radius;
    }

    // Adding Triangle? Must add calculateTriangleArea() here — MODIFY old class
}
```

**What goes wrong:**
- Every new shape forces you to open `AreaCalculator` and add a method.
- `AreaCalculator` keeps growing. Old tested code gets touched → risk of breaking rectangles/circles.

---

### Good — new shape = new class, calculator stays untouched

```java
abstract class Shape {
    abstract double area();
}

class Rectangle extends Shape {
    double width, height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() { return width * height; }
}

class Circle extends Shape {
    double radius;

    Circle(double radius) { this.radius = radius; }

    @Override
    double area() { return 3.14 * radius * radius; }
}

class AreaCalculator {
    double calculate(Shape shape) {
        return shape.area();  // works for ANY Shape
    }
}

// Adding Triangle — only NEW class, zero changes to AreaCalculator:
class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() { return 0.5 * base * height; }
}
```

---

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| Added `Shape` with `area()` method | All shapes share one contract |
| Each shape implements its own `area()` | Shape logic lives with the shape |
| `AreaCalculator` has one method `calculate(shape)` | Add 10 new shapes — calculator file never changes |
| New shape = new class only (**extend**) | Old code stays closed (**no modification**) |

**Takeaway:** Push variation into subclasses or strategies — don't grow if/else chains in one class.

---

## L — Liskov Substitution Principle (LSP)

**In one sentence:** If code expects a parent type, you should be able to pass any child type and nothing breaks.

**Rule:** Subtypes must be **substitutable** for their base type.

---

### Bad — Square breaks Rectangle's rules

```java
class Rectangle {
    protected int width, height;

    void setWidth(int w) { width = w; }
    void setHeight(int h) { height = h; }
    int getArea() { return width * height; }
}

class Square extends Rectangle {
    @Override
    void setWidth(int w) {
        width = height = w;  // force equal sides
    }

    @Override
    void setHeight(int h) {
        width = height = h;
    }
}

void resizeRect(Rectangle rect) {
    rect.setWidth(5);
    rect.setHeight(10);
    assert rect.getArea() == 50;  // expects 5 × 10
}

resizeRect(new Rectangle());  // OK — area = 50
resizeRect(new Square());     // FAIL — Square sets both to 10, area = 100
```

**What goes wrong:**
- Code says `Rectangle` but passes `Square`.
- `Square` changes behavior of `setWidth` / `setHeight` in a way callers don't expect.
- **Child cannot safely replace parent** → LSP violation.

---

### Good — don't force Square to be a Rectangle

```java
abstract class Shape {
    abstract double area();
}

class Rectangle extends Shape {
    double width, height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() { return width * height; }
}

class Square extends Shape {
    double side;

    Square(double side) { this.side = side; }

    @Override
    double area() { return side * side; }
}

double totalArea(List<Shape> shapes) {
    return shapes.stream().mapToDouble(Shape::area).sum();
}
```

---

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| `Square` no longer extends `Rectangle` | Square isn't forced into width/height rules it can't honor |
| Both extend `Shape` with `area()` | Common behavior without wrong "is-a" relationship |
| Caller uses `Shape`, not `Rectangle`, when both are valid | Any `Shape` subclass works without surprises |

**Takeaway:** Inheritance must match real-world "is-a". If the child needs to override parent methods to break parent rules, the hierarchy is wrong.

---

## I — Interface Segregation Principle (ISP)

**In one sentence:** Don't force a class to implement methods it will never use.

**Rule:** Many small interfaces beat one fat interface.

---

### Bad — robot forced to implement `eat()`

```java
interface Worker {
    void work();
    void eat();
}

class Human implements Worker {
    @Override
    public void work() { System.out.println("Working..."); }

    @Override
    public void eat() { System.out.println("Eating lunch..."); }
}

class Robot implements Worker {
    @Override
    public void work() { System.out.println("Working 24/7..."); }

    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat!");
    }
}
```

**What goes wrong:**
- `Robot` must implement `eat()` even though it never eats.
- Callers might call `robot.eat()` and get a crash.
- Fat interface = every implementer carries dead methods.

---

### Good — split into small interfaces

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    @Override
    public void work() { System.out.println("Working..."); }

    @Override
    public void eat() { System.out.println("Eating lunch..."); }
}

class Robot implements Workable {
    @Override
    public void work() { System.out.println("Working 24/7..."); }
    // no eat() — doesn't implement Eatable, and that's fine
}
```

---

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| Split `Worker` into `Workable` + `Eatable` | Each interface has one purpose |
| `Human` implements both | Gets everything it needs |
| `Robot` implements only `Workable` | No fake `eat()` method, no runtime crash |
| Code that only needs work uses `Workable` | Doesn't depend on eating at all |

**Takeaway:** If an implementer leaves a method empty or throws `UnsupportedOperationException`, the interface is too big — split it.

---

## D — Dependency Inversion Principle (DIP)

**In one sentence:** High-level code (business logic) should not depend on low-level details (MySQL, Razorpay, file system). Both should depend on an **interface**.

**Rule:** Depend on **abstractions**, not concretions.

---

### Bad — switch is hard-wired to a light bulb

```java
class LightBulb {
    void turnOn()  { System.out.println("Bulb on"); }
    void turnOff() { System.out.println("Bulb off"); }
}

class ElectricPowerSwitch {
    private LightBulb bulb = new LightBulb();  // hard-coded — always a LightBulb
    private boolean on = false;

    void press() {
        if (on) {
            bulb.turnOff();
        } else {
            bulb.turnOn();
        }
        on = !on;
    }
}

// Want to control a Fan instead? Must rewrite ElectricPowerSwitch.
```

**What goes wrong:**
- `ElectricPowerSwitch` (high-level: "toggle power") directly creates `LightBulb` (low-level detail).
- Swap bulb for fan, lamp, or smart plug → rewrite the switch class.
- Hard to unit test — can't inject a mock device.

---

### Good — switch depends on an interface, device is injected

```java
interface Switchable {
    void turnOn();
    void turnOff();
}

class LightBulb implements Switchable {
    @Override
    public void turnOn()  { System.out.println("Bulb on"); }

    @Override
    public void turnOff() { System.out.println("Bulb off"); }
}

class Fan implements Switchable {
    @Override
    public void turnOn()  { System.out.println("Fan spinning"); }

    @Override
    public void turnOff() { System.out.println("Fan stopped"); }
}

class ElectricPowerSwitch {
    private final Switchable device;  // injected — not hard-coded
    private boolean on = false;

    ElectricPowerSwitch(Switchable device) {
        this.device = device;
    }

    void press() {
        if (on) {
            device.turnOff();
        } else {
            device.turnOn();
        }
        on = !on;
    }
}

ElectricPowerSwitch switch1 = new ElectricPowerSwitch(new LightBulb());
ElectricPowerSwitch switch2 = new ElectricPowerSwitch(new Fan());
```

---

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| Added `Switchable` interface | Switch only knows on/off — not bulb vs fan |
| Removed `new LightBulb()` inside switch | Switch doesn't create its dependency |
| Device passed in via constructor (**injection**) | Swap `LightBulb` → `Fan` without editing switch |
| High-level (`Switch`) depends on abstraction (`Switchable`) | Low-level (`LightBulb`) also implements abstraction — both meet at the interface |

**Takeaway:** Spring `@Autowired` / constructor injection follows DIP — business code asks for an interface (`Switchable`), not `new LightBulb()`.

---

## Quick reference

| Letter | Name | One-line rule | Bad smell |
|--------|------|---------------|-----------|
| **S** | Single Responsibility | One class, one job | Class does data + DB + email |
| **O** | Open-Closed | Extend with new classes, don't edit old ones | Growing if/else or method list |
| **L** | Liskov Substitution | Child replaces parent safely | Child breaks parent's assumptions |
| **I** | Interface Segregation | Small focused interfaces | Empty methods or `UnsupportedOperationException` |
| **D** | Dependency Inversion | Depend on interfaces, inject implementations | `new ConcreteClass()` inside business logic |

---

## Revision drill

1. Pick any principle — write **bad** code first, then refactor to **good**.
2. For each refactor, fill in the "what changed → how it improves" table from memory.
