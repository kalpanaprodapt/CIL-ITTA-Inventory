import { describe, expect, it } from 'vitest';
import { countRadioUnitActiveFilters, parseRadioUnitFilter, serializeRadioUnitFilter, toRadioUnitQuery } from '../filterState';

describe('radioUnit filter state', () => {
  it('is empty by default', () => {
    expect(parseRadioUnitFilter(new URLSearchParams())).toEqual({});
    expect(countRadioUnitActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).externalRef).toBe('abc');
  });

  it('round trips siteId', () => {
    const filter = { siteId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
  });

  it('round trips unitType selection', () => {
    const filter = { unitType: ['RRU'] as never };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).unitType).toBe('RRU');
  });

  it('round trips bandMhz range', () => {
    const filter = { bandMhzMin: 1, bandMhzMax: 9 };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).bandMhzMax).toBe(9);
  });

  it('round trips txPowerDbm range', () => {
    const filter = { txPowerDbmMin: 1, txPowerDbmMax: 9 };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).txPowerDbmMax).toBe(9);
  });

  it('round trips serialNumber', () => {
    const filter = { serialNumber: 'abc' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).serialNumber).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseRadioUnitFilter(serializeRadioUnitFilter(filter))).toEqual(filter);
    expect(toRadioUnitQuery(filter).sourceSystem).toBe('abc');
  });
});
