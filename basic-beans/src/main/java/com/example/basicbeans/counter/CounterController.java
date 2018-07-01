package com.example.basicbeans.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test with http and ab.
 *
 * ab -c 20 -n 2000 http://localhost:8080/inc
 * http --session=s1 :8080/inc/session
 *
 */
@RestController
public class CounterController {

    private static Logger logger = LoggerFactory.getLogger(CounterController.class);

    /**
     * Unsafe because controller mapping functions are not thread-safe.
     */

    private int count = 0;

    @GetMapping("/inc")
    public int inc(){
        int ret = count++;
        logger.info("Thread[{}] /inc -> {}", Thread.currentThread().getName(), ret);
        return ret++;
    }

    /**
     * Safely increment a counter because we're using AtomicInteger.
     */

    private AtomicInteger safeCount = new AtomicInteger();

    @GetMapping("/inc/safe")
    public int incSafe(){
        int ret = safeCount.incrementAndGet();
        logger.info("Thread[{}] /inc/safe -> {}", Thread.currentThread().getName(), ret);
        return ret;
    }

    /**
     * This bean is scoped to a session so each user has their own counter.
     */

    @Autowired private Counter sessionScopedCounter;

    @GetMapping("/inc/session")
    public int incSessionScope(){
        int ret = sessionScopedCounter.incrementAndGet();
        logger.info("Thread[{}] uuid={} /inc/session -> {}", Thread.currentThread().getName(), sessionScopedCounter.getUUID(), ret);
        return ret;
    }

}
