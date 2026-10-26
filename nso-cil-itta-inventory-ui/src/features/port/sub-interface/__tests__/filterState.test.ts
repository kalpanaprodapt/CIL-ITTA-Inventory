import { describe, expect, it } from 'vitest';
import { countSubInterfaceActiveFilters, parseSubInterfaceFilter, serializeSubInterfaceFilter, toSubInterfaceQuery } from '../filterState';

describe('subInterface filter state', () => {
  it('is empty by default', () => {
    expect(parseSubInterfaceFilter(new URLSearchParams())).toEqual({});
    expect(countSubInterfaceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips interfaceId', () => {
    const filter = { interfaceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
  });

  it('round trips vlanTag range', () => {
    const filter = { vlanTagMin: 1, vlanTagMax: 9 };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).vlanTagMax).toBe(9);
  });

  it('round trips encapsulation', () => {
    const filter = { encapsulation: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).encapsulation).toBe('abc');
  });

  it('round trips ipv4Address', () => {
    const filter = { ipv4Address: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).ipv4Address).toBe('abc');
  });

  it('round trips ipv6Address', () => {
    const filter = { ipv6Address: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).ipv6Address).toBe('abc');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseSubInterfaceFilter(serializeSubInterfaceFilter(filter))).toEqual(filter);
    expect(toSubInterfaceQuery(filter).labels).toBe('abc');
  });
});
