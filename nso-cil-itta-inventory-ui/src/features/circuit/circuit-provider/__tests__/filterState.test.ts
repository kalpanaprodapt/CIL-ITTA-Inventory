import { describe, expect, it } from 'vitest';
import { countCircuitProviderActiveFilters, parseCircuitProviderFilter, serializeCircuitProviderFilter, toCircuitProviderQuery } from '../filterState';

describe('circuitProvider filter state', () => {
  it('is empty by default', () => {
    expect(parseCircuitProviderFilter(new URLSearchParams())).toEqual({});
    expect(countCircuitProviderActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).externalRef).toBe('abc');
  });

  it('round trips providerName', () => {
    const filter = { providerName: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).providerName).toBe('abc');
  });

  it('round trips accountNumber', () => {
    const filter = { accountNumber: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).accountNumber).toBe('abc');
  });

  it('round trips noc', () => {
    const filter = { noc: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).noc).toBe('abc');
  });

  it('round trips escalationEmail', () => {
    const filter = { escalationEmail: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).escalationEmail).toBe('abc');
  });

  it('round trips providerType selection', () => {
    const filter = { providerType: ['TIER1'] as never };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).providerType).toBe('TIER1');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
    expect(toCircuitProviderQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCircuitProviderFilter(serializeCircuitProviderFilter(filter))).toEqual(filter);
  });
});
