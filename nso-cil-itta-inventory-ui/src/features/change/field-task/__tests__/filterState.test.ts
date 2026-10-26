import { describe, expect, it } from 'vitest';
import { countFieldTaskActiveFilters, parseFieldTaskFilter, serializeFieldTaskFilter, toFieldTaskQuery } from '../filterState';

describe('fieldTask filter state', () => {
  it('is empty by default', () => {
    expect(parseFieldTaskFilter(new URLSearchParams())).toEqual({});
    expect(countFieldTaskActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).externalRef).toBe('abc');
  });

  it('round trips workOrderId', () => {
    const filter = { workOrderId: '00000000-0000-4000-8000-000000000011' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
  });

  it('round trips taskName', () => {
    const filter = { taskName: 'abc' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).taskName).toBe('abc');
  });

  it('round trips sequence range', () => {
    const filter = { sequenceMin: 1, sequenceMax: 9 };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).sequenceMax).toBe(9);
  });

  it('round trips durationMinutes range', () => {
    const filter = { durationMinutesMin: 1, durationMinutesMax: 9 };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).durationMinutesMax).toBe(9);
  });

  it('round trips completedOn window', () => {
    const filter = { completedOnFrom: '2026-01-01', completedOnTo: '2026-02-01' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseFieldTaskFilter(serializeFieldTaskFilter(filter))).toEqual(filter);
    expect(toFieldTaskQuery(filter).costCenter).toBe('abc');
  });
});
