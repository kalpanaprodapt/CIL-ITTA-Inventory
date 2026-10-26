import { describe, expect, it } from 'vitest';
import { cleanVnfValues, validateVnf } from '../schema';
import { vnfInput } from './fixtures';

describe('vnf schema', () => {
  it('accepts the fixture', () => {
    expect(validateVnf(vnfInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanVnfValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateVnf({ ...vnfInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...vnfInput };
    delete values.code;
    const result = validateVnf(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateVnf({ ...vnfInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...vnfInput };
    delete values.name;
    const result = validateVnf(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateVnf({ ...vnfInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...vnfInput };
    delete values.status;
    const result = validateVnf(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateVnf({ ...vnfInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateVnf({ ...vnfInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects vnfName longer than 255', () => {
    const result = validateVnf({ ...vnfInput, vnfName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.vnfName).toContain('Vnf Name');
  });

  it('requires vnfName', () => {
    const values: Record<string, unknown> = { ...vnfInput };
    delete values.vnfName;
    const result = validateVnf(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vnfName).toBeDefined();
  });

  it('rejects vnfType longer than 255', () => {
    const result = validateVnf({ ...vnfInput, vnfType: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.vnfType).toContain('Vnf Type');
  });

  it('requires vnfType', () => {
    const values: Record<string, unknown> = { ...vnfInput };
    delete values.vnfType;
    const result = validateVnf(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vnfType).toBeDefined();
  });

  it('rejects flavor longer than 255', () => {
    const result = validateVnf({ ...vnfInput, flavor: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.flavor).toContain('Flavor');
  });

  it('rejects negative vcpu', () => {
    const result = validateVnf({ ...vnfInput, vcpu: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.vcpu).toContain('negative');
  });

  it('rejects negative memoryGb', () => {
    const result = validateVnf({ ...vnfInput, memoryGb: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.memoryGb).toContain('negative');
  });

  it('rejects negative instanceCount', () => {
    const result = validateVnf({ ...vnfInput, instanceCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.instanceCount).toContain('negative');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateVnf({ ...vnfInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateVnf({ ...vnfInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateVnf({ ...vnfInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateVnf({ ...vnfInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateVnf({ ...vnfInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
