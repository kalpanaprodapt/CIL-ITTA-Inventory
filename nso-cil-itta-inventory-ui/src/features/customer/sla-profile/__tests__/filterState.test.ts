import { describe, expect, it } from 'vitest';
import { countSlaProfileActiveFilters, parseSlaProfileFilter, serializeSlaProfileFilter, toSlaProfileQuery } from '../filterState';

describe('slaProfile filter state', () => {
  it('is empty by default', () => {
    expect(parseSlaProfileFilter(new URLSearchParams())).toEqual({});
    expect(countSlaProfileActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).externalRef).toBe('abc');
  });

  it('round trips profileName', () => {
    const filter = { profileName: 'abc' };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).profileName).toBe('abc');
  });

  it('round trips availabilityPct range', () => {
    const filter = { availabilityPctMin: 1, availabilityPctMax: 9 };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).availabilityPctMax).toBe(9);
  });

  it('round trips mttrMinutes range', () => {
    const filter = { mttrMinutesMin: 1, mttrMinutesMax: 9 };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).mttrMinutesMax).toBe(9);
  });

  it('round trips latencyMs range', () => {
    const filter = { latencyMsMin: 1, latencyMsMax: 9 };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).latencyMsMax).toBe(9);
  });

  it('round trips jitterMs range', () => {
    const filter = { jitterMsMin: 1, jitterMsMax: 9 };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).jitterMsMax).toBe(9);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
    expect(toSlaProfileQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseSlaProfileFilter(serializeSlaProfileFilter(filter))).toEqual(filter);
  });
});
