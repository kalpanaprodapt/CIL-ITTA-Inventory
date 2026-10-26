import { describe, expect, it } from 'vitest';
import { countRegionActiveFilters, parseRegionFilter, serializeRegionFilter, toRegionQuery } from '../filterState';

describe('region filter state', () => {
  it('is empty by default', () => {
    expect(parseRegionFilter(new URLSearchParams())).toEqual({});
    expect(countRegionActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).externalRef).toBe('abc');
  });

  it('round trips countryId', () => {
    const filter = { countryId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
  });

  it('round trips regionType selection', () => {
    const filter = { regionType: ['STATE'] as never };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).regionType).toBe('STATE');
  });

  it('round trips population range', () => {
    const filter = { populationMin: 1, populationMax: 9 };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).populationMax).toBe(9);
  });

  it('round trips latitude range', () => {
    const filter = { latitudeMin: 1, latitudeMax: 9 };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).latitudeMax).toBe(9);
  });

  it('round trips longitude range', () => {
    const filter = { longitudeMin: 1, longitudeMax: 9 };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).longitudeMax).toBe(9);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
    expect(toRegionQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseRegionFilter(serializeRegionFilter(filter))).toEqual(filter);
  });
});
