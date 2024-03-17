# SOLID Principles of OOP

The SOLID principles are a set of design principles that help in creating maintainable and scalable object-oriented code. These principles provide guidelines for writing code that is easy to understand, test, and maintain.

- S - Single Responsibility Principle 
- O - Open/Closed Principle
- L - Liskov Substitution Principle
- I - Interface Segregation Principle
- D - Dependency Inversion Principle

The SOLID principles improve software quality by promoting maintainability, scalability, testability, flexibility, reusability, reduced coupling, and higher overall code quality.

### Single Responsibility Principle (SRP): 
This principle states that a class should have only one reason to change. It means that a **class should have only one responsibility or job**. This principle helps in keeping classes focused and promotes better code organization.


Example: 
- Bad example
```python
class User:
    def __init__(self, name: str):
            self.name = name

    def get_user_name(self):
        pass  # return user name

    def save_user(self):
        pass  # save user details to a database
```

- Good example
```python
class User:
    def __init__(self, name: str):
            self.name = name

    def get_user_name(self):
        pass  # return user name

class UserDB:
    def get_user(self, user: User):
        pass  # get a user

    def save_user(self, user: User):
        pass  # save user details to a database
```

### Open-Closed Principle (OCP): 
According to this principle, software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification. This means that **you should be able to add new functionality without changing the existing code**.

- Bad example
```python
class Rectangle:
    def __init__(self, width, height):
        self.width = width
        self.height = height

class AreaCalculator:
    def calculate_rectangle_area(self, rectangle):
        return rectangle.width * rectangle.height
```

- Good example
```python
class Shape:
    def area(self):
        pass

class Rectangle(Shape):
    def __init__(self, width, height):
        self.width = width
        self.height = height

    def area(self):
        return self.width * self.height

class AreaCalculator:
    def calculate_area(self, shape):
        return shape.area()
```

### Liskov Substitution Principle (LSP): 
This principle states that if a program is using a base class, it should be able to use any of its subclasses without the program knowing it. In other words, the ***subclasses should be substitutable for their base class***.

- Bad example
```python
class Bird:
    def fly(self):
        pass

class Ostrich(Bird):
    def fly(self):
        pass  # Ostrich can't fly!
```

- Good example
```python
class Bird:
    def fly(self):
        pass

class FlightlessBird(Bird):
    pass  # Doesn't have fly method

class Ostrich(FlightlessBird):
    pass  # Inherits from FlightlessBird
```

### Interface Segregation Principle (ISP): 
This principle suggests that clients should not be forced to depend on interfaces they do not use. This means that a **class should not have to implement methods it doesn't need**.

- Bad example
```python
class Worker:
    def work(self):
        pass

    def eat(self):
        pass
```
- Good example
```python
class Workable:
    def work(self):
        pass

class Eatable:
    def eat(self):
        pass

class Worker(Workable, Eatable):
    pass  # Worker now has methods it can use without any unnecessary ones
```

### Dependency Inversion Principle (DIP): 
This principle states that high-level modules should not depend on low-level modules. Both should depend on abstractions. Also, **abstractions should not depend on details. Details should depend on abstractions**.

- Bad example
```python
class LightBulb:
    def turn_on(self):
        pass

    def turn_off(self):
        pass

class ElectricPowerSwitch:
    def __init__(self, light_bulb):
        self.light_bulb = light_bulb
        self.on = False

    def press(self):
        if self.on:
            self.light_bulb.turn_off()
            self.on = False
        else:
            self.light_bulb.turn_on()
            self.on = True
```

- Good example
```python
class Switchable(ABC):
    @abstractmethod
    def turn_on(self):
        pass

    @abstractmethod
    def turn_off(self):
        pass

class LightBulb(Switchable):
    def turn_on(self):
        pass

    def turn_off(self):
        pass

class ElectricPowerSwitch:
    def __init__(self, device: Switchable):
        self.device = device
        self.on = False

    def press(self):
        if self.on:
            self.device.turn_off()
            self.on = False
        else:
            self.device.turn_on()
            self.on = True
```