import { describe, expect, it } from 'vitest';
import { countVpnServiceActiveFilters, parseVpnServiceFilter, serializeVpnServiceFilter, toVpnServiceQuery } from '../filterState';

describe('vpnService filter state', () => {
  it('is empty by default', () => {
    expect(parseVpnServiceFilter(new URLSearchParams())).toEqual({});
    expect(countVpnServiceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vpnName', () => {
    const filter = { vpnName: 'abc' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).vpnName).toBe('abc');
  });

  it('round trips vpnType selection', () => {
    const filter = { vpnType: ['L3VPN'] as never };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).vpnType).toBe('L3VPN');
  });

  it('round trips vrfId', () => {
    const filter = { vrfId: '00000000-0000-4000-8000-000000000011' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
  });

  it('round trips customerId', () => {
    const filter = { customerId: '00000000-0000-4000-8000-000000000011' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
  });

  it('round trips topology selection', () => {
    const filter = { topology: ['MESH'] as never };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).topology).toBe('MESH');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
    expect(toVpnServiceQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseVpnServiceFilter(serializeVpnServiceFilter(filter))).toEqual(filter);
  });
});
