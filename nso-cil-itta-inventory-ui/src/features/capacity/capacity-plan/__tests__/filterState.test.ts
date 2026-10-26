import { describe, expect, it } from 'vitest';
import { countCapacityPlanActiveFilters, parseCapacityPlanFilter, serializeCapacityPlanFilter, toCapacityPlanQuery } from '../filterState';

describe('capacityPlan filter state', () => {
  it('is empty by default', () => {
    expect(parseCapacityPlanFilter(new URLSearchParams())).toEqual({});
    expect(countCapacityPlanActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).externalRef).toBe('abc');
  });

  it('round trips planName', () => {
    const filter = { planName: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).planName).toBe('abc');
  });

  it('round trips horizonMonths range', () => {
    const filter = { horizonMonthsMin: 1, horizonMonthsMax: 9 };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).horizonMonthsMax).toBe(9);
  });

  it('round trips targetUtilizationPct range', () => {
    const filter = { targetUtilizationPctMin: 1, targetUtilizationPctMax: 9 };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).targetUtilizationPctMax).toBe(9);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).owner).toBe('abc');
  });

  it('round trips approvedOn window', () => {
    const filter = { approvedOnFrom: '2026-01-01', approvedOnTo: '2026-02-01' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseCapacityPlanFilter(serializeCapacityPlanFilter(filter))).toEqual(filter);
    expect(toCapacityPlanQuery(filter).region).toBe('abc');
  });
});
