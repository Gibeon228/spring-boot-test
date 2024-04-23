package ru.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.netty.resources.ConnectionProvider;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyMetric {
    private final AtomicInteger count;


    public MyMetric(MeterRegistry meterRegistry) {
        count = meterRegistry.gauge("Count", new AtomicInteger());
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 0)
    public void schedulingTask() {
        int random = (int) (Math.random()*100);
        count.set(random);

    }
}
