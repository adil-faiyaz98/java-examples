/**
 * Advanced Builder Design Pattern Example
 * 
 * This example demonstrates more advanced concepts of the Builder pattern:
 * 1. Multiple builders for the same product
 * 2. Required vs. optional parameters
 * 3. Builder inheritance
 * 4. Step-by-step construction with validation
 */
package design.patterns.builder;

import java.util.ArrayList;
import java.util.List;

public class AdvancedBuilderPattern {
    
    public static void main(String[] args) {
        // Example 1: Using the meal builder with step-by-step construction
        System.out.println("Building a custom meal:");
        
        Meal customMeal = new MealBuilder()
                .prepareMeal("Deluxe Dinner")
                .addMainCourse("Grilled Salmon")
                .addSide("Roasted Vegetables")
                .addDrink("Sparkling Water")
                .addDessert("Chocolate Cake")
                .build();
        
        System.out.println(customMeal);
        
        System.out.println("\n------------------------\n");
        
        // Example 2: Using the HTML builder to create a simple document
        System.out.println("Building an HTML document:");
        
        String html = new HTMLBuilder("Product Page")
                .addHeader("Our Amazing Product", 1)
                .addParagraph("This is the best product ever created!")
                .addImage("product.jpg", "Product Image")
                .addHeader("Features", 2)
                .beginList()
                    .addListItem("High quality")
                    .addListItem("Affordable price")
                    .addListItem("Easy to use")
                .endList()
                .addButton("Buy Now", "order.html")
                .build();
        
        System.out.println(html);
    }
}

/**
 * Example 1: Meal Builder with step-by-step construction
 */
class Meal {
    private final String name;
    private final String mainCourse;
    private final List<String> sides = new ArrayList<>();
    private final List<String> drinks = new ArrayList<>();
    private final List<String> desserts = new ArrayList<>();
    
    Meal(MealBuilder builder) {
        this.name = builder.name;
        this.mainCourse = builder.mainCourse;
        this.sides.addAll(builder.sides);
        this.drinks.addAll(builder.drinks);
        this.desserts.addAll(builder.desserts);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Meal: ").append(name).append("\n");
        sb.append("- Main Course: ").append(mainCourse).append("\n");
        
        if (!sides.isEmpty()) {
            sb.append("- Sides:\n");
            sides.forEach(side -> sb.append("  * ").append(side).append("\n"));
        }
        
        if (!drinks.isEmpty()) {
            sb.append("- Drinks:\n");
            drinks.forEach(drink -> sb.append("  * ").append(drink).append("\n"));
        }
        
        if (!desserts.isEmpty()) {
            sb.append("- Desserts:\n");
            desserts.forEach(dessert -> sb.append("  * ").append(dessert).append("\n"));
        }
        
        return sb.toString();
    }
}

class MealBuilder {
    // Required parameters
    String name;
    String mainCourse;
    
    // Optional parameters
    List<String> sides = new ArrayList<>();
    List<String> drinks = new ArrayList<>();
    List<String> desserts = new ArrayList<>();
    
    /**
     * Step 1: Prepare the meal (required)
     */
    public MealBuilder prepareMeal(String name) {
        this.name = name;
        return this;
    }
    
    /**
     * Step 2: Add main course (required)
     */
    public MealBuilder addMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
        return this;
    }
    
    /**
     * Optional: Add a side dish
     */
    public MealBuilder addSide(String side) {
        this.sides.add(side);
        return this;
    }
    
    /**
     * Optional: Add a drink
     */
    public MealBuilder addDrink(String drink) {
        this.drinks.add(drink);
        return this;
    }
    
    /**
     * Optional: Add a dessert
     */
    public MealBuilder addDessert(String dessert) {
        this.desserts.add(dessert);
        return this;
    }
    
    /**
     * Build the meal with validation
     */
    public Meal build() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Meal must have a name");
        }
        
        if (mainCourse == null || mainCourse.trim().isEmpty()) {
            throw new IllegalStateException("Meal must have a main course");
        }
        
        return new Meal(this);
    }
}

/**
 * Example 2: HTML Builder with method chaining and nested elements
 */
class HTMLBuilder {
    private final StringBuilder html = new StringBuilder();
    private boolean inList = false;
    
    /**
     * Constructor with required title parameter
     */
    public HTMLBuilder(String title) {
        html.append("<!DOCTYPE html>\n")
            .append("<html>\n")
            .append("<head>\n")
            .append("  <title>").append(title).append("</title>\n")
            .append("</head>\n")
            .append("<body>\n");
    }
    
    /**
     * Add a header element
     */
    public HTMLBuilder addHeader(String text, int level) {
        if (level < 1) level = 1;
        if (level > 6) level = 6;
        
        html.append("  <h").append(level).append(">")
            .append(text)
            .append("</h").append(level).append(">\n");
        
        return this;
    }
    
    /**
     * Add a paragraph element
     */
    public HTMLBuilder addParagraph(String text) {
        html.append("  <p>").append(text).append("</p>\n");
        return this;
    }
    
    /**
     * Add an image element
     */
    public HTMLBuilder addImage(String src, String alt) {
        html.append("  <img src=\"").append(src).append("\" alt=\"")
            .append(alt).append("\">\n");
        
        return this;
    }
    
    /**
     * Begin an unordered list
     */
    public HTMLBuilder beginList() {
        if (!inList) {
            html.append("  <ul>\n");
            inList = true;
        }
        return this;
    }
    
    /**
     * Add a list item (must be inside a list)
     */
    public HTMLBuilder addListItem(String text) {
        if (inList) {
            html.append("    <li>").append(text).append("</li>\n");
        }
        return this;
    }
    
    /**
     * End the current list
     */
    public HTMLBuilder endList() {
        if (inList) {
            html.append("  </ul>\n");
            inList = false;
        }
        return this;
    }
    
    /**
     * Add a button element
     */
    public HTMLBuilder addButton(String text, String href) {
        html.append("  <a href=\"").append(href).append("\" class=\"button\">")
            .append(text).append("</a>\n");
        
        return this;
    }
    
    /**
     * Build the final HTML document
     */
    public String build() {
        // Close any open lists
        if (inList) {
            endList();
        }
        
        // Close the HTML document
        html.append("</body>\n")
            .append("</html>");
        
        return html.toString();
    }
}
