import { describe, expect, it } from 'vitest';
import { countAssetActiveFilters, parseAssetFilter, serializeAssetFilter, toAssetQuery } from '../filterState';

describe('asset filter state', () => {
  it('is empty by default', () => {
    expect(parseAssetFilter(new URLSearchParams())).toEqual({});
    expect(countAssetActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).externalRef).toBe('abc');
  });

  it('round trips assetTag', () => {
    const filter = { assetTag: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).assetTag).toBe('abc');
  });

  it('round trips assetClass', () => {
    const filter = { assetClass: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).assetClass).toBe('abc');
  });

  it('round trips acquiredOn window', () => {
    const filter = { acquiredOnFrom: '2026-01-01', acquiredOnTo: '2026-02-01' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
  });

  it('round trips purchasePrice range', () => {
    const filter = { purchasePriceMin: 1, purchasePriceMax: 9 };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).purchasePriceMax).toBe(9);
  });

  it('round trips currentValue range', () => {
    const filter = { currentValueMin: 1, currentValueMax: 9 };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).currentValueMax).toBe(9);
  });

  it('round trips custodian', () => {
    const filter = { custodian: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).custodian).toBe('abc');
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseAssetFilter(serializeAssetFilter(filter))).toEqual(filter);
    expect(toAssetQuery(filter).owner).toBe('abc');
  });
});
