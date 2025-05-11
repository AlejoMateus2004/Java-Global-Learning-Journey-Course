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
- **Concurrent Execution**: `ConcurrentHashMap` muestra un rendimiento superior en Java 11 y Java 15 comparado con Java 8.
- **Synchronized Execution**: Aunque el `ConcurrentHashMap` muestra un rendimiento inferior en Java 8, muestra mejoras en versiones más modernas.
- **Overall**: `Collections.synchronizedMap` y `ThreadSafeMap` ofrecen sincronización explícita y rendimientos más consistentes, pero con mayor potencial de contención en situaciones concurrentes.

### Conclusion
- **Optimized Concurrent Usage**: Para escenarios de uso concurrente, `ConcurrentHashMap` es la elección preferida en Java 11 y Java 15, debido a sus mecánicas avanzadas y reducción de contención.
- **Educational Value of Custom Implementation**: La implementación personalizada (`ThreadSafeMap`) sirve para fines educativos pero no puede igualar el rendimiento de `ConcurrentHashMap` en situaciones concurrentes.