# Trigger table

If the interviewer says X, reach for Y.

| Interviewer says… | Reach for… | Why |
|-------------------|----------|-----|
| "Only one config / connection / cache manager" | Singleton | Global single instance |
| "Create object but caller shouldn't know concrete class" | Factory Method | Creation delegated to factory |
| "Switch type based on string / enum at runtime" | Simple Factory | Central place maps input → class |
| "Windows vs Mac UI components as a set" | Abstract Factory | Family of related products |
| "Build object with many optional fields" | Builder | Step-by-step construction |
| "Copy existing object cheaply" | Prototype | Clone vs `new` |
| "Third-party API doesn't match ours" | Adapter | Interface translation |
| "Add features without exploding subclasses" | Decorator | Wrap and extend |
| "Hide 10 classes behind one method" | Facade | Simplified subsystem API |
| "Lazy load / access control / remote stub" | Proxy | Controlled surrogate |
| "Abstraction and implementation evolve separately" | Bridge | Two independent hierarchies |
| "File system — files and folders same interface" | Composite | Part-whole tree |
| "Thousands of identical glyphs / tiles" | Flyweight | Shared intrinsic state |
| "Swap payment / shipping / compression algorithm" | Strategy | Pluggable algorithms |
| "Notify all listeners when data changes" | Observer | Pub-sub |
| "Object behaves differently in each mode" | State | Internal state drives behavior |
| "Undo, redo, job queue, macro" | Command | Request as object |
| "Same workflow, different steps in subclasses" | Template Method | Fixed skeleton, variable steps |
| "Approval chain / logging filters / middleware" | Chain of Responsibility | Pass until handled |
| "Traverse collection without exposing it" | Iterator | External traversal |
| "Chat room — users shouldn't reference each other" | Mediator | Central coordinator |
| "Save game / undo editor state" | Memento | Snapshot without breaking encapsulation |
| "Add export-to-JSON on existing AST nodes" | Visitor | New operation on stable hierarchy |
| "Parse simple grammar / rule expressions" | Interpreter | Grammar as object structure |
