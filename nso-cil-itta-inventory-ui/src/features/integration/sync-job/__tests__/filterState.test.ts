import { describe, expect, it } from 'vitest';
import { countSyncJobActiveFilters, parseSyncJobFilter, serializeSyncJobFilter, toSyncJobQuery } from '../filterState';

describe('syncJob filter state', () => {
  it('is empty by default', () => {
    expect(parseSyncJobFilter(new URLSearchParams())).toEqual({});
    expect(countSyncJobActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).externalRef).toBe('abc');
  });

  it('round trips endpointId', () => {
    const filter = { endpointId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
  });

  it('round trips direction selection', () => {
    const filter = { direction: ['INBOUND'] as never };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).direction).toBe('INBOUND');
  });

  it('round trips scheduleCron', () => {
    const filter = { scheduleCron: 'abc' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).scheduleCron).toBe('abc');
  });

  it('round trips lastRunAt window', () => {
    const filter = { lastRunAtFrom: '2026-01-01', lastRunAtTo: '2026-02-01' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
  });

  it('round trips lastStatus selection', () => {
    const filter = { lastStatus: ['OK'] as never };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).lastStatus).toBe('OK');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
    expect(toSyncJobQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseSyncJobFilter(serializeSyncJobFilter(filter))).toEqual(filter);
  });
});
