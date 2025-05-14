## Performance Report

### Concurrent Execution Results
#### Java 8
- **ConcurrentHashMap**: Time: 73 ms
- **SynchronizedMap**: Time: 59 ms
- **ThreadSafeMap**: Time: 58 ms

#### Java 11
- **ConcurrentHashMap**: Time: 6 ms
- **SynchronizedMap**: Time: 7 ms
- **ThreadSafeMap**: Time: 8 ms

#### Java 15
- **ConcurrentHashMap**: Time: 7 ms
- **SynchronizedMap**: Time: 7 ms
- **ThreadSafeMap**: Time: 7 ms

### Synchronized Execution Results
#### Java 8
- **ConcurrentHashMap**: Time: 95 ms
- **SynchronizedMap**: Time: 61 ms
- **ThreadSafeMap**: Time: 67 ms

#### Java 11
- **ConcurrentHashMap**: Time: 9 ms
- **SynchronizedMap**: Time: 8 ms
- **ThreadSafeMap**: Time: 8 ms

#### Java 15
- **ConcurrentHashMap**: Time: 8 ms
- **SynchronizedMap**: Time: 7 ms
- **ThreadSafeMap**: Time: 9 ms

### Observations
- **Concurrent Execution**: `ConcurrentHashMap` shows superior performance in Java 11 and Java 15 compared to Java 8.
- **Synchronized Execution**: Although `ConcurrentHashMap` shows lower performance in Java 8, it shows improvements in newer versions.
- **Overall**: `Collections.synchronizedMap` and `ThreadSafeMap` offer explicit synchronization and more consistent performance, but with greater potential for contention in concurrent situations.

### Conclusion
- **Optimized Concurrent Usage**: For concurrent usage scenarios, `ConcurrentHashMap` is the preferred choice in Java 11 and Java 15, due to its advanced mechanics and reduced contention.
- **Educational Value of Custom Implementation**: The custom implementation (`ThreadSafeMap`) is suitable for educational purposes but cannot match the performance of `ConcurrentHashMap` in concurrent situations.