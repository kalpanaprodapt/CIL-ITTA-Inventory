import { describe, expect, it } from 'vitest';
import { countEventSourceActiveFilters, parseEventSourceFilter, serializeEventSourceFilter, toEventSourceQuery } from '../filterState';

describe('eventSource filter state', () => {
  it('is empty by default', () => {
    expect(parseEventSourceFilter(new URLSearchParams())).toEqual({});
    expect(countEventSourceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips sourceName', () => {
    const filter = { sourceName: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).sourceName).toBe('abc');
  });

  it('round trips protocol selection', () => {
    const filter = { protocol: ['SNMP_TRAP'] as never };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).protocol).toBe('SNMP_TRAP');
  });

  it('round trips endpoint', () => {
    const filter = { endpoint: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).endpoint).toBe('abc');
  });

  it('round trips heartbeatSeconds range', () => {
    const filter = { heartbeatSecondsMin: 1, heartbeatSecondsMax: 9 };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).heartbeatSecondsMax).toBe(9);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseEventSourceFilter(serializeEventSourceFilter(filter))).toEqual(filter);
    expect(toEventSourceQuery(filter).owner).toBe('abc');
  });
});
