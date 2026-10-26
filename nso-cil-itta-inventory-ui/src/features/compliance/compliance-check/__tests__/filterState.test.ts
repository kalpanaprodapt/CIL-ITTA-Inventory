import { describe, expect, it } from 'vitest';
import { countComplianceCheckActiveFilters, parseComplianceCheckFilter, serializeComplianceCheckFilter, toComplianceCheckQuery } from '../filterState';

describe('complianceCheck filter state', () => {
  it('is empty by default', () => {
    expect(parseComplianceCheckFilter(new URLSearchParams())).toEqual({});
    expect(countComplianceCheckActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).externalRef).toBe('abc');
  });

  it('round trips ruleId', () => {
    const filter = { ruleId: '00000000-0000-4000-8000-000000000011' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
  });

  it('round trips targetRef', () => {
    const filter = { targetRef: 'abc' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).targetRef).toBe('abc');
  });

  it('round trips checkedAt window', () => {
    const filter = { checkedAtFrom: '2026-01-01', checkedAtTo: '2026-02-01' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
  });

  it('round trips result selection', () => {
    const filter = { result: ['PASS'] as never };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).result).toBe('PASS');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
    expect(toComplianceCheckQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseComplianceCheckFilter(serializeComplianceCheckFilter(filter))).toEqual(filter);
  });
});
