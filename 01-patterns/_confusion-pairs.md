# Confusion pairs

Five mix-ups that show up constantly in LLD interviews — with a quick disambiguation.

## 1. Strategy vs State

| | Strategy | State |
|---|----------|-------|
| **Who picks the behavior?** | Client injects / swaps algorithm | Object transitions internally |
| **Example** | Payment: UPI vs card vs wallet | Vending machine: idle → has coin → dispensing |
| **Test** | "User chooses payment method" → Strategy | "Machine mode changes on its own" → State |

## 2. Factory vs Abstract Factory

| | Factory Method | Abstract Factory |
|---|----------------|------------------|
| **Creates** | One product type | A *family* of related products |
| **Example** | `LoggerFactory.create("file")` → FileLogger | `WinFactory` → WinButton + WinCheckbox together |
| **Test** | "Which single class?" → Factory | "Whole UI kit / OS theme" → Abstract Factory |

## 3. Decorator vs Proxy

| | Decorator | Proxy |
|---|-----------|-------|
| **Intent** | Add responsibilities | Control access (lazy load, security, remote) |
| **Example** | Buffered stream on file stream | Lazy image proxy until scroll into view |
| **Test** | "Stack features" → Decorator | "Gatekeeper / stand-in" → Proxy |

## 4. Adapter vs Facade

| | Adapter | Facade |
|---|---------|--------|
| **Intent** | Make *one* incompatible thing fit | Simplify *many* things behind one API |
| **Example** | USB-C to HDMI dongle | `orderService.placeOrder()` hiding 5 subsystems |
| **Test** | "Legacy API mismatch" → Adapter | "Too many classes to call" → Facade |

## 5. Template Method vs Strategy

| | Template Method | Strategy |
|---|-----------------|----------|
| **Mechanism** | Inheritance — base defines skeleton | Composition — delegate to strategy object |
| **Example** | `DataParser.parse()` calls abstract `extract()` | Sort context holds `QuickSort` or `MergeSort` |
| **Test** | "Subclass overrides steps" → Template | "Inject algorithm interface" → Strategy |

## My mistakes

Track your own confusions in [../03-interview-qa/mistakes-log.md](../03-interview-qa/mistakes-log.md).
