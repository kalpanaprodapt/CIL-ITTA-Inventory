import { describe, expect, it } from 'vitest';
import { countSpliceActiveFilters, parseSpliceFilter, serializeSpliceFilter, toSpliceQuery } from '../filterState';

describe('splice filter state', () => {
  it('is empty by default', () => {
    expect(parseSpliceFilter(new URLSearchParams())).toEqual({});
    expect(countSpliceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips enclosureId', () => {
    const filter = { enclosureId: 'abc' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).enclosureId).toBe('abc');
  });

  it('round trips aStrandId', () => {
    const filter = { aStrandId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
  });

  it('round trips zStrandId', () => {
    const filter = { zStrandId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
  });

  it('round trips lossDb range', () => {
    const filter = { lossDbMin: 1, lossDbMax: 9 };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).lossDbMax).toBe(9);
  });

  it('round trips splicedOn window', () => {
    const filter = { splicedOnFrom: '2026-01-01', splicedOnTo: '2026-02-01' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSpliceFilter(serializeSpliceFilter(filter))).toEqual(filter);
    expect(toSpliceQuery(filter).criticality).toBe('LOW');
  });
});
