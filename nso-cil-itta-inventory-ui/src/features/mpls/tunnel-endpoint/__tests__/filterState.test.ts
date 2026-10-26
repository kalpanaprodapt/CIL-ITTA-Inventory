import { describe, expect, it } from 'vitest';
import { countTunnelEndpointActiveFilters, parseTunnelEndpointFilter, serializeTunnelEndpointFilter, toTunnelEndpointQuery } from '../filterState';

describe('tunnelEndpoint filter state', () => {
  it('is empty by default', () => {
    expect(parseTunnelEndpointFilter(new URLSearchParams())).toEqual({});
    expect(countTunnelEndpointActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).externalRef).toBe('abc');
  });

  it('round trips deviceId', () => {
    const filter = { deviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips tunnelName', () => {
    const filter = { tunnelName: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).tunnelName).toBe('abc');
  });

  it('round trips tunnelType selection', () => {
    const filter = { tunnelType: ['GRE'] as never };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).tunnelType).toBe('GRE');
  });

  it('round trips sourceIp', () => {
    const filter = { sourceIp: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).sourceIp).toBe('abc');
  });

  it('round trips destinationIp', () => {
    const filter = { destinationIp: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).destinationIp).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseTunnelEndpointFilter(serializeTunnelEndpointFilter(filter))).toEqual(filter);
    expect(toTunnelEndpointQuery(filter).sourceSystem).toBe('abc');
  });
});
