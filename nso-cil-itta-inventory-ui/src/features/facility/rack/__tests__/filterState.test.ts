import { describe, expect, it } from 'vitest';
import { countRackActiveFilters, parseRackFilter, serializeRackFilter, toRackQuery } from '../filterState';

describe('rack filter state', () => {
  it('is empty by default', () => {
    expect(parseRackFilter(new URLSearchParams())).toEqual({});
    expect(countRackActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).externalRef).toBe('abc');
  });

  it('round trips roomId', () => {
    const filter = { roomId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
  });

  it('round trips rackUnits range', () => {
    const filter = { rackUnitsMin: 1, rackUnitsMax: 9 };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).rackUnitsMax).toBe(9);
  });

  it('round trips widthMm range', () => {
    const filter = { widthMmMin: 1, widthMmMax: 9 };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).widthMmMax).toBe(9);
  });

  it('round trips depthMm range', () => {
    const filter = { depthMmMin: 1, depthMmMax: 9 };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).depthMmMax).toBe(9);
  });

  it('round trips maxWeightKg range', () => {
    const filter = { maxWeightKgMin: 1, maxWeightKgMax: 9 };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).maxWeightKgMax).toBe(9);
  });

  it('round trips rowLabel', () => {
    const filter = { rowLabel: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).rowLabel).toBe('abc');
  });

  it('round trips position', () => {
    const filter = { position: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).position).toBe('abc');
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseRackFilter(serializeRackFilter(filter))).toEqual(filter);
    expect(toRackQuery(filter).owner).toBe('abc');
  });
});
