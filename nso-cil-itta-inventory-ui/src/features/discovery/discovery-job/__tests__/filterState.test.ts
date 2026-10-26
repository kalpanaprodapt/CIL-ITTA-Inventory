import { describe, expect, it } from 'vitest';
import { countDiscoveryJobActiveFilters, parseDiscoveryJobFilter, serializeDiscoveryJobFilter, toDiscoveryJobQuery } from '../filterState';

describe('discoveryJob filter state', () => {
  it('is empty by default', () => {
    expect(parseDiscoveryJobFilter(new URLSearchParams())).toEqual({});
    expect(countDiscoveryJobActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).externalRef).toBe('abc');
  });

  it('round trips profileId', () => {
    const filter = { profileId: '00000000-0000-4000-8000-000000000011' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
  });

  it('round trips startedAt window', () => {
    const filter = { startedAtFrom: '2026-01-01', startedAtTo: '2026-02-01' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
  });

  it('round trips finishedAt window', () => {
    const filter = { finishedAtFrom: '2026-01-01', finishedAtTo: '2026-02-01' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
  });

  it('round trips jobState selection', () => {
    const filter = { jobState: ['QUEUED'] as never };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).jobState).toBe('QUEUED');
  });

  it('round trips assetsFound range', () => {
    const filter = { assetsFoundMin: 1, assetsFoundMax: 9 };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).assetsFoundMax).toBe(9);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
    expect(toDiscoveryJobQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseDiscoveryJobFilter(serializeDiscoveryJobFilter(filter))).toEqual(filter);
  });
});
