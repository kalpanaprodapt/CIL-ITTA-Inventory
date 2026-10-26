import { describe, expect, it } from 'vitest';
import { countHardwareModelActiveFilters, parseHardwareModelFilter, serializeHardwareModelFilter, toHardwareModelQuery } from '../filterState';

describe('hardwareModel filter state', () => {
  it('is empty by default', () => {
    expect(parseHardwareModelFilter(new URLSearchParams())).toEqual({});
    expect(countHardwareModelActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vendorId', () => {
    const filter = { vendorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
  });

  it('round trips modelNumber', () => {
    const filter = { modelNumber: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).modelNumber).toBe('abc');
  });

  it('round trips formFactor', () => {
    const filter = { formFactor: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).formFactor).toBe('abc');
  });

  it('round trips powerWatts range', () => {
    const filter = { powerWattsMin: 1, powerWattsMax: 9 };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).powerWattsMax).toBe(9);
  });

  it('round trips eolDate window', () => {
    const filter = { eolDateFrom: '2026-01-01', eolDateTo: '2026-02-01' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
  });

  it('round trips eosDate window', () => {
    const filter = { eosDateFrom: '2026-01-01', eosDateTo: '2026-02-01' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseHardwareModelFilter(serializeHardwareModelFilter(filter))).toEqual(filter);
    expect(toHardwareModelQuery(filter).owner).toBe('abc');
  });
});
