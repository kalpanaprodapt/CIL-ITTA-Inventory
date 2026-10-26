import { describe, expect, it } from 'vitest';
import { countAntennaActiveFilters, parseAntennaFilter, serializeAntennaFilter, toAntennaQuery } from '../filterState';

describe('antenna filter state', () => {
  it('is empty by default', () => {
    expect(parseAntennaFilter(new URLSearchParams())).toEqual({});
    expect(countAntennaActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).externalRef).toBe('abc');
  });

  it('round trips sectorId', () => {
    const filter = { sectorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
  });

  it('round trips model', () => {
    const filter = { model: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).model).toBe('abc');
  });

  it('round trips heightM range', () => {
    const filter = { heightMMin: 1, heightMMax: 9 };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).heightMMax).toBe(9);
  });

  it('round trips azimuthDeg range', () => {
    const filter = { azimuthDegMin: 1, azimuthDegMax: 9 };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).azimuthDegMax).toBe(9);
  });

  it('round trips tiltDeg range', () => {
    const filter = { tiltDegMin: 1, tiltDegMax: 9 };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).tiltDegMax).toBe(9);
  });

  it('round trips gainDbi range', () => {
    const filter = { gainDbiMin: 1, gainDbiMax: 9 };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).gainDbiMax).toBe(9);
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseAntennaFilter(serializeAntennaFilter(filter))).toEqual(filter);
    expect(toAntennaQuery(filter).costCenter).toBe('abc');
  });
});
