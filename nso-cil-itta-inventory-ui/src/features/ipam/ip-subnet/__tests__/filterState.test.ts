import { describe, expect, it } from 'vitest';
import { countIpSubnetActiveFilters, parseIpSubnetFilter, serializeIpSubnetFilter, toIpSubnetQuery } from '../filterState';

describe('ipSubnet filter state', () => {
  it('is empty by default', () => {
    expect(parseIpSubnetFilter(new URLSearchParams())).toEqual({});
    expect(countIpSubnetActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).externalRef).toBe('abc');
  });

  it('round trips cidr', () => {
    const filter = { cidr: 'abc' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).cidr).toBe('abc');
  });

  it('round trips ipVersion selection', () => {
    const filter = { ipVersion: ['V4'] as never };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).ipVersion).toBe('V4');
  });

  it('round trips vrfId', () => {
    const filter = { vrfId: '00000000-0000-4000-8000-000000000011' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
  });

  it('round trips gateway', () => {
    const filter = { gateway: 'abc' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).gateway).toBe('abc');
  });

  it('round trips vlanId', () => {
    const filter = { vlanId: '00000000-0000-4000-8000-000000000011' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
  });

  it('round trips utilizationPct range', () => {
    const filter = { utilizationPctMin: 1, utilizationPctMax: 9 };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).utilizationPctMax).toBe(9);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
    expect(toIpSubnetQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseIpSubnetFilter(serializeIpSubnetFilter(filter))).toEqual(filter);
  });
});
