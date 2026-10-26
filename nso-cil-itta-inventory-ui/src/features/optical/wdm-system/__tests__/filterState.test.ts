import { describe, expect, it } from 'vitest';
import { countWdmSystemActiveFilters, parseWdmSystemFilter, serializeWdmSystemFilter, toWdmSystemQuery } from '../filterState';

describe('wdmSystem filter state', () => {
  it('is empty by default', () => {
    expect(parseWdmSystemFilter(new URLSearchParams())).toEqual({});
    expect(countWdmSystemActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).externalRef).toBe('abc');
  });

  it('round trips systemName', () => {
    const filter = { systemName: 'abc' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).systemName).toBe('abc');
  });

  it('round trips channelCapacity range', () => {
    const filter = { channelCapacityMin: 1, channelCapacityMax: 9 };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).channelCapacityMax).toBe(9);
  });

  it('round trips band selection', () => {
    const filter = { band: ['C'] as never };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).band).toBe('C');
  });

  it('round trips spanCount range', () => {
    const filter = { spanCountMin: 1, spanCountMax: 9 };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).spanCountMax).toBe(9);
  });

  it('round trips vendorId', () => {
    const filter = { vendorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseWdmSystemFilter(serializeWdmSystemFilter(filter))).toEqual(filter);
    expect(toWdmSystemQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
