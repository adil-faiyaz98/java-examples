# Spring Boot Auto-Configuration Example

This directory contains examples demonstrating Spring Boot's auto-configuration mechanism, including how to create custom auto-configurations.

## What is Spring Boot Auto-Configuration?

Auto-configuration is a key feature of Spring Boot that automatically configures your Spring application based on the dependencies you have added to your project. It eliminates the need for extensive XML configuration or Java-based configuration, making Spring applications easier to set up and run.

## Key Concepts

1. **Conditional Configuration**: Auto-configuration is applied only when certain conditions are met, such as specific classes being present on the classpath or certain properties being defined.

2. **Configuration Properties**: Spring Boot allows you to externalize your configuration through properties files, which can be used to customize auto-configurations.

3. **Starter Dependencies**: Spring Boot starters are dependency descriptors that include all the necessary dependencies for a specific functionality, triggering the related auto-configurations.

4. **Auto-Configuration Classes**: These are classes annotated with `@Configuration` that provide beans based on conditions.

## Project Structure

- `application/` - Main Spring Boot application
- `autoconfigure/` - Custom auto-configuration module
- `service/` - Service module that will be auto-configured

## Examples Included

1. **Basic Auto-Configuration**: Demonstrates how Spring Boot auto-configures beans based on classpath dependencies.

2. **Custom Auto-Configuration**: Shows how to create your own auto-configuration for a custom service.

3. **Conditional Auto-Configuration**: Examples of various conditional annotations like `@ConditionalOnClass`, `@ConditionalOnMissingBean`, etc.

4. **Configuration Properties**: How to use `@ConfigurationProperties` to externalize and validate configuration.

5. **Auto-Configuration Ordering**: How to control the order in which auto-configurations are applied.

## How Auto-Configuration Works

1. Spring Boot applications are annotated with `@SpringBootApplication`, which includes `@EnableAutoConfiguration`.

2. `@EnableAutoConfiguration` tells Spring Boot to look for auto-configuration classes in the classpath.

3. Auto-configuration classes are listed in `META-INF/spring.factories` under the key `org.springframework.boot.autoconfigure.EnableAutoConfiguration`.

4. Each auto-configuration class is evaluated based on its conditions (`@ConditionalOn*` annotations).

5. If all conditions are met, the configuration is applied and the beans are created.

## Running the Examples

To run the examples, you need to have Maven installed. Then, you can run:

```bash
mvn clean install
mvn spring-boot:run -pl application
```

## Dependencies

This example requires:
- Spring Boot 2.5.0+
- Java 8+
- Maven 3.6+
