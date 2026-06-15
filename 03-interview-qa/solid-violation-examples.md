# SOLID violation examples

"What is wrong here?" — spot the principle broken, then sketch the fix.

## 1. God class

```python
class OrderService:
    def validate(self): ...
    def calculate_tax(self): ...
    def charge_payment(self): ...
    def send_email(self): ...
    def update_inventory(self): ...
```

**Violation:** SRP — five reasons to change.

**Fix:** Split into `OrderValidator`, `TaxCalculator`, `PaymentService`, `NotificationService`, `InventoryService`.

## 2. Switch on type

```python
def export(data, fmt):
    if fmt == "json":
        return json.dumps(data)
    elif fmt == "csv":
        return to_csv(data)
    elif fmt == "xml":
        return to_xml(data)
```

**Violation:** OCP — new format means editing this function.

**Fix:** `Exporter` interface + one class per format (Strategy or simple registry).

## 3. Square extends Rectangle

```python
class Rectangle:
    def set_width(self, w): self.width = w
    def set_height(self, h): self.height = h

class Square(Rectangle):
    def set_width(self, w):
        self.width = self.height = w
```

**Violation:** LSP — square can't substitute rectangle where width/height are set independently.

## 4. Fat interface

```python
class Machine(ABC):
    @abstractmethod
    def print(self): ...
    @abstractmethod
    def scan(self): ...
    @abstractmethod
    def fax(self): ...
```

**Violation:** ISP — simple printer forced to implement scan/fax.

**Fix:** `Printable`, `Scannable`, `Faxable` separately.

## 5. High-level depends on concrete DB

```python
class ReportGenerator:
    def __init__(self):
        self.db = MySQLConnection()  # concrete
```

**Violation:** DIP — report logic tied to MySQL.

**Fix:** Inject `Database` abstraction.
