package com.uniti.cil.itta.inventory.alarm.model;

/** Allowed values for Event Source Protocol. */
public enum EventSourceProtocol {
    SNMP_TRAP,
    SYSLOG,
    KAFKA,
    WEBHOOK
}
