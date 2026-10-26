import { describe, expect, it } from 'vitest';
import { countCableActiveFilters, parseCableFilter, serializeCableFilter, toCableQuery } from '../filterState';

describe('cable filter state', () => {
  it('is empty by default', () => {
    expect(parseCableFilter(new URLSearchParams())).toEqual({});
    expect(countCableActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).externalRef).toBe('abc');
  });

  it('round trips cableType selection', () => {
    const filter = { cableType: ['CAT6'] as never };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).cableType).toBe('CAT6');
  });

  it('round trips lengthM range', () => {
    const filter = { lengthMMin: 1, lengthMMax: 9 };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).lengthMMax).toBe(9);
  });

  it('round trips aEndPortId', () => {
    const filter = { aEndPortId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
  });

  it('round trips zEndPortId', () => {
    const filter = { zEndPortId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
  });

  it('round trips color', () => {
    const filter = { color: 'abc' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).color).toBe('abc');
  });

  it('round trips label', () => {
    const filter = { label: 'abc' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).label).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
    expect(toCableQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCableFilter(serializeCableFilter(filter))).toEqual(filter);
  });
});
