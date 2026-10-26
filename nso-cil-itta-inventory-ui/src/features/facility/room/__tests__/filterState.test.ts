import { describe, expect, it } from 'vitest';
import { countRoomActiveFilters, parseRoomFilter, serializeRoomFilter, toRoomQuery } from '../filterState';

describe('room filter state', () => {
  it('is empty by default', () => {
    expect(parseRoomFilter(new URLSearchParams())).toEqual({});
    expect(countRoomActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).externalRef).toBe('abc');
  });

  it('round trips floorId', () => {
    const filter = { floorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
  });

  it('round trips roomType selection', () => {
    const filter = { roomType: ['MMR'] as never };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).roomType).toBe('MMR');
  });

  it('round trips areaSqm range', () => {
    const filter = { areaSqmMin: 1, areaSqmMax: 9 };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).areaSqmMax).toBe(9);
  });

  it('round trips coolingTons range', () => {
    const filter = { coolingTonsMin: 1, coolingTonsMax: 9 };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).coolingTonsMax).toBe(9);
  });

  it('round trips fireZone', () => {
    const filter = { fireZone: 'abc' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).fireZone).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseRoomFilter(serializeRoomFilter(filter))).toEqual(filter);
    expect(toRoomQuery(filter).sourceSystem).toBe('abc');
  });
});
