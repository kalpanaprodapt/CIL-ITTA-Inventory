import { describe, expect, it } from 'vitest';
import { countMplsLspActiveFilters, parseMplsLspFilter, serializeMplsLspFilter, toMplsLspQuery } from '../filterState';

describe('mplsLsp filter state', () => {
  it('is empty by default', () => {
    expect(parseMplsLspFilter(new URLSearchParams())).toEqual({});
    expect(countMplsLspActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).externalRef).toBe('abc');
  });

  it('round trips lspName', () => {
    const filter = { lspName: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).lspName).toBe('abc');
  });

  it('round trips headEndId', () => {
    const filter = { headEndId: '00000000-0000-4000-8000-000000000011' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
  });

  it('round trips tailEndId', () => {
    const filter = { tailEndId: '00000000-0000-4000-8000-000000000011' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
  });

  it('round trips bandwidthMbps range', () => {
    const filter = { bandwidthMbpsMin: 1, bandwidthMbpsMax: 9 };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).bandwidthMbpsMax).toBe(9);
  });

  it('round trips setupPriority range', () => {
    const filter = { setupPriorityMin: 1, setupPriorityMax: 9 };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).setupPriorityMax).toBe(9);
  });

  it('round trips pathType selection', () => {
    const filter = { pathType: ['PRIMARY'] as never };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).pathType).toBe('PRIMARY');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseMplsLspFilter(serializeMplsLspFilter(filter))).toEqual(filter);
    expect(toMplsLspQuery(filter).costCenter).toBe('abc');
  });
});
