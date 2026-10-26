import { describe, expect, it } from 'vitest';
import { countQosPolicyActiveFilters, parseQosPolicyFilter, serializeQosPolicyFilter, toQosPolicyQuery } from '../filterState';

describe('qosPolicy filter state', () => {
  it('is empty by default', () => {
    expect(parseQosPolicyFilter(new URLSearchParams())).toEqual({});
    expect(countQosPolicyActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).externalRef).toBe('abc');
  });

  it('round trips policyName', () => {
    const filter = { policyName: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).policyName).toBe('abc');
  });

  it('round trips classCount range', () => {
    const filter = { classCountMin: 1, classCountMax: 9 };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).classCountMax).toBe(9);
  });

  it('round trips cirMbps range', () => {
    const filter = { cirMbpsMin: 1, cirMbpsMax: 9 };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).cirMbpsMax).toBe(9);
  });

  it('round trips pirMbps range', () => {
    const filter = { pirMbpsMin: 1, pirMbpsMax: 9 };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).pirMbpsMax).toBe(9);
  });

  it('round trips markingScheme', () => {
    const filter = { markingScheme: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).markingScheme).toBe('abc');
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseQosPolicyFilter(serializeQosPolicyFilter(filter))).toEqual(filter);
    expect(toQosPolicyQuery(filter).owner).toBe('abc');
  });
});
