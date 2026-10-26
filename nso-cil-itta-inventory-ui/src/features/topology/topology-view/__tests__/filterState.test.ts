import { describe, expect, it } from 'vitest';
import { countTopologyViewActiveFilters, parseTopologyViewFilter, serializeTopologyViewFilter, toTopologyViewQuery } from '../filterState';

describe('topologyView filter state', () => {
  it('is empty by default', () => {
    expect(parseTopologyViewFilter(new URLSearchParams())).toEqual({});
    expect(countTopologyViewActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).externalRef).toBe('abc');
  });

  it('round trips viewName', () => {
    const filter = { viewName: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).viewName).toBe('abc');
  });

  it('round trips viewType selection', () => {
    const filter = { viewType: ['PHYSICAL'] as never };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).viewType).toBe('PHYSICAL');
  });

  it('round trips scopeFilter', () => {
    const filter = { scopeFilter: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).scopeFilter).toBe('abc');
  });

  it('round trips refreshedAt window', () => {
    const filter = { refreshedAtFrom: '2026-01-01', refreshedAtTo: '2026-02-01' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseTopologyViewFilter(serializeTopologyViewFilter(filter))).toEqual(filter);
    expect(toTopologyViewQuery(filter).region).toBe('abc');
  });
});
