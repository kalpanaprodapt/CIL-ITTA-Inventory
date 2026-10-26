import { describe, expect, it } from 'vitest';
import { countReconciliationResultActiveFilters, parseReconciliationResultFilter, serializeReconciliationResultFilter, toReconciliationResultQuery } from '../filterState';

describe('reconciliationResult filter state', () => {
  it('is empty by default', () => {
    expect(parseReconciliationResultFilter(new URLSearchParams())).toEqual({});
    expect(countReconciliationResultActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).externalRef).toBe('abc');
  });

  it('round trips jobId', () => {
    const filter = { jobId: '00000000-0000-4000-8000-000000000011' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
  });

  it('round trips ruleId', () => {
    const filter = { ruleId: '00000000-0000-4000-8000-000000000011' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
  });

  it('round trips outcome selection', () => {
    const filter = { outcome: ['MATCHED'] as never };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).outcome).toBe('MATCHED');
  });

  it('round trips resolvedBy', () => {
    const filter = { resolvedBy: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).resolvedBy).toBe('abc');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseReconciliationResultFilter(serializeReconciliationResultFilter(filter))).toEqual(filter);
    expect(toReconciliationResultQuery(filter).costCenter).toBe('abc');
  });
});
