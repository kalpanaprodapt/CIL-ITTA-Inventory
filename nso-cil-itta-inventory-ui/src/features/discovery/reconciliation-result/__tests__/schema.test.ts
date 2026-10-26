import { describe, expect, it } from 'vitest';
import { cleanReconciliationResultValues, validateReconciliationResult } from '../schema';
import { reconciliationResultInput } from './fixtures';

describe('reconciliationResult schema', () => {
  it('accepts the fixture', () => {
    expect(validateReconciliationResult(reconciliationResultInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanReconciliationResultValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...reconciliationResultInput };
    delete values.code;
    const result = validateReconciliationResult(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...reconciliationResultInput };
    delete values.name;
    const result = validateReconciliationResult(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...reconciliationResultInput };
    delete values.status;
    const result = validateReconciliationResult(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires jobId', () => {
    const values: Record<string, unknown> = { ...reconciliationResultInput };
    delete values.jobId;
    const result = validateReconciliationResult(values);
    expect(result.ok).toBe(false);
    expect(result.errors.jobId).toBeDefined();
  });

  it('rejects malformed jobId', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, jobId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.jobId).toContain('UUID');
  });

  it('rejects malformed ruleId', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, ruleId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.ruleId).toContain('UUID');
  });

  it('rejects unknown outcome', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, outcome: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects diffSummary longer than 4000', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, diffSummary: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.diffSummary).toContain('Diff Summary');
  });

  it('rejects resolvedBy longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, resolvedBy: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.resolvedBy).toContain('Resolved By');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateReconciliationResult({ ...reconciliationResultInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
