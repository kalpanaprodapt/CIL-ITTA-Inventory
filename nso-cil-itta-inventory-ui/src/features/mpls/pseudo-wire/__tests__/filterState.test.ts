import { describe, expect, it } from 'vitest';
import { countPseudoWireActiveFilters, parsePseudoWireFilter, serializePseudoWireFilter, toPseudoWireQuery } from '../filterState';

describe('pseudoWire filter state', () => {
  it('is empty by default', () => {
    expect(parsePseudoWireFilter(new URLSearchParams())).toEqual({});
    expect(countPseudoWireActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vcId range', () => {
    const filter = { vcIdMin: 1, vcIdMax: 9 };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).vcIdMax).toBe(9);
  });

  it('round trips aEndId', () => {
    const filter = { aEndId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
  });

  it('round trips zEndId', () => {
    const filter = { zEndId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
  });

  it('round trips encapsulation', () => {
    const filter = { encapsulation: 'abc' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).encapsulation).toBe('abc');
  });

  it('round trips controlWord flag', () => {
    const filter = { controlWord: false };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parsePseudoWireFilter(serializePseudoWireFilter(filter))).toEqual(filter);
    expect(toPseudoWireQuery(filter).labels).toBe('abc');
  });
});
