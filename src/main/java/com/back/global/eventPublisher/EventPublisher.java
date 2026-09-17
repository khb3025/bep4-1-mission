package com.back.global.eventPublisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(Object event) {
        applicationEventPublisher.publishEvent(event);
    }
}
