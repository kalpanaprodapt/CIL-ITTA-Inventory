import { describe, expect, it } from 'vitest';
import { countCustomerActiveFilters, parseCustomerFilter, serializeCustomerFilter, toCustomerQuery } from '../filterState';

describe('customer filter state', () => {
  it('is empty by default', () => {
    expect(parseCustomerFilter(new URLSearchParams())).toEqual({});
    expect(countCustomerActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).externalRef).toBe('abc');
  });

  it('round trips legalName', () => {
    const filter = { legalName: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).legalName).toBe('abc');
  });

  it('round trips segment selection', () => {
    const filter = { segment: ['ENTERPRISE'] as never };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).segment).toBe('ENTERPRISE');
  });

  it('round trips taxId', () => {
    const filter = { taxId: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).taxId).toBe('abc');
  });

  it('round trips industry', () => {
    const filter = { industry: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).industry).toBe('abc');
  });

  it('round trips accountManager', () => {
    const filter = { accountManager: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).accountManager).toBe('abc');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCustomerFilter(serializeCustomerFilter(filter))).toEqual(filter);
    expect(toCustomerQuery(filter).labels).toBe('abc');
  });
});
