import { describe, expect, it } from 'vitest';
import { countCircuitTerminationActiveFilters, parseCircuitTerminationFilter, serializeCircuitTerminationFilter, toCircuitTerminationQuery } from '../filterState';

describe('circuitTermination filter state', () => {
  it('is empty by default', () => {
    expect(parseCircuitTerminationFilter(new URLSearchParams())).toEqual({});
    expect(countCircuitTerminationActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).externalRef).toBe('abc');
  });

  it('round trips circuitId', () => {
    const filter = { circuitId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
  });

  it('round trips side selection', () => {
    const filter = { side: ['A'] as never };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).side).toBe('A');
  });

  it('round trips portId', () => {
    const filter = { portId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
  });

  it('round trips xconnectId', () => {
    const filter = { xconnectId: 'abc' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).xconnectId).toBe('abc');
  });

  it('round trips patchInfo', () => {
    const filter = { patchInfo: 'abc' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).patchInfo).toBe('abc');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCircuitTerminationFilter(serializeCircuitTerminationFilter(filter))).toEqual(filter);
    expect(toCircuitTerminationQuery(filter).labels).toBe('abc');
  });
});
