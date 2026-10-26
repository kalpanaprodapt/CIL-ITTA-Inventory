import { describe, expect, it } from 'vitest';
import { cleanAssetValues, validateAsset } from '../schema';
import { assetInput } from './fixtures';

describe('asset schema', () => {
  it('accepts the fixture', () => {
    expect(validateAsset(assetInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanAssetValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateAsset({ ...assetInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...assetInput };
    delete values.code;
    const result = validateAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateAsset({ ...assetInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...assetInput };
    delete values.name;
    const result = validateAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateAsset({ ...assetInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...assetInput };
    delete values.status;
    const result = validateAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateAsset({ ...assetInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateAsset({ ...assetInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects assetTag longer than 255', () => {
    const result = validateAsset({ ...assetInput, assetTag: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.assetTag).toContain('Asset Tag');
  });

  it('requires assetTag', () => {
    const values: Record<string, unknown> = { ...assetInput };
    delete values.assetTag;
    const result = validateAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.assetTag).toBeDefined();
  });

  it('rejects assetClass longer than 255', () => {
    const result = validateAsset({ ...assetInput, assetClass: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.assetClass).toContain('Asset Class');
  });

  it('requires assetClass', () => {
    const values: Record<string, unknown> = { ...assetInput };
    delete values.assetClass;
    const result = validateAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.assetClass).toBeDefined();
  });

  it('rejects malformed date in acquiredOn', () => {
    const result = validateAsset({ ...assetInput, acquiredOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative purchasePrice', () => {
    const result = validateAsset({ ...assetInput, purchasePrice: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.purchasePrice).toContain('negative');
  });

  it('rejects negative currentValue', () => {
    const result = validateAsset({ ...assetInput, currentValue: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.currentValue).toContain('negative');
  });

  it('rejects custodian longer than 255', () => {
    const result = validateAsset({ ...assetInput, custodian: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.custodian).toContain('Custodian');
  });

  it('rejects labels longer than 255', () => {
    const result = validateAsset({ ...assetInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateAsset({ ...assetInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateAsset({ ...assetInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateAsset({ ...assetInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateAsset({ ...assetInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
