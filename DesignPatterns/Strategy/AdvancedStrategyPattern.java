/**
 * Advanced Strategy Design Pattern Example
 * 
 * This example demonstrates more advanced concepts of the Strategy pattern:
 * 1. Dynamic strategy selection based on input
 * 2. Strategy factory for creating strategies
 * 3. Default strategies
 * 4. Strategy composition
 * 
 * The example uses a text formatting system that can apply different formatting strategies
 * to text based on the context and requirements.
 */
package design.patterns.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedStrategyPattern {
    
    public static void main(String[] args) {
        // Create a text processor with default strategy
        TextProcessor processor = new TextProcessor();
        
        String inputText = "This is a sample text that will be formatted using different strategies.";
        
        // Format with default strategy
        System.out.println("Default formatting:");
        System.out.println(processor.formatText(inputText));
        System.out.println();
        
        // Format with HTML strategy
        System.out.println("HTML formatting:");
        processor.setFormattingStrategy(new HtmlFormattingStrategy());
        System.out.println(processor.formatText(inputText));
        System.out.println();
        
        // Format with Markdown strategy
        System.out.println("Markdown formatting:");
        processor.setFormattingStrategy(new MarkdownFormattingStrategy());
        System.out.println(processor.formatText(inputText));
        System.out.println();
        
        // Using the strategy factory
        System.out.println("Using strategy factory:");
        FormattingStrategyFactory factory = new FormattingStrategyFactory();
        
        processor.setFormattingStrategy(factory.createStrategy("json"));
        System.out.println("JSON formatting:");
        System.out.println(processor.formatText(inputText));
        System.out.println();
        
        // Using composite strategy
        System.out.println("Using composite strategy (Markdown + Encryption):");
        CompositeFormattingStrategy compositeStrategy = new CompositeFormattingStrategy();
        compositeStrategy.addStrategy(new MarkdownFormattingStrategy());
        compositeStrategy.addStrategy(new EncryptionFormattingStrategy());
        
        processor.setFormattingStrategy(compositeStrategy);
        System.out.println(processor.formatText(inputText));
        System.out.println();
        
        // Using context-aware strategy selector
        System.out.println("Using context-aware strategy selector:");
        ContextAwareTextProcessor contextProcessor = new ContextAwareTextProcessor();
        
        System.out.println("HTML document:");
        System.out.println(contextProcessor.formatText(inputText, "html"));
        
        System.out.println("\nMarkdown document:");
        System.out.println(contextProcessor.formatText(inputText, "md"));
        
        System.out.println("\nSecret document:");
        System.out.println(contextProcessor.formatText(inputText, "secret"));
    }
}

/**
 * Strategy interface for text formatting
 */
interface FormattingStrategy {
    String format(String text);
}

/**
 * Concrete Strategy: Plain Text Formatting (default)
 */
class PlainTextFormattingStrategy implements FormattingStrategy {
    @Override
    public String format(String text) {
        return text;
    }
}

/**
 * Concrete Strategy: HTML Formatting
 */
class HtmlFormattingStrategy implements FormattingStrategy {
    @Override
    public String format(String text) {
        return "<html>\n<body>\n    <p>" + text + "</p>\n</body>\n</html>";
    }
}

/**
 * Concrete Strategy: Markdown Formatting
 */
class MarkdownFormattingStrategy implements FormattingStrategy {
    @Override
    public String format(String text) {
        return "# Formatted Text\n\n" + text + "\n\n*Formatted with Markdown*";
    }
}

/**
 * Concrete Strategy: JSON Formatting
 */
class JsonFormattingStrategy implements FormattingStrategy {
    @Override
    public String format(String text) {
        return "{\n    \"content\": \"" + text.replace("\"", "\\\"") + "\",\n    \"format\": \"json\"\n}";
    }
}

/**
 * Concrete Strategy: Encryption Formatting
 */
class EncryptionFormattingStrategy implements FormattingStrategy {
    @Override
    public String format(String text) {
        // Simple Caesar cipher for demonstration (shift by 1)
        StringBuilder encrypted = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                encrypted.append((char) ((c - base + 1) % 26 + base));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }
}

/**
 * Composite Strategy: Combines multiple strategies
 */
class CompositeFormattingStrategy implements FormattingStrategy {
    private List<FormattingStrategy> strategies = new ArrayList<>();
    
    public void addStrategy(FormattingStrategy strategy) {
        strategies.add(strategy);
    }
    
    @Override
    public String format(String text) {
        String result = text;
        for (FormattingStrategy strategy : strategies) {
            result = strategy.format(result);
        }
        return result;
    }
}

/**
 * Strategy Factory: Creates appropriate strategies based on format type
 */
class FormattingStrategyFactory {
    private Map<String, FormattingStrategy> strategies = new HashMap<>();
    
    public FormattingStrategyFactory() {
        // Register default strategies
        strategies.put("plain", new PlainTextFormattingStrategy());
        strategies.put("html", new HtmlFormattingStrategy());
        strategies.put("markdown", new MarkdownFormattingStrategy());
        strategies.put("md", new MarkdownFormattingStrategy());
        strategies.put("json", new JsonFormattingStrategy());
        strategies.put("encrypt", new EncryptionFormattingStrategy());
    }
    
    public FormattingStrategy createStrategy(String formatType) {
        FormattingStrategy strategy = strategies.get(formatType.toLowerCase());
        if (strategy == null) {
            // Return default strategy if requested type is not found
            return strategies.get("plain");
        }
        return strategy;
    }
    
    public void registerStrategy(String formatType, FormattingStrategy strategy) {
        strategies.put(formatType.toLowerCase(), strategy);
    }
}

/**
 * Context: Text Processor
 */
class TextProcessor {
    private FormattingStrategy formattingStrategy;
    
    public TextProcessor() {
        // Default strategy
        this.formattingStrategy = new PlainTextFormattingStrategy();
    }
    
    public void setFormattingStrategy(FormattingStrategy formattingStrategy) {
        this.formattingStrategy = formattingStrategy;
    }
    
    public String formatText(String text) {
        return formattingStrategy.format(text);
    }
}

/**
 * Advanced Context: Context-Aware Text Processor
 * Automatically selects the appropriate strategy based on context
 */
class ContextAwareTextProcessor {
    private FormattingStrategyFactory strategyFactory;
    
    public ContextAwareTextProcessor() {
        this.strategyFactory = new FormattingStrategyFactory();
        
        // Register a composite strategy for "secret" documents
        CompositeFormattingStrategy secretStrategy = new CompositeFormattingStrategy();
        secretStrategy.addStrategy(new MarkdownFormattingStrategy());
        secretStrategy.addStrategy(new EncryptionFormattingStrategy());
        
        strategyFactory.registerStrategy("secret", secretStrategy);
    }
    
    public String formatText(String text, String documentType) {
        // Select strategy based on document type
        FormattingStrategy strategy = strategyFactory.createStrategy(documentType);
        return strategy.format(text);
    }
}
