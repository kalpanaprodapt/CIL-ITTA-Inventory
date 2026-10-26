import { describe, expect, it } from 'vitest';
import { countAuditRecordActiveFilters, parseAuditRecordFilter, serializeAuditRecordFilter, toAuditRecordQuery } from '../filterState';

describe('auditRecord filter state', () => {
  it('is empty by default', () => {
    expect(parseAuditRecordFilter(new URLSearchParams())).toEqual({});
    expect(countAuditRecordActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).externalRef).toBe('abc');
  });

  it('round trips actor', () => {
    const filter = { actor: 'abc' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).actor).toBe('abc');
  });

  it('round trips action', () => {
    const filter = { action: 'abc' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).action).toBe('abc');
  });

  it('round trips entityRef', () => {
    const filter = { entityRef: 'abc' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).entityRef).toBe('abc');
  });

  it('round trips occurredAt window', () => {
    const filter = { occurredAtFrom: '2026-01-01', occurredAtTo: '2026-02-01' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseAuditRecordFilter(serializeAuditRecordFilter(filter))).toEqual(filter);
    expect(toAuditRecordQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
