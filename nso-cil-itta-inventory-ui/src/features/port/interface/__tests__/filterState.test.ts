import { describe, expect, it } from 'vitest';
import { countInterfaceActiveFilters, parseInterfaceFilter, serializeInterfaceFilter, toInterfaceQuery } from '../filterState';

describe('interface filter state', () => {
  it('is empty by default', () => {
    expect(parseInterfaceFilter(new URLSearchParams())).toEqual({});
    expect(countInterfaceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips portId', () => {
    const filter = { portId: '00000000-0000-4000-8000-000000000011' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
  });

  it('round trips ifName', () => {
    const filter = { ifName: 'abc' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).ifName).toBe('abc');
  });

  it('round trips ifIndex range', () => {
    const filter = { ifIndexMin: 1, ifIndexMax: 9 };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).ifIndexMax).toBe(9);
  });

  it('round trips macAddress', () => {
    const filter = { macAddress: 'abc' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).macAddress).toBe('abc');
  });

  it('round trips mtu range', () => {
    const filter = { mtuMin: 1, mtuMax: 9 };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).mtuMax).toBe(9);
  });

  it('round trips operState selection', () => {
    const filter = { operState: ['UP'] as never };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).operState).toBe('UP');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
    expect(toInterfaceQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseInterfaceFilter(serializeInterfaceFilter(filter))).toEqual(filter);
  });
});
