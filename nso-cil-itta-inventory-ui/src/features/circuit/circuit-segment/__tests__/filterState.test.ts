import { describe, expect, it } from 'vitest';
import { countCircuitSegmentActiveFilters, parseCircuitSegmentFilter, serializeCircuitSegmentFilter, toCircuitSegmentQuery } from '../filterState';

describe('circuitSegment filter state', () => {
  it('is empty by default', () => {
    expect(parseCircuitSegmentFilter(new URLSearchParams())).toEqual({});
    expect(countCircuitSegmentActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).externalRef).toBe('abc');
  });

  it('round trips circuitId', () => {
    const filter = { circuitId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
  });

  it('round trips segmentOrder range', () => {
    const filter = { segmentOrderMin: 1, segmentOrderMax: 9 };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).segmentOrderMax).toBe(9);
  });

  it('round trips aTerminationId', () => {
    const filter = { aTerminationId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
  });

  it('round trips zTerminationId', () => {
    const filter = { zTerminationId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
  });

  it('round trips latencyMs range', () => {
    const filter = { latencyMsMin: 1, latencyMsMax: 9 };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).latencyMsMax).toBe(9);
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseCircuitSegmentFilter(serializeCircuitSegmentFilter(filter))).toEqual(filter);
    expect(toCircuitSegmentQuery(filter).costCenter).toBe('abc');
  });
});
