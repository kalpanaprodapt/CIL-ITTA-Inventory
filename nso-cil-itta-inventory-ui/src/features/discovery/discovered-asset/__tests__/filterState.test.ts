import { describe, expect, it } from 'vitest';
import { countDiscoveredAssetActiveFilters, parseDiscoveredAssetFilter, serializeDiscoveredAssetFilter, toDiscoveredAssetQuery } from '../filterState';

describe('discoveredAsset filter state', () => {
  it('is empty by default', () => {
    expect(parseDiscoveredAssetFilter(new URLSearchParams())).toEqual({});
    expect(countDiscoveredAssetActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).externalRef).toBe('abc');
  });

  it('round trips jobId', () => {
    const filter = { jobId: '00000000-0000-4000-8000-000000000011' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
  });

  it('round trips assetType', () => {
    const filter = { assetType: 'abc' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).assetType).toBe('abc');
  });

  it('round trips fingerprint', () => {
    const filter = { fingerprint: 'abc' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).fingerprint).toBe('abc');
  });

  it('round trips matchedAssetId', () => {
    const filter = { matchedAssetId: '00000000-0000-4000-8000-000000000011' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseDiscoveredAssetFilter(serializeDiscoveredAssetFilter(filter))).toEqual(filter);
    expect(toDiscoveredAssetQuery(filter).sourceSystem).toBe('abc');
  });
});
