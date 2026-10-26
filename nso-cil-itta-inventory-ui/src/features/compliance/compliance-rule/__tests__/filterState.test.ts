import { describe, expect, it } from 'vitest';
import { countComplianceRuleActiveFilters, parseComplianceRuleFilter, serializeComplianceRuleFilter, toComplianceRuleQuery } from '../filterState';

describe('complianceRule filter state', () => {
  it('is empty by default', () => {
    expect(parseComplianceRuleFilter(new URLSearchParams())).toEqual({});
    expect(countComplianceRuleActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).externalRef).toBe('abc');
  });

  it('round trips ruleCode', () => {
    const filter = { ruleCode: 'abc' };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).ruleCode).toBe('abc');
  });

  it('round trips framework selection', () => {
    const filter = { framework: ['ISO27001'] as never };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).framework).toBe('ISO27001');
  });

  it('round trips severity selection', () => {
    const filter = { severity: ['LOW'] as never };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).severity).toBe('LOW');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseComplianceRuleFilter(serializeComplianceRuleFilter(filter))).toEqual(filter);
    expect(toComplianceRuleQuery(filter).costCenter).toBe('abc');
  });
});
