import { describe, expect, it } from 'vitest';
import { countReservationActiveFilters, parseReservationFilter, serializeReservationFilter, toReservationQuery } from '../filterState';

describe('reservation filter state', () => {
  it('is empty by default', () => {
    expect(parseReservationFilter(new URLSearchParams())).toEqual({});
    expect(countReservationActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).externalRef).toBe('abc');
  });

  it('round trips resourceRef', () => {
    const filter = { resourceRef: 'abc' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).resourceRef).toBe('abc');
  });

  it('round trips reservedFor', () => {
    const filter = { reservedFor: 'abc' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).reservedFor).toBe('abc');
  });

  it('round trips quantity range', () => {
    const filter = { quantityMin: 1, quantityMax: 9 };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).quantityMax).toBe(9);
  });

  it('round trips startDate window', () => {
    const filter = { startDateFrom: '2026-01-01', startDateTo: '2026-02-01' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
  });

  it('round trips endDate window', () => {
    const filter = { endDateFrom: '2026-01-01', endDateTo: '2026-02-01' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseReservationFilter(serializeReservationFilter(filter))).toEqual(filter);
    expect(toReservationQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
