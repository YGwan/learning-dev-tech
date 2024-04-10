package org.example.prometheus;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.service.SpeedService;
import org.springframework.stereotype.Component;

@Component
public class PrometheusMeterRegister {

    public PrometheusMeterRegister(SpeedService speedService, MeterRegistry meterRegistry) {
        registerSpeed(speedService, meterRegistry);
    }

    private void registerSpeed(SpeedService speedService, MeterRegistry meterRegistry) {
        Gauge.builder("speed", speedService::getSpeed)
                .register(meterRegistry);
    }
}
