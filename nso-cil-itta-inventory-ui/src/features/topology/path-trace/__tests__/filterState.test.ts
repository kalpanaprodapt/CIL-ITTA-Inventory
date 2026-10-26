import { describe, expect, it } from 'vitest';
import { countPathTraceActiveFilters, parsePathTraceFilter, serializePathTraceFilter, toPathTraceQuery } from '../filterState';

describe('pathTrace filter state', () => {
  it('is empty by default', () => {
    expect(parsePathTraceFilter(new URLSearchParams())).toEqual({});
    expect(countPathTraceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips sourceRef', () => {
    const filter = { sourceRef: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).sourceRef).toBe('abc');
  });

  it('round trips targetRef', () => {
    const filter = { targetRef: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).targetRef).toBe('abc');
  });

  it('round trips hopCount range', () => {
    const filter = { hopCountMin: 1, hopCountMax: 9 };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).hopCountMax).toBe(9);
  });

  it('round trips totalLatencyMs range', () => {
    const filter = { totalLatencyMsMin: 1, totalLatencyMsMax: 9 };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).totalLatencyMsMax).toBe(9);
  });

  it('round trips traceMethod', () => {
    const filter = { traceMethod: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).traceMethod).toBe('abc');
  });

  it('round trips tracedAt window', () => {
    const filter = { tracedAtFrom: '2026-01-01', tracedAtTo: '2026-02-01' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parsePathTraceFilter(serializePathTraceFilter(filter))).toEqual(filter);
    expect(toPathTraceQuery(filter).criticality).toBe('LOW');
  });
});
