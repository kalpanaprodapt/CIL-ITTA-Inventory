import { describe, expect, it } from 'vitest';
import { countChassisActiveFilters, parseChassisFilter, serializeChassisFilter, toChassisQuery } from '../filterState';

describe('chassis filter state', () => {
  it('is empty by default', () => {
    expect(parseChassisFilter(new URLSearchParams())).toEqual({});
    expect(countChassisActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).externalRef).toBe('abc');
  });

  it('round trips deviceId', () => {
    const filter = { deviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
  });

  it('round trips slotCount range', () => {
    const filter = { slotCountMin: 1, slotCountMax: 9 };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).slotCountMax).toBe(9);
  });

  it('round trips serialNumber', () => {
    const filter = { serialNumber: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).serialNumber).toBe('abc');
  });

  it('round trips backplaneGbps range', () => {
    const filter = { backplaneGbpsMin: 1, backplaneGbpsMax: 9 };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).backplaneGbpsMax).toBe(9);
  });

  it('round trips redundancyMode', () => {
    const filter = { redundancyMode: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).redundancyMode).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseChassisFilter(serializeChassisFilter(filter))).toEqual(filter);
    expect(toChassisQuery(filter).criticality).toBe('LOW');
  });
});
