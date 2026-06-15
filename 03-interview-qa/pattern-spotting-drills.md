# Pattern spotting drills

Self-test: "Design X" → which pattern + why?

| Prompt | Pattern(s) | Why |
|--------|------------|-----|
| Notification service — email, SMS, push | Factory + Strategy | Factory creates channel; Strategy could swap send logic |
| Payment gateway — UPI, card, wallet | Strategy | Runtime algorithm swap |
| Vending machine — idle, coin inserted, dispensing | State | Behavior changes with internal mode |
| Undo/redo in text editor | Command + Memento | Request as object + state snapshot |
| Rate limiter — token bucket vs sliding window | Strategy | Pluggable algorithms |
| DB connection pool — one shared instance | Singleton | Single global instance |
| Legacy XML API behind your REST service | Adapter | Interface mismatch |
| Coffee order — size, milk, whip, all optional | Builder | Step-by-step optional assembly |

## Drill format

1. Pick 3 rows — cover the answer, then check.
2. Say aloud: entities → relationships → pattern → why not the confusion pair.
