import { describe, expect, it } from 'vitest';
import { cleanSkuValues, validateSku } from '../schema';
import { skuInput } from './fixtures';

describe('sku schema', () => {
  it('accepts the fixture', () => {
    expect(validateSku(skuInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSkuValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSku({ ...skuInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...skuInput };
    delete values.code;
    const result = validateSku(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSku({ ...skuInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...skuInput };
    delete values.name;
    const result = validateSku(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSku({ ...skuInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...skuInput };
    delete values.status;
    const result = validateSku(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSku({ ...skuInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSku({ ...skuInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires modelId', () => {
    const values: Record<string, unknown> = { ...skuInput };
    delete values.modelId;
    const result = validateSku(values);
    expect(result.ok).toBe(false);
    expect(result.errors.modelId).toBeDefined();
  });

  it('rejects malformed modelId', () => {
    const result = validateSku({ ...skuInput, modelId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.modelId).toContain('UUID');
  });

  it('rejects skuCode longer than 255', () => {
    const result = validateSku({ ...skuInput, skuCode: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.skuCode).toContain('Sku Code');
  });

  it('requires skuCode', () => {
    const values: Record<string, unknown> = { ...skuInput };
    delete values.skuCode;
    const result = validateSku(values);
    expect(result.ok).toBe(false);
    expect(result.errors.skuCode).toBeDefined();
  });

  it('rejects negative listPrice', () => {
    const result = validateSku({ ...skuInput, listPrice: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.listPrice).toContain('negative');
  });

  it('rejects currency longer than 255', () => {
    const result = validateSku({ ...skuInput, currency: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.currency).toContain('Currency');
  });

  it('rejects negative leadTimeDays', () => {
    const result = validateSku({ ...skuInput, leadTimeDays: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.leadTimeDays).toContain('negative');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateSku({ ...skuInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateSku({ ...skuInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSku({ ...skuInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSku({ ...skuInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSku({ ...skuInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
