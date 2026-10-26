import { describe, expect, it } from 'vitest';
import { countPatchActiveFilters, parsePatchFilter, serializePatchFilter, toPatchQuery } from '../filterState';

describe('patch filter state', () => {
  it('is empty by default', () => {
    expect(parsePatchFilter(new URLSearchParams())).toEqual({});
    expect(countPatchActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).externalRef).toBe('abc');
  });

  it('round trips versionId', () => {
    const filter = { versionId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
  });

  it('round trips patchId', () => {
    const filter = { patchId: 'abc' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).patchId).toBe('abc');
  });

  it('round trips severity selection', () => {
    const filter = { severity: ['LOW'] as never };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).severity).toBe('LOW');
  });

  it('round trips releasedOn window', () => {
    const filter = { releasedOnFrom: '2026-01-01', releasedOnTo: '2026-02-01' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
  });

  it('round trips rebootRequired flag', () => {
    const filter = { rebootRequired: false };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
    expect(toPatchQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parsePatchFilter(serializePatchFilter(filter))).toEqual(filter);
  });
});
