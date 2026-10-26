import { describe, expect, it } from 'vitest';
import { cleanVlanPoolValues, validateVlanPool } from '../schema';
import { vlanPoolInput } from './fixtures';

describe('vlanPool schema', () => {
  it('accepts the fixture', () => {
    expect(validateVlanPool(vlanPoolInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanVlanPoolValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...vlanPoolInput };
    delete values.code;
    const result = validateVlanPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...vlanPoolInput };
    delete values.name;
    const result = validateVlanPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateVlanPool({ ...vlanPoolInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...vlanPoolInput };
    delete values.status;
    const result = validateVlanPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateVlanPool({ ...vlanPoolInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects poolName longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, poolName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.poolName).toContain('Pool Name');
  });

  it('requires poolName', () => {
    const values: Record<string, unknown> = { ...vlanPoolInput };
    delete values.poolName;
    const result = validateVlanPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.poolName).toBeDefined();
  });

  it('requires rangeStart', () => {
    const values: Record<string, unknown> = { ...vlanPoolInput };
    delete values.rangeStart;
    const result = validateVlanPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.rangeStart).toBeDefined();
  });

  it('rejects negative rangeStart', () => {
    const result = validateVlanPool({ ...vlanPoolInput, rangeStart: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.rangeStart).toContain('negative');
  });

  it('requires rangeEnd', () => {
    const values: Record<string, unknown> = { ...vlanPoolInput };
    delete values.rangeEnd;
    const result = validateVlanPool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.rangeEnd).toBeDefined();
  });

  it('rejects negative rangeEnd', () => {
    const result = validateVlanPool({ ...vlanPoolInput, rangeEnd: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.rangeEnd).toContain('negative');
  });

  it('rejects negative allocated', () => {
    const result = validateVlanPool({ ...vlanPoolInput, allocated: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.allocated).toContain('negative');
  });

  it('rejects scope longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, scope: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.scope).toContain('Scope');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateVlanPool({ ...vlanPoolInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateVlanPool({ ...vlanPoolInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateVlanPool({ ...vlanPoolInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
