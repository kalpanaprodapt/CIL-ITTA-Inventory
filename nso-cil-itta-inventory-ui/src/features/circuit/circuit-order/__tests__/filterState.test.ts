import { describe, expect, it } from 'vitest';
import { countCircuitOrderActiveFilters, parseCircuitOrderFilter, serializeCircuitOrderFilter, toCircuitOrderQuery } from '../filterState';

describe('circuitOrder filter state', () => {
  it('is empty by default', () => {
    expect(parseCircuitOrderFilter(new URLSearchParams())).toEqual({});
    expect(countCircuitOrderActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).externalRef).toBe('abc');
  });

  it('round trips circuitId', () => {
    const filter = { circuitId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
  });

  it('round trips orderNumber', () => {
    const filter = { orderNumber: 'abc' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).orderNumber).toBe('abc');
  });

  it('round trips orderedOn window', () => {
    const filter = { orderedOnFrom: '2026-01-01', orderedOnTo: '2026-02-01' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
  });

  it('round trips requestedDate window', () => {
    const filter = { requestedDateFrom: '2026-01-01', requestedDateTo: '2026-02-01' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
  });

  it('round trips committedDate window', () => {
    const filter = { committedDateFrom: '2026-01-01', committedDateTo: '2026-02-01' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
  });

  it('round trips orderStatus selection', () => {
    const filter = { orderStatus: ['DRAFT'] as never };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).orderStatus).toBe('DRAFT');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseCircuitOrderFilter(serializeCircuitOrderFilter(filter))).toEqual(filter);
    expect(toCircuitOrderQuery(filter).sourceSystem).toBe('abc');
  });
});
