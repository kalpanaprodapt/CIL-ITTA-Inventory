import { describe, expect, it } from 'vitest';
import { countFloorActiveFilters, parseFloorFilter, serializeFloorFilter, toFloorQuery } from '../filterState';

describe('floor filter state', () => {
  it('is empty by default', () => {
    expect(parseFloorFilter(new URLSearchParams())).toEqual({});
    expect(countFloorActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).externalRef).toBe('abc');
  });

  it('round trips buildingId', () => {
    const filter = { buildingId: '00000000-0000-4000-8000-000000000011' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
  });

  it('round trips level range', () => {
    const filter = { levelMin: 1, levelMax: 9 };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).levelMax).toBe(9);
  });

  it('round trips areaSqm range', () => {
    const filter = { areaSqmMin: 1, areaSqmMax: 9 };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).areaSqmMax).toBe(9);
  });

  it('round trips loadCapacityKg range', () => {
    const filter = { loadCapacityKgMin: 1, loadCapacityKgMax: 9 };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).loadCapacityKgMax).toBe(9);
  });

  it('round trips hasRaisedFloor flag', () => {
    const filter = { hasRaisedFloor: false };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseFloorFilter(serializeFloorFilter(filter))).toEqual(filter);
    expect(toFloorQuery(filter).labels).toBe('abc');
  });
});
