package com.example.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller that provides information about the auto-configuration process.
 * This is useful for understanding which auto-configurations were applied and why.
 */
@RestController
@RequestMapping("/api/auto-configuration")
public class AutoConfigurationExplainerController {

    private final ConfigurableApplicationContext context;

    @Autowired
    public AutoConfigurationExplainerController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    /**
     * Returns a report of all auto-configuration conditions that were evaluated.
     * 
     * @return the condition evaluation report
     */
    @GetMapping("/report")
    public String getAutoConfigurationReport() {
        ConditionEvaluationReport report = ConditionEvaluationReport.get(context.getBeanFactory());
        
        StringBuilder builder = new StringBuilder();
        builder.append("=== Auto-Configuration Report ===\n\n");
        
        // Positive matches (configurations that were applied)
        builder.append("Positive matches (auto-configuration was applied):\n");
        for (Map.Entry<String, ConditionOutcome> entry : report.getConditionAndOutcomesBySource().entrySet()) {
            String source = entry.getKey();
            if (source.contains("com.example.autoconfigure")) {
                ConditionOutcome outcome = entry.getValue();
                builder.append("- ").append(source).append("\n");
                builder.append("  Outcome: ").append(outcome.getMessage()).append("\n");
            }
        }
        
        // Negative matches (configurations that were not applied)
        builder.append("\nNegative matches (auto-configuration was not applied):\n");
        for (Map.Entry<String, ConditionOutcome> entry : report.getConditionAndOutcomesBySource().entrySet()) {
            String source = entry.getKey();
            if (source.contains("com.example.autoconfigure") && !report.getConditionAndOutcomesBySource().containsKey(source)) {
                ConditionOutcome outcome = entry.getValue();
                builder.append("- ").append(source).append("\n");
                builder.append("  Outcome: ").append(outcome.getMessage()).append("\n");
            }
        }
        
        // Beans created by our auto-configuration
        builder.append("\nBeans created by our auto-configuration:\n");
        for (String beanName : context.getBeanDefinitionNames()) {
            Object bean = context.getBean(beanName);
            if (bean.getClass().getName().startsWith("com.example.service")) {
                builder.append("- ").append(beanName).append(" (").append(bean.getClass().getName()).append(")\n");
            }
        }
        
        return builder.toString();
    }
}
