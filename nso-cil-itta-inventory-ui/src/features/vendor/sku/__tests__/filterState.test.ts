import { describe, expect, it } from 'vitest';
import { countSkuActiveFilters, parseSkuFilter, serializeSkuFilter, toSkuQuery } from '../filterState';

describe('sku filter state', () => {
  it('is empty by default', () => {
    expect(parseSkuFilter(new URLSearchParams())).toEqual({});
    expect(countSkuActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).externalRef).toBe('abc');
  });

  it('round trips modelId', () => {
    const filter = { modelId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
  });

  it('round trips skuCode', () => {
    const filter = { skuCode: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).skuCode).toBe('abc');
  });

  it('round trips listPrice range', () => {
    const filter = { listPriceMin: 1, listPriceMax: 9 };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).listPriceMax).toBe(9);
  });

  it('round trips currency', () => {
    const filter = { currency: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).currency).toBe('abc');
  });

  it('round trips leadTimeDays range', () => {
    const filter = { leadTimeDaysMin: 1, leadTimeDaysMax: 9 };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).leadTimeDaysMax).toBe(9);
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSkuFilter(serializeSkuFilter(filter))).toEqual(filter);
    expect(toSkuQuery(filter).costCenter).toBe('abc');
  });
});
