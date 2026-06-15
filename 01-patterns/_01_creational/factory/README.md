# Factory (Creational)

**One idea:** something else creates the object — you don't scatter `new EmailNotification()` / `new MiniCabRide()` everywhere.

Two examples, same two patterns each:

```
factory/
├── notification/     Email / SMS / Push  →  read notification/first-principles.md
└── cab/              Mini / Bike / Premium  →  read cab/first-principles.md
```

Each example has:

```
example/
├── common/           shared interfaces + implementations (not a demo)
├── simplefactory/    YOU pick the type
└── factorymethod/    SUBCLASS picks the type
```

---

## The only difference (applies to both examples)

| | Simple Factory | Factory Method |
|---|----------------|----------------|
| **Who picks?** | **You** pass the type | **Subclass** decides |
| **Simple Factory** | `NotificationFactory.create(EMAIL)` | — |
| **Factory Method** | — | `new OtpService()` → always SMS inside |

---

## Example 1 — Notification (`notification/`)

**Simple Factory:** User picks channel in app settings.

```java
Notification sender = NotificationFactory.create(Channel.EMAIL);
sender.send("user@example.com", "Order shipped");
```

**Factory Method:** Caller picks the service; service picks the channel.

| Service | Creates |
|---------|---------|
| `OtpService` | SMS |
| `MarketingService` | Email |
| `OrderAlertService` | Push |

```java
NotificationService otp = new OtpService();
otp.notifyUser("+91-9876543210", "Your OTP is 482910");
```

**Run:**
```bash
cd 01-patterns/_01_creational/factory && mkdir -p out

javac -d out notification/common/*.java notification/simplefactory/*.java
java -cp out notification.simplefactory.Main

javac -d out notification/common/*.java notification/factorymethod/*.java
java -cp out notification.factorymethod.Main
```

---

## Example 2 — Cab service (`cab/`)

**Simple Factory:** User taps Mini / Bike / Premium on home screen.

```java
Ride ride = RideFactory.create(RideType.MINI);
ride.book("Indiranagar Metro", "Koramangala 5th Block");
```

**Factory Method:** Same `bookRide()` flow; city subclass applies fleet rules.

| Service | Rule |
|---------|------|
| `MumbaiBookingService` | Monsoon → bike upgrades to Mini Cab |
| `BangaloreBookingService` | All types available |

```java
RideBookingService mumbai = new MumbaiBookingService(true);
mumbai.bookRide(RideType.BIKE, "Andheri West", "Bandra Kurla Complex");
```

**Run:**
```bash
javac -d out cab/common/*.java cab/simplefactory/*.java
java -cp out cab.simplefactory.Main

javac -d out cab/common/*.java cab/simplefactory/*.java cab/factorymethod/*.java
java -cp out cab.factorymethod.Main
```

---

## Side-by-side mapping

| Concept | Notification | Cab |
|---------|--------------|-----|
| Product interface | `Notification` | `Ride` |
| Type enum | `Channel` | `RideType` |
| Simple factory class | `NotificationFactory` | `RideFactory` |
| Factory method parent | `NotificationService` | `RideBookingService` |
| Factory method subclass | `OtpService` | `MumbaiBookingService` |
| Template method | `notifyUser()` | `bookRide()` |

---

## Bad vs good

**Bad**
```java
if ("email".equals(type)) {
    new EmailNotification().send(to, msg);
} else if ("sms".equals(type)) {
    new SmsNotification().send(to, msg);
}
```

**Good**
```java
NotificationFactory.create(Channel.EMAIL).send(to, msg);
```

---

## Don't confuse with

- **Abstract Factory** — creates a *family* of related objects together
- **Builder** — step-by-step assembly of one complex object
- **Strategy** — swaps *algorithm*; Factory picks *which object*
