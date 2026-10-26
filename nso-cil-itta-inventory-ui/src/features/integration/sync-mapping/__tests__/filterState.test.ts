import { describe, expect, it } from 'vitest';
import { countSyncMappingActiveFilters, parseSyncMappingFilter, serializeSyncMappingFilter, toSyncMappingQuery } from '../filterState';

describe('syncMapping filter state', () => {
  it('is empty by default', () => {
    expect(parseSyncMappingFilter(new URLSearchParams())).toEqual({});
    expect(countSyncMappingActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).externalRef).toBe('abc');
  });

  it('round trips jobId', () => {
    const filter = { jobId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
  });

  it('round trips sourceField', () => {
    const filter = { sourceField: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).sourceField).toBe('abc');
  });

  it('round trips targetField', () => {
    const filter = { targetField: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).targetField).toBe('abc');
  });

  it('round trips transform', () => {
    const filter = { transform: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).transform).toBe('abc');
  });

  it('round trips required flag', () => {
    const filter = { required: false };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseSyncMappingFilter(serializeSyncMappingFilter(filter))).toEqual(filter);
    expect(toSyncMappingQuery(filter).labels).toBe('abc');
  });
});
