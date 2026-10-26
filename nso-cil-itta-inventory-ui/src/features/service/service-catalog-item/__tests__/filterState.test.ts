import { describe, expect, it } from 'vitest';
import { countServiceCatalogItemActiveFilters, parseServiceCatalogItemFilter, serializeServiceCatalogItemFilter, toServiceCatalogItemQuery } from '../filterState';

describe('serviceCatalogItem filter state', () => {
  it('is empty by default', () => {
    expect(parseServiceCatalogItemFilter(new URLSearchParams())).toEqual({});
    expect(countServiceCatalogItemActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).externalRef).toBe('abc');
  });

  it('round trips itemName', () => {
    const filter = { itemName: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).itemName).toBe('abc');
  });

  it('round trips family selection', () => {
    const filter = { family: ['CONNECTIVITY'] as never };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).family).toBe('CONNECTIVITY');
  });

  it('round trips basePrice range', () => {
    const filter = { basePriceMin: 1, basePriceMax: 9 };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).basePriceMax).toBe(9);
  });

  it('round trips billingUnit', () => {
    const filter = { billingUnit: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).billingUnit).toBe('abc');
  });

  it('round trips orderable flag', () => {
    const filter = { orderable: false };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseServiceCatalogItemFilter(serializeServiceCatalogItemFilter(filter))).toEqual(filter);
    expect(toServiceCatalogItemQuery(filter).region).toBe('abc');
  });
});
