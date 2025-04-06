# Scanner vs BufferedReader Comparison

This document compares Java's `Scanner` and `BufferedReader` classes for reading input, explaining their differences, advantages, and best use cases.

## Overview

Both `Scanner` and `BufferedReader` are used to read input in Java, but they have different features, performance characteristics, and use cases.

## Scanner

`Scanner` was introduced in Java 1.5 and is part of the `java.util` package.

### Advantages

1. **Parsing Convenience**
   - Built-in methods for parsing different data types (`nextInt()`, `nextDouble()`, etc.)
   - No need for manual conversion from strings
   - Can parse input using regular expressions

2. **Flexibility**
   - Can read from various sources (files, strings, input streams)
   - Can change delimiter pattern (`useDelimiter()`)
   - Supports scanning for specific patterns

3. **Ease of Use**
   - More intuitive API for beginners
   - Less boilerplate code for parsing different data types
   - Handles tokenization automatically

### Disadvantages

1. **Performance**
   - Significantly slower than `BufferedReader` for reading large files
   - Uses regular expressions internally, which adds overhead
   - Not synchronized, which can be an issue in multi-threaded environments

2. **Resource Usage**
   - Higher memory usage due to internal buffers and regex machinery
   - Less efficient for large inputs

3. **Input Handling Issues**
   - Notorious for issues with mixing token-based and line-based reading
   - Requires careful handling of newline characters (e.g., using `nextLine()` after `nextInt()`)

## BufferedReader

`BufferedReader` has been part of Java since JDK 1.1 and is in the `java.io` package.

### Advantages

1. **Performance**
   - Much faster than `Scanner` for reading large files
   - Uses an internal buffer to reduce I/O operations
   - More efficient memory usage

2. **Simplicity**
   - Straightforward reading of text lines
   - Thread-safe (synchronized methods)
   - Clearer behavior with fewer surprises

3. **Resource Efficiency**
   - Lower memory footprint
   - More efficient for large inputs
   - Better buffer management

### Disadvantages

1. **Manual Parsing**
   - Only reads strings (`readLine()`)
   - Requires manual conversion to other data types
   - No built-in tokenization (need to use `String.split()` or `StringTokenizer`)

2. **Less Flexible**
   - Primarily designed for character input streams
   - No built-in pattern matching
   - Requires more code for complex parsing tasks

## Performance Comparison

In our performance tests (see `InputProcessingExample.java`), `BufferedReader` consistently outperforms `Scanner` when reading large files:

- `BufferedReader` is typically 2-10 times faster than `Scanner` for reading large text files
- The performance gap widens as file size increases
- For small files or console input, the difference is negligible

## When to Use Each

### Use Scanner When:

1. You need to parse different data types from input
2. You're working with small to medium-sized inputs
3. You need pattern matching or custom delimiters
4. Ease of use is more important than performance
5. You're reading user input from the console
6. You're parsing structured text with various data types

### Use BufferedReader When:

1. Performance is critical
2. You're reading large files
3. You're primarily reading line by line
4. Memory efficiency is important
5. You're implementing high-throughput I/O operations
6. You need thread-safety

## Modern Alternatives

For many use cases, Java's newer Stream API provides elegant alternatives:

```java
// Reading a file line by line with streams
List<String> lines = Files.lines(Paths.get("file.txt"))
                         .collect(Collectors.toList());

// Processing a file with streams
long wordCount = Files.lines(Paths.get("file.txt"))
                     .flatMap(line -> Stream.of(line.split("\\s+")))
                     .count();
```

## Conclusion

- **Scanner**: Choose for convenience, flexibility, and ease of parsing different data types, especially for smaller inputs or interactive console applications.

- **BufferedReader**: Choose for performance, efficiency, and simplicity when reading large files or when performance is critical.

In practice, the best choice depends on your specific requirements. For most general-purpose applications with moderate-sized inputs, either will work well. For high-performance applications or large file processing, `BufferedReader` is the better choice.
