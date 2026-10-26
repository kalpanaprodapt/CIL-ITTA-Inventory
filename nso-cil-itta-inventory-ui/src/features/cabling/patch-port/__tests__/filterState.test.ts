import { describe, expect, it } from 'vitest';
import { countPatchPortActiveFilters, parsePatchPortFilter, serializePatchPortFilter, toPatchPortQuery } from '../filterState';

describe('patchPort filter state', () => {
  it('is empty by default', () => {
    expect(parsePatchPortFilter(new URLSearchParams())).toEqual({});
    expect(countPatchPortActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).externalRef).toBe('abc');
  });

  it('round trips panelId', () => {
    const filter = { panelId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
  });

  it('round trips portNumber range', () => {
    const filter = { portNumberMin: 1, portNumberMax: 9 };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).portNumberMax).toBe(9);
  });

  it('round trips connectedCableId', () => {
    const filter = { connectedCableId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
  });

  it('round trips polarity', () => {
    const filter = { polarity: 'abc' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).polarity).toBe('abc');
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parsePatchPortFilter(serializePatchPortFilter(filter))).toEqual(filter);
    expect(toPatchPortQuery(filter).region).toBe('abc');
  });
});
