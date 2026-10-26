import { describe, expect, it } from 'vitest';
import { countRackUnitActiveFilters, parseRackUnitFilter, serializeRackUnitFilter, toRackUnitQuery } from '../filterState';

describe('rackUnit filter state', () => {
  it('is empty by default', () => {
    expect(parseRackUnitFilter(new URLSearchParams())).toEqual({});
    expect(countRackUnitActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).externalRef).toBe('abc');
  });

  it('round trips rackId', () => {
    const filter = { rackId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
  });

  it('round trips unitNumber range', () => {
    const filter = { unitNumberMin: 1, unitNumberMax: 9 };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).unitNumberMax).toBe(9);
  });

  it('round trips occupied flag', () => {
    const filter = { occupied: false };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
  });

  it('round trips mountedDeviceId', () => {
    const filter = { mountedDeviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
  });

  it('round trips face selection', () => {
    const filter = { face: ['FRONT'] as never };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).face).toBe('FRONT');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseRackUnitFilter(serializeRackUnitFilter(filter))).toEqual(filter);
    expect(toRackUnitQuery(filter).costCenter).toBe('abc');
  });
});
