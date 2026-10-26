import { describe, expect, it } from 'vitest';
import { countBaseStationActiveFilters, parseBaseStationFilter, serializeBaseStationFilter, toBaseStationQuery } from '../filterState';

describe('baseStation filter state', () => {
  it('is empty by default', () => {
    expect(parseBaseStationFilter(new URLSearchParams())).toEqual({});
    expect(countBaseStationActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).externalRef).toBe('abc');
  });

  it('round trips siteId', () => {
    const filter = { siteId: '00000000-0000-4000-8000-000000000011' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
  });

  it('round trips enbId range', () => {
    const filter = { enbIdMin: 1, enbIdMax: 9 };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).enbIdMax).toBe(9);
  });

  it('round trips technology selection', () => {
    const filter = { technology: ['LTE'] as never };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).technology).toBe('LTE');
  });

  it('round trips sectorCount range', () => {
    const filter = { sectorCountMin: 1, sectorCountMax: 9 };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).sectorCountMax).toBe(9);
  });

  it('round trips cellCount range', () => {
    const filter = { cellCountMin: 1, cellCountMax: 9 };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).cellCountMax).toBe(9);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseBaseStationFilter(serializeBaseStationFilter(filter))).toEqual(filter);
    expect(toBaseStationQuery(filter).owner).toBe('abc');
  });
});
