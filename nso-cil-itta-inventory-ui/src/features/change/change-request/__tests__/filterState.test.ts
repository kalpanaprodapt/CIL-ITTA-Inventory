import { describe, expect, it } from 'vitest';
import { countChangeRequestActiveFilters, parseChangeRequestFilter, serializeChangeRequestFilter, toChangeRequestQuery } from '../filterState';

describe('changeRequest filter state', () => {
  it('is empty by default', () => {
    expect(parseChangeRequestFilter(new URLSearchParams())).toEqual({});
    expect(countChangeRequestActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).externalRef).toBe('abc');
  });

  it('round trips changeNumber', () => {
    const filter = { changeNumber: 'abc' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).changeNumber).toBe('abc');
  });

  it('round trips changeType selection', () => {
    const filter = { changeType: ['STANDARD'] as never };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).changeType).toBe('STANDARD');
  });

  it('round trips riskLevel selection', () => {
    const filter = { riskLevel: ['LOW'] as never };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).riskLevel).toBe('LOW');
  });

  it('round trips plannedStart window', () => {
    const filter = { plannedStartFrom: '2026-01-01', plannedStartTo: '2026-02-01' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
  });

  it('round trips plannedEnd window', () => {
    const filter = { plannedEndFrom: '2026-01-01', plannedEndTo: '2026-02-01' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseChangeRequestFilter(serializeChangeRequestFilter(filter))).toEqual(filter);
    expect(toChangeRequestQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
