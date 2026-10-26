package com.uniti.cil.itta.inventory.common.event;

public interface EventPublisher {
    void publish(DomainEvent event);
}
