import { describe, expect, it } from 'vitest';
import { countCircuitActiveFilters, parseCircuitFilter, serializeCircuitFilter, toCircuitQuery } from '../filterState';

describe('circuit filter state', () => {
  it('is empty by default', () => {
    expect(parseCircuitFilter(new URLSearchParams())).toEqual({});
    expect(countCircuitActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).externalRef).toBe('abc');
  });

  it('round trips circuitId', () => {
    const filter = { circuitId: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).circuitId).toBe('abc');
  });

  it('round trips circuitType selection', () => {
    const filter = { circuitType: ['ETHERNET'] as never };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).circuitType).toBe('ETHERNET');
  });

  it('round trips bandwidthMbps range', () => {
    const filter = { bandwidthMbpsMin: 1, bandwidthMbpsMax: 9 };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).bandwidthMbpsMax).toBe(9);
  });

  it('round trips providerId', () => {
    const filter = { providerId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
  });

  it('round trips aSiteId', () => {
    const filter = { aSiteId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
  });

  it('round trips zSiteId', () => {
    const filter = { zSiteId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseCircuitFilter(serializeCircuitFilter(filter))).toEqual(filter);
    expect(toCircuitQuery(filter).owner).toBe('abc');
  });
});
