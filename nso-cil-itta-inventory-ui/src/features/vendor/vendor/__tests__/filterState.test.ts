import { describe, expect, it } from 'vitest';
import { countVendorActiveFilters, parseVendorFilter, serializeVendorFilter, toVendorQuery } from '../filterState';

describe('vendor filter state', () => {
  it('is empty by default', () => {
    expect(parseVendorFilter(new URLSearchParams())).toEqual({});
    expect(countVendorActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).externalRef).toBe('abc');
  });

  it('round trips legalName', () => {
    const filter = { legalName: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).legalName).toBe('abc');
  });

  it('round trips supportEmail', () => {
    const filter = { supportEmail: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).supportEmail).toBe('abc');
  });

  it('round trips supportPhone', () => {
    const filter = { supportPhone: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).supportPhone).toBe('abc');
  });

  it('round trips website', () => {
    const filter = { website: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).website).toBe('abc');
  });

  it('round trips tier selection', () => {
    const filter = { tier: ['STRATEGIC'] as never };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).tier).toBe('STRATEGIC');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseVendorFilter(serializeVendorFilter(filter))).toEqual(filter);
    expect(toVendorQuery(filter).sourceSystem).toBe('abc');
  });
});
