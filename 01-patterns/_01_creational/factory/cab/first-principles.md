# Cab service — Factory (first principles)

Real scenario: Ola / Uber app — user picks **Mini**, **Bike**, or **Premium**. Different cities may apply different rules (e.g. no bikes in Mumbai monsoon). Factory handles *who creates the ride object*.

This folder has two variants:

```
cab/
├── common/           Ride, MiniCabRide, BikeRide, PremiumSuvRide
├── simplefactory/    USER picks ride type on home screen
└── factorymethod/    CITY picks / adjusts ride based on local rules
```

---

## Part 1 — Simple Factory (`simplefactory/`)

### If we don't use this, what happens?

The home screen or booking service creates rides directly:

```java
void bookRide(String type, String pickup, String drop) {
    if ("mini".equals(type)) {
        new MiniCabRide().book(pickup, drop);
    } else if ("bike".equals(type)) {
        new BikeRide().book(pickup, drop);
    } else if ("premium".equals(type)) {
        new PremiumSuvRide().book(pickup, drop);
    }
    // New Auto ride? Edit this method again
}
```

**Problems:**
- Home screen, fare estimator, and trip history all duplicate the same `if/else`
- Fare logic lives in ride classes, but *which* class to pick is scattered
- Add a new vehicle type → touch every screen that books a ride
- UI code depends on `MiniCabRide`, `BikeRide` — hard to change fleet

---

### The code we wrote instead

**Step 1 — Product interface** (`common/Ride.java`)

```java
public interface Ride {
    String getVehicleType();
    double estimateFare(double distanceKm);
    void book(String pickup, String drop);
}
```

**Step 2 — One factory maps type → ride** (`simplefactory/RideFactory.java`)

```java
public static Ride create(RideType type) {
    return registry.get(type).get();  // MINI → MiniCabRide, etc.
}
```

**Step 3 — UI only passes the enum** (`simplefactory/Main.java`)

```java
Ride ride = RideFactory.create(RideType.MINI);
ride.book("Indiranagar Metro", "Koramangala 5th Block");
```

Home screen never calls `new MiniCabRide()`.

---

### How it helps us

| Without factory | With Simple Factory |
|-----------------|---------------------|
| `if/else` on every screen | One registry in `RideFactory` |
| Add Auto pod → edit many files | Add `AutoRide` + one registry entry |
| UI imports all concrete ride classes | UI only knows `Ride` and `RideType` |
| Fare preview and booking use different creation logic | Both call `RideFactory.create()` |

**Principle:** *The home screen says "I want MINI". It doesn't need to know how a Mini Cab is built.*

---

### Interview follow-ups — Simple Factory

1. **Where would Simple Factory sit in a real cab app?**  
   Between UI and domain — when user taps a tile, controller calls `RideFactory.create(selectedType)`.

2. **How do you add a new ride type (e.g. Auto)?**  
   Create `AutoRide implements Ride`, register in `RideFactory`. Home screen loop over `RideType.values()` picks it up automatically.

3. **Does this violate Open-Closed Principle if `create()` has a switch?**  
   A big switch in one method is weak OCP. A **registry map** (what we use) is better — add entries without editing `create()` logic.

4. **Simple Factory vs calling `new MiniCabRide()` once?**  
   For one place, `new` is fine. Factory pays off when **many callers** create objects and types grow over time.

5. **How is this different from Strategy?**  
   Strategy swaps *pricing algorithm* at runtime. Factory creates *which ride object exists*. You might use both: Factory creates ride, Strategy calculates surge pricing.

---

## Part 2 — Factory Method (`factorymethod/`)

### If we don't use this, what happens?

City-specific rules live in one booking method:

```java
void bookRide(String city, RideType type, String pickup, String drop) {
    if ("Mumbai".equals(city) && isMonsoon() && type == BIKE) {
        new MiniCabRide().book(pickup, drop);  // forced upgrade
    } else if ("Bangalore".equals(city)) {
        // normal logic...
        if (type == MINI) new MiniCabRide().book(pickup, drop);
        // ...
    }
    // Delhi, Chennai... method keeps growing
}
```

