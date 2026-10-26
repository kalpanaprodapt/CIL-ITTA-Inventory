import { describe, expect, it } from 'vitest';
import { countThresholdPolicyActiveFilters, parseThresholdPolicyFilter, serializeThresholdPolicyFilter, toThresholdPolicyQuery } from '../filterState';

describe('thresholdPolicy filter state', () => {
  it('is empty by default', () => {
    expect(parseThresholdPolicyFilter(new URLSearchParams())).toEqual({});
    expect(countThresholdPolicyActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).externalRef).toBe('abc');
  });

  it('round trips policyName', () => {
    const filter = { policyName: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).policyName).toBe('abc');
  });

  it('round trips metric', () => {
    const filter = { metric: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).metric).toBe('abc');
  });

  it('round trips warnPct range', () => {
    const filter = { warnPctMin: 1, warnPctMax: 9 };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).warnPctMax).toBe(9);
  });

  it('round trips critPct range', () => {
    const filter = { critPctMin: 1, critPctMax: 9 };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).critPctMax).toBe(9);
  });

  it('round trips evaluationWindowMinutes range', () => {
    const filter = { evaluationWindowMinutesMin: 1, evaluationWindowMinutesMax: 9 };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).evaluationWindowMinutesMax).toBe(9);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseThresholdPolicyFilter(serializeThresholdPolicyFilter(filter))).toEqual(filter);
    expect(toThresholdPolicyQuery(filter).costCenter).toBe('abc');
  });
});
