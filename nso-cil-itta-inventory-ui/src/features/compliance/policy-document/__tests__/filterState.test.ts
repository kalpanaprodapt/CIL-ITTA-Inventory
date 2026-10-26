import { describe, expect, it } from 'vitest';
import { countPolicyDocumentActiveFilters, parsePolicyDocumentFilter, serializePolicyDocumentFilter, toPolicyDocumentQuery } from '../filterState';

describe('policyDocument filter state', () => {
  it('is empty by default', () => {
    expect(parsePolicyDocumentFilter(new URLSearchParams())).toEqual({});
    expect(countPolicyDocumentActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).externalRef).toBe('abc');
  });

  it('round trips title', () => {
    const filter = { title: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).title).toBe('abc');
  });

  it('round trips docVersion', () => {
    const filter = { docVersion: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).docVersion).toBe('abc');
  });

  it('round trips effectiveDate window', () => {
    const filter = { effectiveDateFrom: '2026-01-01', effectiveDateTo: '2026-02-01' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).owner).toBe('abc');
  });

  it('round trips documentUrl', () => {
    const filter = { documentUrl: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).documentUrl).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).region).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parsePolicyDocumentFilter(serializePolicyDocumentFilter(filter))).toEqual(filter);
    expect(toPolicyDocumentQuery(filter).costCenter).toBe('abc');
  });
});
