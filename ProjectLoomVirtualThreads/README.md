# Project Loom Virtual Threads Examples

This directory contains examples demonstrating Java's Project Loom Virtual Threads, introduced as a preview feature in JDK 19 and finalized in JDK 21.

## What are Virtual Threads?

Virtual threads are lightweight threads that dramatically reduce the effort of writing, maintaining, and observing high-throughput concurrent applications. They are designed to solve the scalability issues of traditional platform threads.

Key characteristics of virtual threads:
- **Lightweight**: Consume much less memory than platform threads
- **Abundant**: Can create millions of virtual threads in a single JVM
- **Efficient**: Automatically yield to other virtual threads when blocked on I/O
- **Compatible**: Work with existing Java concurrency APIs

## Requirements

- Java 21 or later (for stable virtual threads)
- Java 19-20 with `--enable-preview` flag (for preview virtual threads)

## Examples Included

1. **Basic Virtual Threads**
   - Creating and starting virtual threads
   - Comparing with platform threads

2. **Thread Pools**
   - Virtual thread executors
   - Comparing with traditional thread pools

3. **Performance Benchmarks**
   - Throughput comparison
   - Memory usage comparison
   - Scaling with concurrent tasks

4. **I/O Operations**
   - HTTP client example
   - File operations
   - Database access

5. **Structured Concurrency**
   - Using structured concurrency (preview feature)
   - Handling exceptions
   - Cancellation and timeouts

6. **Debugging and Monitoring**
   - Thread dumps
   - JFR events
   - Troubleshooting

## Key Concepts

### Virtual Threads vs Platform Threads

Platform threads (traditional Java threads) are wrappers around OS threads. Each platform thread consumes significant memory and has scheduling overhead. Virtual threads, on the other hand, are implemented in the JVM and are much more lightweight.

### Carrier Threads

Virtual threads are scheduled on a set of platform threads called "carrier threads." By default, the number of carrier threads equals the number of available processors. When a virtual thread performs a blocking operation, it is unmounted from its carrier thread, allowing the carrier thread to execute other virtual threads.

### Structured Concurrency

Structured concurrency is a programming paradigm that treats multiple related tasks as a single unit of work. It ensures that when a task splits into concurrent subtasks, they all complete before the parent task completes, making error handling and cancellation more straightforward.

## Running the Examples

To run the examples with Java 21:

```bash
java -cp . example.BasicVirtualThreadExample
```

To run with Java 19-20 (preview mode):

```bash
java --enable-preview -cp . example.BasicVirtualThreadExample
```

## Additional Resources

- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)
- [JEP 428: Structured Concurrency](https://openjdk.org/jeps/428)
- [Inside Java Podcast: Project Loom](https://inside.java/2021/11/30/podcast-015/)
- [Virtual Threads Documentation](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html)
