import { describe, expect, it } from 'vitest';
import { countTopologyNodeActiveFilters, parseTopologyNodeFilter, serializeTopologyNodeFilter, toTopologyNodeQuery } from '../filterState';

describe('topologyNode filter state', () => {
  it('is empty by default', () => {
    expect(parseTopologyNodeFilter(new URLSearchParams())).toEqual({});
    expect(countTopologyNodeActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).externalRef).toBe('abc');
  });

  it('round trips viewId', () => {
    const filter = { viewId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
  });

  it('round trips entityRef', () => {
    const filter = { entityRef: 'abc' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).entityRef).toBe('abc');
  });

  it('round trips nodeType', () => {
    const filter = { nodeType: 'abc' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).nodeType).toBe('abc');
  });

  it('round trips xPos range', () => {
    const filter = { xPosMin: 1, xPosMax: 9 };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).xPosMax).toBe(9);
  });

  it('round trips yPos range', () => {
    const filter = { yPosMin: 1, yPosMax: 9 };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).yPosMax).toBe(9);
  });

  it('round trips layer range', () => {
    const filter = { layerMin: 1, layerMax: 9 };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).layerMax).toBe(9);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
    expect(toTopologyNodeQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseTopologyNodeFilter(serializeTopologyNodeFilter(filter))).toEqual(filter);
  });
});
