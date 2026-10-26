import { describe, expect, it } from 'vitest';
import { countComplianceExceptionActiveFilters, parseComplianceExceptionFilter, serializeComplianceExceptionFilter, toComplianceExceptionQuery } from '../filterState';

describe('complianceException filter state', () => {
  it('is empty by default', () => {
    expect(parseComplianceExceptionFilter(new URLSearchParams())).toEqual({});
    expect(countComplianceExceptionActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).externalRef).toBe('abc');
  });

  it('round trips ruleId', () => {
    const filter = { ruleId: '00000000-0000-4000-8000-000000000011' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
  });

  it('round trips targetRef', () => {
    const filter = { targetRef: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).targetRef).toBe('abc');
  });

  it('round trips approvedBy', () => {
    const filter = { approvedBy: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).approvedBy).toBe('abc');
  });

  it('round trips expiresOn window', () => {
    const filter = { expiresOnFrom: '2026-01-01', expiresOnTo: '2026-02-01' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseComplianceExceptionFilter(serializeComplianceExceptionFilter(filter))).toEqual(filter);
    expect(toComplianceExceptionQuery(filter).region).toBe('abc');
  });
});
