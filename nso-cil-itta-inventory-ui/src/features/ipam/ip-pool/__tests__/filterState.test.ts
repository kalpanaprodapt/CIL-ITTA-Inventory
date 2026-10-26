import { describe, expect, it } from 'vitest';
import { countIpPoolActiveFilters, parseIpPoolFilter, serializeIpPoolFilter, toIpPoolQuery } from '../filterState';

describe('ipPool filter state', () => {
  it('is empty by default', () => {
    expect(parseIpPoolFilter(new URLSearchParams())).toEqual({});
    expect(countIpPoolActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).externalRef).toBe('abc');
  });

  it('round trips subnetId', () => {
    const filter = { subnetId: '00000000-0000-4000-8000-000000000011' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
  });

  it('round trips rangeStart', () => {
    const filter = { rangeStart: 'abc' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).rangeStart).toBe('abc');
  });

  it('round trips rangeEnd', () => {
    const filter = { rangeEnd: 'abc' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).rangeEnd).toBe('abc');
  });

  it('round trips poolType selection', () => {
    const filter = { poolType: ['DYNAMIC'] as never };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).poolType).toBe('DYNAMIC');
  });

  it('round trips leaseSeconds range', () => {
    const filter = { leaseSecondsMin: 1, leaseSecondsMax: 9 };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).leaseSecondsMax).toBe(9);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseIpPoolFilter(serializeIpPoolFilter(filter))).toEqual(filter);
    expect(toIpPoolQuery(filter).sourceSystem).toBe('abc');
  });
});
