import { describe, expect, it } from 'vitest';
import { countCityActiveFilters, parseCityFilter, serializeCityFilter, toCityQuery } from '../filterState';

describe('city filter state', () => {
  it('is empty by default', () => {
    expect(parseCityFilter(new URLSearchParams())).toEqual({});
    expect(countCityActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).externalRef).toBe('abc');
  });

  it('round trips regionId', () => {
    const filter = { regionId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
  });

  it('round trips postalPrefix', () => {
    const filter = { postalPrefix: 'abc' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).postalPrefix).toBe('abc');
  });

  it('round trips population range', () => {
    const filter = { populationMin: 1, populationMax: 9 };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).populationMax).toBe(9);
  });

  it('round trips latitude range', () => {
    const filter = { latitudeMin: 1, latitudeMax: 9 };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).latitudeMax).toBe(9);
  });

  it('round trips longitude range', () => {
    const filter = { longitudeMin: 1, longitudeMax: 9 };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).longitudeMax).toBe(9);
  });

  it('round trips metroArea', () => {
    const filter = { metroArea: 'abc' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).metroArea).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseCityFilter(serializeCityFilter(filter))).toEqual(filter);
    expect(toCityQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
