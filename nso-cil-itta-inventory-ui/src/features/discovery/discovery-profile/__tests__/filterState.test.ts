import { describe, expect, it } from 'vitest';
import { countDiscoveryProfileActiveFilters, parseDiscoveryProfileFilter, serializeDiscoveryProfileFilter, toDiscoveryProfileQuery } from '../filterState';

describe('discoveryProfile filter state', () => {
  it('is empty by default', () => {
    expect(parseDiscoveryProfileFilter(new URLSearchParams())).toEqual({});
    expect(countDiscoveryProfileActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).externalRef).toBe('abc');
  });

  it('round trips profileName', () => {
    const filter = { profileName: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).profileName).toBe('abc');
  });

  it('round trips protocol selection', () => {
    const filter = { protocol: ['SNMP'] as never };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).protocol).toBe('SNMP');
  });

  it('round trips credentialRef', () => {
    const filter = { credentialRef: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).credentialRef).toBe('abc');
  });

  it('round trips scopeCidr', () => {
    const filter = { scopeCidr: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).scopeCidr).toBe('abc');
  });

  it('round trips scheduleCron', () => {
    const filter = { scheduleCron: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).scheduleCron).toBe('abc');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseDiscoveryProfileFilter(serializeDiscoveryProfileFilter(filter))).toEqual(filter);
    expect(toDiscoveryProfileQuery(filter).labels).toBe('abc');
  });
});
