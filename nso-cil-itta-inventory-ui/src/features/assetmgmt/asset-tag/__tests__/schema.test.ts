import { describe, expect, it } from 'vitest';
import { cleanAssetTagValues, validateAssetTag } from '../schema';
import { assetTagInput } from './fixtures';

describe('assetTag schema', () => {
  it('accepts the fixture', () => {
    expect(validateAssetTag(assetTagInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanAssetTagValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...assetTagInput };
    delete values.code;
    const result = validateAssetTag(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...assetTagInput };
    delete values.name;
    const result = validateAssetTag(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateAssetTag({ ...assetTagInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...assetTagInput };
    delete values.status;
    const result = validateAssetTag(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateAssetTag({ ...assetTagInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires assetId', () => {
    const values: Record<string, unknown> = { ...assetTagInput };
    delete values.assetId;
    const result = validateAssetTag(values);
    expect(result.ok).toBe(false);
    expect(result.errors.assetId).toBeDefined();
  });

  it('rejects malformed assetId', () => {
    const result = validateAssetTag({ ...assetTagInput, assetId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.assetId).toContain('UUID');
  });

  it('rejects tagValue longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, tagValue: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.tagValue).toContain('Tag Value');
  });

  it('requires tagValue', () => {
    const values: Record<string, unknown> = { ...assetTagInput };
    delete values.tagValue;
    const result = validateAssetTag(values);
    expect(result.ok).toBe(false);
    expect(result.errors.tagValue).toBeDefined();
  });

  it('rejects unknown tagType', () => {
    const result = validateAssetTag({ ...assetTagInput, tagType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in printedOn', () => {
    const result = validateAssetTag({ ...assetTagInput, printedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateAssetTag({ ...assetTagInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateAssetTag({ ...assetTagInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
