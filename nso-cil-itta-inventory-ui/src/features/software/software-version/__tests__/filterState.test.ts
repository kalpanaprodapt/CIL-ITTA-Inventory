import { describe, expect, it } from 'vitest';
import { countSoftwareVersionActiveFilters, parseSoftwareVersionFilter, serializeSoftwareVersionFilter, toSoftwareVersionQuery } from '../filterState';

describe('softwareVersion filter state', () => {
  it('is empty by default', () => {
    expect(parseSoftwareVersionFilter(new URLSearchParams())).toEqual({});
    expect(countSoftwareVersionActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).externalRef).toBe('abc');
  });

  it('round trips imageId', () => {
    const filter = { imageId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
  });

  it('round trips versionString', () => {
    const filter = { versionString: 'abc' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).versionString).toBe('abc');
  });

  it('round trips releaseDate window', () => {
    const filter = { releaseDateFrom: '2026-01-01', releaseDateTo: '2026-02-01' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
  });

  it('round trips endOfSupport window', () => {
    const filter = { endOfSupportFrom: '2026-01-01', endOfSupportTo: '2026-02-01' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
  });

  it('round trips releaseTrain selection', () => {
    const filter = { releaseTrain: ['GA'] as never };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).releaseTrain).toBe('GA');
  });

  it('round trips cveCount range', () => {
    const filter = { cveCountMin: 1, cveCountMax: 9 };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).cveCountMax).toBe(9);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSoftwareVersionFilter(serializeSoftwareVersionFilter(filter))).toEqual(filter);
    expect(toSoftwareVersionQuery(filter).costCenter).toBe('abc');
  });
});
