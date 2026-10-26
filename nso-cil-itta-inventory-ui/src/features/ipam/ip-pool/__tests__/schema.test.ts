import { describe, expect, it } from 'vitest';
import { cleanIpPoolValues, validateIpPool } from '../schema';
import { ipPoolInput } from './fixtures';

describe('ipPool schema', () => {
  it('accepts the fixture', () => {
    expect(validateIpPool(ipPoolInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanIpPoolValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateIpPool({ ...ipPoolInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...ipPoolInput };
    delete values.code;
    const result = validateIpPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateIpPool({ ...ipPoolInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...ipPoolInput };
    delete values.name;
    const result = validateIpPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateIpPool({ ...ipPoolInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...ipPoolInput };
    delete values.status;
    const result = validateIpPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateIpPool({ ...ipPoolInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateIpPool({ ...ipPoolInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires subnetId', () => {
    const values: Record<string, unknown> = { ...ipPoolInput };
    delete values.subnetId;
    const result = validateIpPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.subnetId).toBeDefined();
  });

  it('rejects malformed subnetId', () => {
    const result = validateIpPool({ ...ipPoolInput, subnetId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.subnetId).toContain('UUID');
  });

  it('rejects rangeStart longer than 255', () => {
    const result = validateIpPool({ ...ipPoolInput, rangeStart: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.rangeStart).toContain('Range Start');
  });

  it('requires rangeStart', () => {
    const values: Record<string, unknown> = { ...ipPoolInput };
    delete values.rangeStart;
    const result = validateIpPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.rangeStart).toBeDefined();
  });

  it('rejects rangeEnd longer than 255', () => {
    const result = validateIpPool({ ...ipPoolInput, rangeEnd: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.rangeEnd).toContain('Range End');
  });

  it('requires rangeEnd', () => {
    const values: Record<string, unknown> = { ...ipPoolInput };
    delete values.rangeEnd;
    const result = validateIpPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.rangeEnd).toBeDefined();
  });

  it('rejects unknown poolType', () => {
    const result = validateIpPool({ ...ipPoolInput, poolType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative leaseSeconds', () => {
    const result = validateIpPool({ ...ipPoolInput, leaseSeconds: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.leaseSeconds).toContain('negative');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateIpPool({ ...ipPoolInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateIpPool({ ...ipPoolInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateIpPool({ ...ipPoolInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateIpPool({ ...ipPoolInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateIpPool({ ...ipPoolInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
