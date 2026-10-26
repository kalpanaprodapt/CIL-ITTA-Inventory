import { describe, expect, it } from 'vitest';
import { countAssetTagActiveFilters, parseAssetTagFilter, serializeAssetTagFilter, toAssetTagQuery } from '../filterState';

describe('assetTag filter state', () => {
  it('is empty by default', () => {
    expect(parseAssetTagFilter(new URLSearchParams())).toEqual({});
    expect(countAssetTagActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).externalRef).toBe('abc');
  });

  it('round trips assetId', () => {
    const filter = { assetId: '00000000-0000-4000-8000-000000000011' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
  });

  it('round trips tagValue', () => {
    const filter = { tagValue: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).tagValue).toBe('abc');
  });

  it('round trips tagType selection', () => {
    const filter = { tagType: ['BARCODE'] as never };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).tagType).toBe('BARCODE');
  });

  it('round trips printedOn window', () => {
    const filter = { printedOnFrom: '2026-01-01', printedOnTo: '2026-02-01' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseAssetTagFilter(serializeAssetTagFilter(filter))).toEqual(filter);
    expect(toAssetTagQuery(filter).costCenter).toBe('abc');
  });
});
