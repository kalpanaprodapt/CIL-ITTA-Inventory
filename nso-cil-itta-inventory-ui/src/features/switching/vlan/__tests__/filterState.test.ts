import { describe, expect, it } from 'vitest';
import { countVlanActiveFilters, parseVlanFilter, serializeVlanFilter, toVlanQuery } from '../filterState';

describe('vlan filter state', () => {
  it('is empty by default', () => {
    expect(parseVlanFilter(new URLSearchParams())).toEqual({});
    expect(countVlanActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vlanId range', () => {
    const filter = { vlanIdMin: 1, vlanIdMax: 9 };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).vlanIdMax).toBe(9);
  });

  it('round trips vlanName', () => {
    const filter = { vlanName: 'abc' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).vlanName).toBe('abc');
  });

  it('round trips vlanType selection', () => {
    const filter = { vlanType: ['DATA'] as never };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).vlanType).toBe('DATA');
  });

  it('round trips poolId', () => {
    const filter = { poolId: '00000000-0000-4000-8000-000000000011' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
  });

  it('round trips stpMode', () => {
    const filter = { stpMode: 'abc' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).stpMode).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseVlanFilter(serializeVlanFilter(filter))).toEqual(filter);
    expect(toVlanQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
