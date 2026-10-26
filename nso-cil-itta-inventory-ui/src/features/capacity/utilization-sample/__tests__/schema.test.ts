import { describe, expect, it } from 'vitest';
import { cleanUtilizationSampleValues, validateUtilizationSample } from '../schema';
import { utilizationSampleInput } from './fixtures';

describe('utilizationSample schema', () => {
  it('accepts the fixture', () => {
    expect(validateUtilizationSample(utilizationSampleInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanUtilizationSampleValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...utilizationSampleInput };
    delete values.code;
    const result = validateUtilizationSample(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...utilizationSampleInput };
    delete values.name;
    const result = validateUtilizationSample(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...utilizationSampleInput };
    delete values.status;
    const result = validateUtilizationSample(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects targetRef longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, targetRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toContain('Target Ref');
  });

  it('requires targetRef', () => {
    const values: Record<string, unknown> = { ...utilizationSampleInput };
    delete values.targetRef;
    const result = validateUtilizationSample(values);
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toBeDefined();
  });

  it('rejects metric longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, metric: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.metric).toContain('Metric');
  });

  it('requires metric', () => {
    const values: Record<string, unknown> = { ...utilizationSampleInput };
    delete values.metric;
    const result = validateUtilizationSample(values);
    expect(result.ok).toBe(false);
    expect(result.errors.metric).toBeDefined();
  });

  it('requires sampledAt', () => {
    const values: Record<string, unknown> = { ...utilizationSampleInput };
    delete values.sampledAt;
    const result = validateUtilizationSample(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sampledAt).toBeDefined();
  });

  it('requires valuePct', () => {
    const values: Record<string, unknown> = { ...utilizationSampleInput };
    delete values.valuePct;
    const result = validateUtilizationSample(values);
    expect(result.ok).toBe(false);
    expect(result.errors.valuePct).toBeDefined();
  });

  it('rejects negative valuePct', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, valuePct: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.valuePct).toContain('negative');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateUtilizationSample({ ...utilizationSampleInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
