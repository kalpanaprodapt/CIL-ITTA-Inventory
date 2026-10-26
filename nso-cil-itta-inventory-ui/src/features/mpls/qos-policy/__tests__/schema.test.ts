import { describe, expect, it } from 'vitest';
import { cleanQosPolicyValues, validateQosPolicy } from '../schema';
import { qosPolicyInput } from './fixtures';

describe('qosPolicy schema', () => {
  it('accepts the fixture', () => {
    expect(validateQosPolicy(qosPolicyInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanQosPolicyValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...qosPolicyInput };
    delete values.code;
    const result = validateQosPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...qosPolicyInput };
    delete values.name;
    const result = validateQosPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...qosPolicyInput };
    delete values.status;
    const result = validateQosPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects policyName longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, policyName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.policyName).toContain('Policy Name');
  });

  it('requires policyName', () => {
    const values: Record<string, unknown> = { ...qosPolicyInput };
    delete values.policyName;
    const result = validateQosPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.policyName).toBeDefined();
  });

  it('requires classCount', () => {
    const values: Record<string, unknown> = { ...qosPolicyInput };
    delete values.classCount;
    const result = validateQosPolicy(values);
    expect(result.ok).toBe(false);
    expect(result.errors.classCount).toBeDefined();
  });

  it('rejects negative classCount', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, classCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.classCount).toContain('negative');
  });

  it('rejects negative cirMbps', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, cirMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.cirMbps).toContain('negative');
  });

  it('rejects negative pirMbps', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, pirMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.pirMbps).toContain('negative');
  });

  it('rejects markingScheme longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, markingScheme: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.markingScheme).toContain('Marking Scheme');
  });

  it('rejects labels longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateQosPolicy({ ...qosPolicyInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
