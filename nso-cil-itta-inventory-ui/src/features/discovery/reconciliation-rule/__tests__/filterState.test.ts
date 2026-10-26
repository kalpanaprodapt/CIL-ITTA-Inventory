import { describe, expect, it } from 'vitest';
import { countReconciliationRuleActiveFilters, parseReconciliationRuleFilter, serializeReconciliationRuleFilter, toReconciliationRuleQuery } from '../filterState';

describe('reconciliationRule filter state', () => {
  it('is empty by default', () => {
    expect(parseReconciliationRuleFilter(new URLSearchParams())).toEqual({});
    expect(countReconciliationRuleActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).externalRef).toBe('abc');
  });

  it('round trips ruleName', () => {
    const filter = { ruleName: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).ruleName).toBe('abc');
  });

  it('round trips matchKey', () => {
    const filter = { matchKey: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).matchKey).toBe('abc');
  });

  it('round trips action selection', () => {
    const filter = { action: ['MERGE'] as never };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).action).toBe('MERGE');
  });

  it('round trips priority range', () => {
    const filter = { priorityMin: 1, priorityMax: 9 };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).priorityMax).toBe(9);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseReconciliationRuleFilter(serializeReconciliationRuleFilter(filter))).toEqual(filter);
    expect(toReconciliationRuleQuery(filter).owner).toBe('abc');
  });
});
