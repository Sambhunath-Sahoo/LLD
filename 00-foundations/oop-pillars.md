# OOP Pillars

Four ideas every OOP language builds on. Read the **bad → good** pair for each, then check **what changed** so you know *why* the good version is better.

---

## 1. Encapsulation

**In one sentence:** Hide the data inside a class and only expose safe ways to read or change it.

**Real-life analogy:** An ATM — you tap "withdraw ₹500". You never open the machine and move wires yourself. The machine checks your balance internally and rejects invalid requests.

### Bad — data is exposed, anyone can break it

```java
class BankAccount {
    public double balance;  // public — anyone can change this directly

    public BankAccount(double balance) {
        this.balance = balance;
    }
}

BankAccount account = new BankAccount(1000);
account.balance = -5000;   // no check — balance is now invalid
account.balance = 999999;  // bypasses all business rules
```

**Problem:** Code outside the class can set `balance` to anything. Rules like "no negative balance" or "max withdrawal limit" are impossible to enforce.

### Good — data is hidden, changes go through methods

```java
class BankAccount {
    private double balance;  // private — cannot access from outside this class

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }
}

BankAccount account = new BankAccount(1000);
account.withdraw(200);        // OK — goes through validation
// account.balance = -5000;   // compile error — balance is private
```

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| `balance` public → `private` | Outside code can't overwrite the value directly |
| Added `withdraw()` with checks | Every change to balance runs through one place with rules |
| Added `getBalance()` instead of direct read | You control what outsiders can see |

**Takeaway:** Encapsulation = **protect your data** + **force changes through methods you wrote**. Java enforces this at compile time with `private`.

---

## 2. Abstraction

**In one sentence:** Show people the simple button; hide the messy machinery behind it.

**Real-life analogy:** You click "Pay" in an app. You don't care if the backend uses Razorpay, Stripe, or UPI — you just see one action.

### Bad — caller must know every implementation detail

```java
class OrderService {
    public boolean checkout(Order order, String paymentType) {
        if ("razorpay".equals(paymentType)) {
            // 20 lines of Razorpay API calls
            String apiKey = "...";
        } else if ("stripe".equals(paymentType)) {
            // 20 lines of Stripe API calls
        } else if ("upi".equals(paymentType)) {
            // 15 lines of UPI logic
        }
        return false;
    }
}
```

**Problem:** `OrderService` knows how every payment gateway works. Adding a new gateway means editing this class again. The "checkout" idea is buried in payment details.

### Good — caller only sees a simple interface

```java
interface PaymentGateway {
    boolean charge(double amount);
}

class RazorpayGateway implements PaymentGateway {
    @Override
    public boolean charge(double amount) {
        // Razorpay details hidden here
        return true;
    }
}

class OrderService {
    public boolean checkout(Order order, PaymentGateway gateway) {
        return gateway.charge(order.getTotal());
    }
}
```

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| Extracted `PaymentGateway` interface with one method `charge()` | Caller only knows: "charge this amount" |
| Each gateway is its own class | Razorpay/Stripe details live in one file each |
| `OrderService` takes `PaymentGateway`, not a string | Adding PayPal = new class, **no change** to `OrderService` |

**Takeaway:** Abstraction = **hide complexity** behind a small, stable interface — typically a Java `interface`.

---

## 3. Inheritance

**In one sentence:** Build a specialized version by extending a base class — reuse what is common, override what is different.

**Real-life analogy:** A "Vehicle" has wheels and speed. A "Car" and "Bike" both are vehicles — they inherit wheels/speed but add their own behavior (4 wheels vs 2).

### Bad — copy-paste the same code in every class

```java
class Dog {
    String name;

    Dog(String name) { this.name = name; }

    void eat() { System.out.println(name + " is eating"); }
    void speak() { System.out.println("woof"); }
}

class Cat {
    String name;

    Cat(String name) { this.name = name; }

    void eat() { System.out.println(name + " is eating"); }  // duplicated
    void speak() { System.out.println("meow"); }
}
```

**Problem:** `name` and `eat()` are duplicated. If eating logic changes (e.g. add logging), you must edit every animal class.

### Good — common parts live in the parent

```java
class Animal {
    protected String name;

    Animal(String name) { this.name = name; }

    void eat() { System.out.println(name + " is eating"); }
}

class Dog extends Animal {
    Dog(String name) { super(name); }

    void speak() { System.out.println("woof"); }
}

class Cat extends Animal {
    Cat(String name) { super(name); }

    void speak() { System.out.println("meow"); }
}
```

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| Created `Animal` parent with shared `name` and `eat()` | Write common logic once |
| `Dog` / `Cat` only define what's different (`speak`) | Less code, less duplication |
| Fix `eat()` in one place → all animals get the fix | Easier to maintain |

**Takeaway:** Inheritance = **reuse shared behavior**, only write the parts that differ. Java uses `extends` and `super()`.

---

## 4. Polymorphism

**In one sentence:** Same method call, different behavior depending on the actual object type.

**Real-life analogy:** One "Volume Up" button on a TV remote — on a TV it raises TV volume, on a soundbar it raises speaker volume. Same button, different result.

### Bad — if/else on type everywhere

```java
void makeSpeak(String animalType, String name) {
    if ("dog".equals(animalType)) {
        System.out.println(name + " says woof");
    } else if ("cat".equals(animalType)) {
        System.out.println(name + " says meow");
    } else if ("cow".equals(animalType)) {
        System.out.println(name + " says moo");
    }
    // add new animal → edit this method every time
}

makeSpeak("dog", "Bruno");
```

**Problem:** Every new animal type means editing this method. Type checking with strings is fragile (`"Dog"` vs `"dog"` breaks).

### Good — each class knows its own behavior; caller doesn't care about type

```java
abstract class Animal {
    abstract String speak();
}

class Dog extends Animal {
    @Override
    String speak() { return "woof"; }
}

class Cat extends Animal {
    @Override
    String speak() { return "meow"; }
}

void makeSpeak(Animal animal) {
    System.out.println(animal.speak());  // works for Dog, Cat, or any future Animal
}

makeSpeak(new Dog());
makeSpeak(new Cat());
```

### What changed → how it improves

| What changed | How it improves |
|--------------|-----------------|
| Removed `if animalType == ...` chain | No central method to edit for every new type |
| Each class implements its own `speak()` | Behavior lives with the class that owns it |
| `makeSpeak(Animal animal)` accepts any subclass | Add `Cow` class → `makeSpeak` still works unchanged |

**Takeaway:** Polymorphism = **call the same method**, let the object decide what happens. Java uses `@Override`; the JVM picks the right implementation at runtime.

---

## How the four pillars connect

```
Encapsulation  → protect data inside the object
Abstraction    → hide complexity behind a simple interface
Inheritance    → reuse and extend a parent class
Polymorphism   → same call, different behavior at runtime
```

Encapsulation + Abstraction often appear together (hide data *and* hide how things work). Inheritance + Polymorphism often appear together (extend a parent, override methods).

---

## Checklist

- [ ] For each pillar: explain it in one sentence without reading
- [ ] For each pillar: write bad code, then fix it from memory
- [ ] See how Abstraction connects to **D** in SOLID → [solid-principles.md](solid-principles.md)
