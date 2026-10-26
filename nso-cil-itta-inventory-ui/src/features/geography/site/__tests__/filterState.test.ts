import { describe, expect, it } from 'vitest';
import { countSiteActiveFilters, parseSiteFilter, serializeSiteFilter, toSiteQuery } from '../filterState';

describe('site filter state', () => {
  it('is empty by default', () => {
    expect(parseSiteFilter(new URLSearchParams())).toEqual({});
    expect(countSiteActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).externalRef).toBe('abc');
  });

  it('round trips cityId', () => {
    const filter = { cityId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
  });

  it('round trips siteType selection', () => {
    const filter = { siteType: ['CO'] as never };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).siteType).toBe('CO');
  });

  it('round trips address', () => {
    const filter = { address: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).address).toBe('abc');
  });

  it('round trips latitude range', () => {
    const filter = { latitudeMin: 1, latitudeMax: 9 };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).latitudeMax).toBe(9);
  });

  it('round trips longitude range', () => {
    const filter = { longitudeMin: 1, longitudeMax: 9 };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).longitudeMax).toBe(9);
  });

  it('round trips powerKw range', () => {
    const filter = { powerKwMin: 1, powerKwMax: 9 };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).powerKwMax).toBe(9);
  });

  it('round trips accessHours', () => {
    const filter = { accessHours: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).accessHours).toBe('abc');
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseSiteFilter(serializeSiteFilter(filter))).toEqual(filter);
    expect(toSiteQuery(filter).region).toBe('abc');
  });
});
