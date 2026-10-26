import { describe, expect, it } from 'vitest';
import { countVlanPoolActiveFilters, parseVlanPoolFilter, serializeVlanPoolFilter, toVlanPoolQuery } from '../filterState';

describe('vlanPool filter state', () => {
  it('is empty by default', () => {
    expect(parseVlanPoolFilter(new URLSearchParams())).toEqual({});
    expect(countVlanPoolActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).externalRef).toBe('abc');
  });

  it('round trips poolName', () => {
    const filter = { poolName: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).poolName).toBe('abc');
  });

  it('round trips rangeStart range', () => {
    const filter = { rangeStartMin: 1, rangeStartMax: 9 };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).rangeStartMax).toBe(9);
  });

  it('round trips rangeEnd range', () => {
    const filter = { rangeEndMin: 1, rangeEndMax: 9 };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).rangeEndMax).toBe(9);
  });

  it('round trips allocated range', () => {
    const filter = { allocatedMin: 1, allocatedMax: 9 };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).allocatedMax).toBe(9);
  });

  it('round trips scope', () => {
    const filter = { scope: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).scope).toBe('abc');
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseVlanPoolFilter(serializeVlanPoolFilter(filter))).toEqual(filter);
    expect(toVlanPoolQuery(filter).region).toBe('abc');
  });
});
