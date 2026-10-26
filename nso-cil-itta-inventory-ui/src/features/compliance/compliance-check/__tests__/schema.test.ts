import { describe, expect, it } from 'vitest';
import { cleanComplianceCheckValues, validateComplianceCheck } from '../schema';
import { complianceCheckInput } from './fixtures';

describe('complianceCheck schema', () => {
  it('accepts the fixture', () => {
    expect(validateComplianceCheck(complianceCheckInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanComplianceCheckValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...complianceCheckInput };
    delete values.code;
    const result = validateComplianceCheck(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...complianceCheckInput };
    delete values.name;
    const result = validateComplianceCheck(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...complianceCheckInput };
    delete values.status;
    const result = validateComplianceCheck(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires ruleId', () => {
    const values: Record<string, unknown> = { ...complianceCheckInput };
    delete values.ruleId;
    const result = validateComplianceCheck(values);
    expect(result.ok).toBe(false);
    expect(result.errors.ruleId).toBeDefined();
  });

  it('rejects malformed ruleId', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, ruleId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.ruleId).toContain('UUID');
  });

  it('rejects targetRef longer than 255', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, targetRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toContain('Target Ref');
  });

  it('requires targetRef', () => {
    const values: Record<string, unknown> = { ...complianceCheckInput };
    delete values.targetRef;
    const result = validateComplianceCheck(values);
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toBeDefined();
  });

  it('requires checkedAt', () => {
    const values: Record<string, unknown> = { ...complianceCheckInput };
    delete values.checkedAt;
    const result = validateComplianceCheck(values);
    expect(result.ok).toBe(false);
    expect(result.errors.checkedAt).toBeDefined();
  });

  it('rejects unknown result', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, result: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects evidence longer than 4000', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, evidence: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.evidence).toContain('Evidence');
  });

  it('rejects unknown criticality', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateComplianceCheck({ ...complianceCheckInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
