package com.example.basicbeans.counter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@SessionScope
public class SessionScopedCounter implements Counter {

    private String uuid = UUID.randomUUID().toString();
    private AtomicInteger safeCount = new AtomicInteger();

    @Override
    public int incrementAndGet() {
        return safeCount.incrementAndGet();
    }

    @Override
    public String getUUID() { return uuid; }
}
