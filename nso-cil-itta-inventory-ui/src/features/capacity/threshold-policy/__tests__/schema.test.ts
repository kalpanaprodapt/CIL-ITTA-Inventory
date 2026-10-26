import { describe, expect, it } from 'vitest';
import { cleanThresholdPolicyValues, validateThresholdPolicy } from '../schema';
import { thresholdPolicyInput } from './fixtures';

describe('thresholdPolicy schema', () => {
  it('accepts the fixture', () => {
    expect(validateThresholdPolicy(thresholdPolicyInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanThresholdPolicyValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...thresholdPolicyInput };
    delete values.code;
    const result = validateThresholdPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...thresholdPolicyInput };
    delete values.name;
    const result = validateThresholdPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...thresholdPolicyInput };
    delete values.status;
    const result = validateThresholdPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects policyName longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, policyName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.policyName).toContain('Policy Name');
  });

  it('requires policyName', () => {
    const values: Record<string, unknown> = { ...thresholdPolicyInput };
    delete values.policyName;
    const result = validateThresholdPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.policyName).toBeDefined();
  });

  it('rejects metric longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, metric: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.metric).toContain('Metric');
  });

  it('requires metric', () => {
    const values: Record<string, unknown> = { ...thresholdPolicyInput };
    delete values.metric;
    const result = validateThresholdPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.metric).toBeDefined();
  });

  it('requires warnPct', () => {
    const values: Record<string, unknown> = { ...thresholdPolicyInput };
    delete values.warnPct;
    const result = validateThresholdPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.warnPct).toBeDefined();
  });

  it('rejects negative warnPct', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, warnPct: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.warnPct).toContain('negative');
  });

  it('requires critPct', () => {
    const values: Record<string, unknown> = { ...thresholdPolicyInput };
    delete values.critPct;
    const result = validateThresholdPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.critPct).toBeDefined();
  });

  it('rejects negative critPct', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, critPct: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.critPct).toContain('negative');
  });

  it('rejects negative evaluationWindowMinutes', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, evaluationWindowMinutes: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.evaluationWindowMinutes).toContain('negative');
  });

  it('rejects region longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateThresholdPolicy({ ...thresholdPolicyInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
