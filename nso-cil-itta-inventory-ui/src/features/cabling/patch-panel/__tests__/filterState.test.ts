import { describe, expect, it } from 'vitest';
import { countPatchPanelActiveFilters, parsePatchPanelFilter, serializePatchPanelFilter, toPatchPanelQuery } from '../filterState';

describe('patchPanel filter state', () => {
  it('is empty by default', () => {
    expect(parsePatchPanelFilter(new URLSearchParams())).toEqual({});
    expect(countPatchPanelActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).externalRef).toBe('abc');
  });

  it('round trips roomId', () => {
    const filter = { roomId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
  });

  it('round trips rackId', () => {
    const filter = { rackId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
  });

  it('round trips panelType selection', () => {
    const filter = { panelType: ['COPPER'] as never };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).panelType).toBe('COPPER');
  });

  it('round trips portCount range', () => {
    const filter = { portCountMin: 1, portCountMax: 9 };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).portCountMax).toBe(9);
  });

  it('round trips label', () => {
    const filter = { label: 'abc' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).label).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parsePatchPanelFilter(serializePatchPanelFilter(filter))).toEqual(filter);
    expect(toPatchPanelQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
