import { describe, expect, it } from 'vitest';
import { countWarrantyActiveFilters, parseWarrantyFilter, serializeWarrantyFilter, toWarrantyQuery } from '../filterState';

describe('warranty filter state', () => {
  it('is empty by default', () => {
    expect(parseWarrantyFilter(new URLSearchParams())).toEqual({});
    expect(countWarrantyActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
    expect(toWarrantyQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
    expect(toWarrantyQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
    expect(toWarrantyQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
    expect(toWarrantyQuery(filter).externalRef).toBe('abc');
  });

  it('round trips assetId', () => {
    const filter = { assetId: '00000000-0000-4000-8000-000000000011' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
  });

  it('round trips startDate window', () => {
    const filter = { startDateFrom: '2026-01-01', startDateTo: '2026-02-01' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
  });

  it('round trips endDate window', () => {
    const filter = { endDateFrom: '2026-01-01', endDateTo: '2026-02-01' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
  });

  it('round trips warrantyType selection', () => {
    const filter = { warrantyType: ['STANDARD'] as never };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
    expect(toWarrantyQuery(filter).warrantyType).toBe('STANDARD');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
    expect(toWarrantyQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseWarrantyFilter(serializeWarrantyFilter(filter))).toEqual(filter);
    expect(toWarrantyQuery(filter).labels).toBe('abc');
  });
});
