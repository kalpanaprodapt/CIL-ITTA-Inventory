import { describe, expect, it } from 'vitest';
import { countNetworkDeviceActiveFilters, parseNetworkDeviceFilter, serializeNetworkDeviceFilter, toNetworkDeviceQuery } from '../filterState';

describe('networkDevice filter state', () => {
  it('is empty by default', () => {
    expect(parseNetworkDeviceFilter(new URLSearchParams())).toEqual({});
    expect(countNetworkDeviceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips hostname', () => {
    const filter = { hostname: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).hostname).toBe('abc');
  });

  it('round trips deviceRole selection', () => {
    const filter = { deviceRole: ['CORE'] as never };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).deviceRole).toBe('CORE');
  });

  it('round trips mgmtIp', () => {
    const filter = { mgmtIp: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).mgmtIp).toBe('abc');
  });

  it('round trips serialNumber', () => {
    const filter = { serialNumber: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).serialNumber).toBe('abc');
  });

  it('round trips modelId', () => {
    const filter = { modelId: '00000000-0000-4000-8000-000000000011' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
  });

  it('round trips rackUnitId', () => {
    const filter = { rackUnitId: '00000000-0000-4000-8000-000000000011' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseNetworkDeviceFilter(serializeNetworkDeviceFilter(filter))).toEqual(filter);
    expect(toNetworkDeviceQuery(filter).region).toBe('abc');
  });
});
