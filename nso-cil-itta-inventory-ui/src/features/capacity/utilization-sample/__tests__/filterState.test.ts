import { describe, expect, it } from 'vitest';
import { countUtilizationSampleActiveFilters, parseUtilizationSampleFilter, serializeUtilizationSampleFilter, toUtilizationSampleQuery } from '../filterState';

describe('utilizationSample filter state', () => {
  it('is empty by default', () => {
    expect(parseUtilizationSampleFilter(new URLSearchParams())).toEqual({});
    expect(countUtilizationSampleActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).externalRef).toBe('abc');
  });

  it('round trips targetRef', () => {
    const filter = { targetRef: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).targetRef).toBe('abc');
  });

  it('round trips metric', () => {
    const filter = { metric: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).metric).toBe('abc');
  });

  it('round trips sampledAt window', () => {
    const filter = { sampledAtFrom: '2026-01-01', sampledAtTo: '2026-02-01' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
  });

  it('round trips valuePct range', () => {
    const filter = { valuePctMin: 1, valuePctMax: 9 };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).valuePctMax).toBe(9);
  });

  it('round trips peak flag', () => {
    const filter = { peak: false };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseUtilizationSampleFilter(serializeUtilizationSampleFilter(filter))).toEqual(filter);
    expect(toUtilizationSampleQuery(filter).criticality).toBe('LOW');
  });
});
