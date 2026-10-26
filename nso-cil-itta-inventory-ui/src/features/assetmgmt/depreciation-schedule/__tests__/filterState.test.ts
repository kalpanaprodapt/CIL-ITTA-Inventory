import { describe, expect, it } from 'vitest';
import { countDepreciationScheduleActiveFilters, parseDepreciationScheduleFilter, serializeDepreciationScheduleFilter, toDepreciationScheduleQuery } from '../filterState';

describe('depreciationSchedule filter state', () => {
  it('is empty by default', () => {
    expect(parseDepreciationScheduleFilter(new URLSearchParams())).toEqual({});
    expect(countDepreciationScheduleActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).externalRef).toBe('abc');
  });

  it('round trips assetId', () => {
    const filter = { assetId: '00000000-0000-4000-8000-000000000011' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
  });

  it('round trips method selection', () => {
    const filter = { method: ['STRAIGHT_LINE'] as never };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).method).toBe('STRAIGHT_LINE');
  });

  it('round trips usefulLifeMonths range', () => {
    const filter = { usefulLifeMonthsMin: 1, usefulLifeMonthsMax: 9 };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).usefulLifeMonthsMax).toBe(9);
  });

  it('round trips salvageValue range', () => {
    const filter = { salvageValueMin: 1, salvageValueMax: 9 };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).salvageValueMax).toBe(9);
  });

  it('round trips startDate window', () => {
    const filter = { startDateFrom: '2026-01-01', startDateTo: '2026-02-01' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseDepreciationScheduleFilter(serializeDepreciationScheduleFilter(filter))).toEqual(filter);
    expect(toDepreciationScheduleQuery(filter).sourceSystem).toBe('abc');
  });
});
