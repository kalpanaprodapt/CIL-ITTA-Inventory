import { describe, expect, it } from 'vitest';
import { cleanServiceInstanceValues, validateServiceInstance } from '../schema';
import { serviceInstanceInput } from './fixtures';

describe('serviceInstance schema', () => {
  it('accepts the fixture', () => {
    expect(validateServiceInstance(serviceInstanceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanServiceInstanceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...serviceInstanceInput };
    delete values.code;
    const result = validateServiceInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...serviceInstanceInput };
    delete values.name;
    const result = validateServiceInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...serviceInstanceInput };
    delete values.status;
    const result = validateServiceInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires catalogItemId', () => {
    const values: Record<string, unknown> = { ...serviceInstanceInput };
    delete values.catalogItemId;
    const result = validateServiceInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.catalogItemId).toBeDefined();
  });

  it('rejects malformed catalogItemId', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, catalogItemId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.catalogItemId).toContain('UUID');
  });

  it('requires customerId', () => {
    const values: Record<string, unknown> = { ...serviceInstanceInput };
    delete values.customerId;
    const result = validateServiceInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toBeDefined();
  });

  it('rejects malformed customerId', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, customerId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toContain('UUID');
  });

  it('rejects malformed date in activatedOn', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, activatedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative bandwidthMbps', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, bandwidthMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.bandwidthMbps).toContain('negative');
  });

  it('rejects unknown serviceState', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, serviceState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateServiceInstance({ ...serviceInstanceInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
