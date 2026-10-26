import { describe, expect, it } from 'vitest';
import { countLicensePoolActiveFilters, parseLicensePoolFilter, serializeLicensePoolFilter, toLicensePoolQuery } from '../filterState';

describe('licensePool filter state', () => {
  it('is empty by default', () => {
    expect(parseLicensePoolFilter(new URLSearchParams())).toEqual({});
    expect(countLicensePoolActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vendorId', () => {
    const filter = { vendorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
  });

  it('round trips poolName', () => {
    const filter = { poolName: 'abc' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).poolName).toBe('abc');
  });

  it('round trips totalSeats range', () => {
    const filter = { totalSeatsMin: 1, totalSeatsMax: 9 };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).totalSeatsMax).toBe(9);
  });

  it('round trips usedSeats range', () => {
    const filter = { usedSeatsMin: 1, usedSeatsMax: 9 };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).usedSeatsMax).toBe(9);
  });

  it('round trips renewalDate window', () => {
    const filter = { renewalDateFrom: '2026-01-01', renewalDateTo: '2026-02-01' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseLicensePoolFilter(serializeLicensePoolFilter(filter))).toEqual(filter);
    expect(toLicensePoolQuery(filter).region).toBe('abc');
  });
});
