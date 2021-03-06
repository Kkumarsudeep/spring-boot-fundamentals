package org.example.ws.actuator.health;

import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GreetingHealthIndicator implements HealthIndicator {

    /**
     * The GreetingService business service.
     */
    @Autowired
    private GreetingService greetingService;

    @Override
    public Health health() {

        // Assess the application's Greeting health. If the application's
        // Greeting components have data to service user requests, the Greeting
        // component is considered 'healthy', otherwise it is not.

        Collection<Greeting> greetings = greetingService.findAll();

        if (greetings == null || greetings.size() == 0) {
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", greetings.size()).build();
    }

}
