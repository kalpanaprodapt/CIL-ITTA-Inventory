import { describe, expect, it } from 'vitest';
import { countOspfAreaActiveFilters, parseOspfAreaFilter, serializeOspfAreaFilter, toOspfAreaQuery } from '../filterState';

describe('ospfArea filter state', () => {
  it('is empty by default', () => {
    expect(parseOspfAreaFilter(new URLSearchParams())).toEqual({});
    expect(countOspfAreaActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).externalRef).toBe('abc');
  });

  it('round trips areaId', () => {
    const filter = { areaId: 'abc' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).areaId).toBe('abc');
  });

  it('round trips areaType selection', () => {
    const filter = { areaType: ['BACKBONE'] as never };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).areaType).toBe('BACKBONE');
  });

  it('round trips deviceCount range', () => {
    const filter = { deviceCountMin: 1, deviceCountMax: 9 };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).deviceCountMax).toBe(9);
  });

  it('round trips authType', () => {
    const filter = { authType: 'abc' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).authType).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
    expect(toOspfAreaQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseOspfAreaFilter(serializeOspfAreaFilter(filter))).toEqual(filter);
  });
});
