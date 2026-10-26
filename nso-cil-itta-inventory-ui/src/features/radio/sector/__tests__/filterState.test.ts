import { describe, expect, it } from 'vitest';
import { countSectorActiveFilters, parseSectorFilter, serializeSectorFilter, toSectorQuery } from '../filterState';

describe('sector filter state', () => {
  it('is empty by default', () => {
    expect(parseSectorFilter(new URLSearchParams())).toEqual({});
    expect(countSectorActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).externalRef).toBe('abc');
  });

  it('round trips baseStationId', () => {
    const filter = { baseStationId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
  });

  it('round trips sectorNumber range', () => {
    const filter = { sectorNumberMin: 1, sectorNumberMax: 9 };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).sectorNumberMax).toBe(9);
  });

  it('round trips pci range', () => {
    const filter = { pciMin: 1, pciMax: 9 };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).pciMax).toBe(9);
  });

  it('round trips frequencyMhz range', () => {
    const filter = { frequencyMhzMin: 1, frequencyMhzMax: 9 };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).frequencyMhzMax).toBe(9);
  });

  it('round trips bandwidthMhz range', () => {
    const filter = { bandwidthMhzMin: 1, bandwidthMhzMax: 9 };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).bandwidthMhzMax).toBe(9);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
    expect(toSectorQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseSectorFilter(serializeSectorFilter(filter))).toEqual(filter);
  });
});
