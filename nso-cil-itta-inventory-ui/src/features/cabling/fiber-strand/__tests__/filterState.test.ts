import { describe, expect, it } from 'vitest';
import { countFiberStrandActiveFilters, parseFiberStrandFilter, serializeFiberStrandFilter, toFiberStrandQuery } from '../filterState';

describe('fiberStrand filter state', () => {
  it('is empty by default', () => {
    expect(parseFiberStrandFilter(new URLSearchParams())).toEqual({});
    expect(countFiberStrandActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).externalRef).toBe('abc');
  });

  it('round trips cableId', () => {
    const filter = { cableId: '00000000-0000-4000-8000-000000000011' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
  });

  it('round trips strandNumber range', () => {
    const filter = { strandNumberMin: 1, strandNumberMax: 9 };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).strandNumberMax).toBe(9);
  });

  it('round trips bufferTube', () => {
    const filter = { bufferTube: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).bufferTube).toBe('abc');
  });

  it('round trips tubeColor', () => {
    const filter = { tubeColor: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).tubeColor).toBe('abc');
  });

  it('round trips attenuationDb range', () => {
    const filter = { attenuationDbMin: 1, attenuationDbMax: 9 };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).attenuationDbMax).toBe(9);
  });

  it('round trips inUse flag', () => {
    const filter = { inUse: false };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseFiberStrandFilter(serializeFiberStrandFilter(filter))).toEqual(filter);
    expect(toFiberStrandQuery(filter).costCenter).toBe('abc');
  });
});
