import { describe, expect, it } from 'vitest';
import { countTopologyEdgeActiveFilters, parseTopologyEdgeFilter, serializeTopologyEdgeFilter, toTopologyEdgeQuery } from '../filterState';

describe('topologyEdge filter state', () => {
  it('is empty by default', () => {
    expect(parseTopologyEdgeFilter(new URLSearchParams())).toEqual({});
    expect(countTopologyEdgeActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
    expect(toTopologyEdgeQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
    expect(toTopologyEdgeQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
    expect(toTopologyEdgeQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
    expect(toTopologyEdgeQuery(filter).externalRef).toBe('abc');
  });

  it('round trips viewId', () => {
    const filter = { viewId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
  });

  it('round trips sourceNodeId', () => {
    const filter = { sourceNodeId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
  });

  it('round trips targetNodeId', () => {
    const filter = { targetNodeId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
  });

  it('round trips weight range', () => {
    const filter = { weightMin: 1, weightMax: 9 };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
    expect(toTopologyEdgeQuery(filter).weightMax).toBe(9);
  });

  it('round trips directed flag', () => {
    const filter = { directed: false };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
    expect(toTopologyEdgeQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseTopologyEdgeFilter(serializeTopologyEdgeFilter(filter))).toEqual(filter);
    expect(toTopologyEdgeQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
