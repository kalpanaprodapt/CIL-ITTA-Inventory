import { describe, expect, it } from 'vitest';
import { countIpAddressActiveFilters, parseIpAddressFilter, serializeIpAddressFilter, toIpAddressQuery } from '../filterState';

describe('ipAddress filter state', () => {
  it('is empty by default', () => {
    expect(parseIpAddressFilter(new URLSearchParams())).toEqual({});
    expect(countIpAddressActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).externalRef).toBe('abc');
  });

  it('round trips subnetId', () => {
    const filter = { subnetId: '00000000-0000-4000-8000-000000000011' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
  });

  it('round trips address', () => {
    const filter = { address: 'abc' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).address).toBe('abc');
  });

  it('round trips assignedToId', () => {
    const filter = { assignedToId: '00000000-0000-4000-8000-000000000011' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
  });

  it('round trips assignmentType selection', () => {
    const filter = { assignmentType: ['STATIC'] as never };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).assignmentType).toBe('STATIC');
  });

  it('round trips dnsName', () => {
    const filter = { dnsName: 'abc' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).dnsName).toBe('abc');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseIpAddressFilter(serializeIpAddressFilter(filter))).toEqual(filter);
    expect(toIpAddressQuery(filter).labels).toBe('abc');
  });
});
