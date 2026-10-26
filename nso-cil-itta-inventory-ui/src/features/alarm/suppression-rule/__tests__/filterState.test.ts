import { describe, expect, it } from 'vitest';
import { countSuppressionRuleActiveFilters, parseSuppressionRuleFilter, serializeSuppressionRuleFilter, toSuppressionRuleQuery } from '../filterState';

describe('suppressionRule filter state', () => {
  it('is empty by default', () => {
    expect(parseSuppressionRuleFilter(new URLSearchParams())).toEqual({});
    expect(countSuppressionRuleActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).externalRef).toBe('abc');
  });

  it('round trips ruleName', () => {
    const filter = { ruleName: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).ruleName).toBe('abc');
  });

  it('round trips startsAt window', () => {
    const filter = { startsAtFrom: '2026-01-01', startsAtTo: '2026-02-01' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
  });

  it('round trips endsAt window', () => {
    const filter = { endsAtFrom: '2026-01-01', endsAtTo: '2026-02-01' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
  });

  it('round trips reason', () => {
    const filter = { reason: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).reason).toBe('abc');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSuppressionRuleFilter(serializeSuppressionRuleFilter(filter))).toEqual(filter);
    expect(toSuppressionRuleQuery(filter).costCenter).toBe('abc');
  });
});
