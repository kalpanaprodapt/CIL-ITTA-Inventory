import { describe, expect, it } from 'vitest';
import { countLicenseKeyActiveFilters, parseLicenseKeyFilter, serializeLicenseKeyFilter, toLicenseKeyQuery } from '../filterState';

describe('licenseKey filter state', () => {
  it('is empty by default', () => {
    expect(parseLicenseKeyFilter(new URLSearchParams())).toEqual({});
    expect(countLicenseKeyActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).externalRef).toBe('abc');
  });

  it('round trips poolId', () => {
    const filter = { poolId: '00000000-0000-4000-8000-000000000011' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
  });

  it('round trips keyValue', () => {
    const filter = { keyValue: 'abc' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).keyValue).toBe('abc');
  });

  it('round trips feature', () => {
    const filter = { feature: 'abc' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).feature).toBe('abc');
  });

  it('round trips expiresOn window', () => {
    const filter = { expiresOnFrom: '2026-01-01', expiresOnTo: '2026-02-01' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
  });

  it('round trips assignedDeviceId', () => {
    const filter = { assignedDeviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseLicenseKeyFilter(serializeLicenseKeyFilter(filter))).toEqual(filter);
    expect(toLicenseKeyQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
