# Notification — Factory (first principles)

Real scenario: your app sends **Email**, **SMS**, or **Push**. Something has to pick the right class. Factory puts that decision in one place.

This folder has two variants:

```
notification/
├── common/           Notification, EmailNotification, SmsNotification, PushNotification
├── simplefactory/    YOU pick the channel
└── factorymethod/    SERVICE picks the channel
```

---

## Part 1 — Simple Factory (`simplefactory/`)

### If we don't use this, what happens?

Every class that sends a notification writes its own `if/else`:

```java
void sendOrderUpdate(String channel, String to, String message) {
    if ("email".equals(channel)) {
        new EmailNotification().send(to, message);
    } else if ("sms".equals(channel)) {
        new SmsNotification().send(to, message);
    } else if ("push".equals(channel)) {
        new PushNotification().send(to, message);
    }
    // Add WhatsApp? Edit OrderService, PaymentService, AuthService...
}
```

**Problems:**
- Same `if/else` copied in `OrderService`, `PaymentService`, `AuthService`
- Add WhatsApp → change every file
- Hard to test — can't swap a mock sender without touching business logic
- Business code knows `EmailNotification`, `SmsNotification` — tight coupling

---

### The code we wrote instead

**Step 1 — Product interface** (`common/Notification.java`)

```java
public interface Notification {
    void send(String to, String message);
}
```

**Step 2 — One factory maps channel → class** (`simplefactory/NotificationFactory.java`)

```java
public static Notification create(Channel channel) {
    return registry.get(channel).get();  // EMAIL → EmailNotification, etc.
}
```

**Step 3 — Client only asks the factory** (`simplefactory/Main.java`)

```java
Notification sender = NotificationFactory.create(Channel.EMAIL);
sender.send("user@example.com", "Your order #1234 has shipped");
```

Caller never writes `new EmailNotification()`.

---

### How it helps us

| Without factory | With Simple Factory |
|-----------------|---------------------|
| `if/else` in many classes | One map in `NotificationFactory` |
| Add channel → edit N files | Add channel → register once in factory |
| Client tied to concrete classes | Client uses `Notification` interface |
| Hard to mock in tests | Inject or replace factory registry |

**Principle:** *Creation logic belongs in one place. Callers say WHAT they want (EMAIL), not HOW to build it.*

---

### Interview follow-ups — Simple Factory

1. **Is Simple Factory a GoF design pattern?**  
   No — it's an **idiom**. GoF has Factory Method and Abstract Factory. Simple Factory is still widely used (Spring `getBean`, static factory methods).

2. **How do you add WhatsApp without changing callers?**  
   Add `WhatsAppNotification`, register in the factory map. `OrderService` unchanged.

3. **Simple Factory vs Strategy?**  
   Factory = *which object exists*. Strategy = *which algorithm runs* on an existing object. Notification creation is Factory; "how to format the message" could be Strategy.

4. **How do you test this?**  
   Mock `Notification` interface, or register a test double in the factory for unit tests.

5. **What SOLID principle does this support?**  
   **OCP** — extend with new notification types without modifying callers. **DIP** — depend on `Notification`, not concrete senders.

---

## Part 2 — Factory Method (`factorymethod/`)

### If we don't use this, what happens?

Each service hard-codes its channel inside `notifyUser()`:

```java
class OtpService {
    void notifyUser(String to, String message) {
        new SmsNotification().send(to, message);  // always SMS — but mixed with flow logic
    }
}

class MarketingService {
    void notifyUser(String to, String message) {
        new EmailNotification().send(to, message);
    }
}
```

Or worse — one giant method with service type checks:

```java
void notify(String serviceType, String to, String message) {
    if ("otp".equals(serviceType)) {
        new SmsNotification().send(to, message);
    } else if ("marketing".equals(serviceType)) {
        new EmailNotification().send(to, message);
    }
}
```

**Problems:**
- Shared steps (logging, retry, rate limit) duplicated or missing
- Can't reuse the same "notify" flow across services cleanly
- Adding a new service type means editing a central switch again

---

### The code we wrote instead

**Step 1 — Parent owns the flow** (`factorymethod/NotificationService.java`)

```java
public void notifyUser(String to, String message) {
    Notification notification = createNotification();  // subclass decides
    notification.send(to, message);
}

public abstract Notification createNotification();  // ← factory method
```

**Step 2 — Each subclass picks its channel**

```java
// OtpService.java
public Notification createNotification() {
    return new SmsNotification();
}

// MarketingService.java
public Notification createNotification() {
    return new EmailNotification();
}
```

**Step 3 — Caller picks the service, not the channel** (`factorymethod/Main.java`)

```java
NotificationService otp = new OtpService();
otp.notifyUser("+91-9876543210", "Your OTP is 482910");
// OTP service internally always uses SMS — caller never passes Channel
```

---

### How it helps us

| Without Factory Method | With Factory Method |
|------------------------|---------------------|
| Channel choice mixed into every service method | `createNotification()` isolated in subclass |
| Shared `notifyUser()` steps duplicated | Defined once in parent |
| New service = new if-branch in one god method | New service = new subclass |
| Caller might pass wrong channel for OTP | `OtpService` **enforces** SMS — can't send OTP via Email by mistake |

**Principle:** *When WHO creates the object varies by subclass, push creation into an overridable method. Parent keeps the algorithm; child picks the product.*

---

### Interview follow-ups — Factory Method

1. **What is the "factory method"?**  
   The abstract method `createNotification()` — each subclass overrides it to return a different `Notification`.

2. **Factory Method vs Simple Factory?**  
   Simple Factory: one class, you pass type. Factory Method: subclass hierarchy, type often decided inside subclass (caller picks `OtpService`, not SMS).

3. **Why not just use Simple Factory inside every service?**  
   You could (`NotificationFactory.create(SMS)` in OtpService). Factory Method is better when the **whole service class** represents a creation strategy and you want a shared template method (`notifyUser`).

4. **Template Method vs Factory Method — same class here?**  
   Yes — `notifyUser()` is a **Template Method** (fixed steps). `createNotification()` is the **Factory Method** (step that subclasses override). They often appear together.

5. **How would you add Slack notifications for internal alerts?**  
   New `InternalAlertService extends NotificationService` returning `SlackNotification`. No change to `OtpService` or `MarketingService`.

---

## Quick compare (both in this folder)

```
Simple Factory:
  User settings → Channel.EMAIL → NotificationFactory.create() → EmailNotification

Factory Method:
  Caller → new OtpService() → createNotification() → SmsNotification
                              ↑ subclass decides, caller never sees Channel
```

**Run:**
```bash
javac -d out notification/common/*.java notification/simplefactory/*.java
java -cp out notification.simplefactory.Main

javac -d out notification/common/*.java notification/factorymethod/*.java
java -cp out notification.factorymethod.Main
```