**Problems:**
- One method knows every city's fleet rules
- Add Delhi → edit the same god method
- Mumbai monsoon logic mixed with Bangalore normal flow
- Hard to unit test Mumbai rules in isolation

---

### The code we wrote instead

**Step 1 — Parent owns booking flow** (`factorymethod/RideBookingService.java`)

```java
public void bookRide(RideType type, String pickup, String drop) {
    Ride ride = createRide(type);   // subclass decides
    ride.book(pickup, drop);
}

public abstract Ride createRide(RideType type);  // ← factory method
```

**Step 2 — Each city overrides creation**

```java
// MumbaiBookingService.java
public Ride createRide(RideType type) {
    if (monsoonMode && type == RideType.BIKE) {
        return new MiniCabRide();  // upgrade — user asked bike, got mini
    }
    return RideFactory.create(type);
}

// BangaloreBookingService.java
public Ride createRide(RideType type) {
    return RideFactory.create(type);  // no special rules
}
```

**Step 3 — App picks city service, not ride rules** (`factorymethod/Main.java`)

```java
RideBookingService mumbai = new MumbaiBookingService(true);
mumbai.bookRide(RideType.BIKE, "Andheri West", "Bandra Kurla Complex");
// Mumbai applies monsoon rule internally — caller just says BIKE
```

---

### How it helps us

| Without Factory Method | With Factory Method |
|------------------------|---------------------|
| All city rules in one `if (city)` block | Each city = one subclass |
| Add Delhi → edit central method | Add `DelhiBookingService` — nothing else changes |
| Mumbai monsoon untestable in isolation | Test `MumbaiBookingService` alone |
| Booking flow duplicated per city | `bookRide()` written once in parent |

**Principle:** *When creation rules vary by context (city, tenant, plan), give each context its own subclass with a factory method.*

---

### Interview follow-ups — Factory Method

1. **Why does Mumbai use Simple Factory inside Factory Method?**  
   Common cases (MINI, PREMIUM) delegate to `RideFactory`. Only the **exception** (monsoon bike → mini) lives in `MumbaiBookingService`. That's normal — factory method handles variation, simple factory handles the default map.

2. **Factory Method vs putting city in an if-statement?**  
   If-statement works for 2 cities. Factory Method scales when rules diverge and you want each city independently testable and deployable.

3. **Who picks the subclass — `MumbaiBookingService` or `BangaloreBookingService`?**  
   Usually a higher layer: `CityConfig.getBookingService()` or GPS detects city. Caller gets the right service without knowing Mumbai's monsoon rules.

4. **Could this be Strategy instead?**  
   If only the *creation rule* differs, Factory Method fits. If the entire *booking algorithm* differs (different steps, not just which ride), consider Strategy for the booking flow itself.

5. **What if pricing also differs by city?**  
   Factory Method picks the ride object. Pricing could be a separate Strategy injected into `Ride` or `RideBookingService` — patterns compose.

---

## Notification vs Cab — same pattern, different story

| | Notification | Cab |
|---|--------------|-----|
| Simple Factory — who picks? | User picks Email/SMS/Push | User picks Mini/Bike/Premium |
| Factory Method — who picks? | Service picks (OTP→SMS) | City picks (Mumbai monsoon rule) |
| Factory method name | `createNotification()` | `createRide(RideType)` |
| Template method | `notifyUser()` | `bookRide()` |

---

## Quick compare (both in this folder)

```
Simple Factory:
  User taps MINI → RideFactory.create(MINI) → MiniCabRide

Factory Method:
  App in Mumbai → MumbaiBookingService.bookRide(BIKE)
                  → createRide() upgrades to MiniCabRide during monsoon
                  → caller never knew the rule existed
```

**Run:**
```bash
javac -d out cab/common/*.java cab/simplefactory/*.java
java -cp out cab.simplefactory.Main

javac -d out cab/common/*.java cab/simplefactory/*.java cab/factorymethod/*.java
java -cp out cab.factorymethod.Main
```
