package com.apples.riverbus.prometheus;

import com.apples.riverbus.service.SpeedService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
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
