import { describe, expect, it } from 'vitest';
import { cleanDiscoveredAssetValues, validateDiscoveredAsset } from '../schema';
import { discoveredAssetInput } from './fixtures';

describe('discoveredAsset schema', () => {
  it('accepts the fixture', () => {
    expect(validateDiscoveredAsset(discoveredAssetInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanDiscoveredAssetValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...discoveredAssetInput };
    delete values.code;
    const result = validateDiscoveredAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...discoveredAssetInput };
    delete values.name;
    const result = validateDiscoveredAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...discoveredAssetInput };
    delete values.status;
    const result = validateDiscoveredAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires jobId', () => {
    const values: Record<string, unknown> = { ...discoveredAssetInput };
    delete values.jobId;
    const result = validateDiscoveredAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.jobId).toBeDefined();
  });

  it('rejects malformed jobId', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, jobId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.jobId).toContain('UUID');
  });

  it('rejects assetType longer than 255', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, assetType: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.assetType).toContain('Asset Type');
  });

  it('requires assetType', () => {
    const values: Record<string, unknown> = { ...discoveredAssetInput };
    delete values.assetType;
    const result = validateDiscoveredAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.assetType).toBeDefined();
  });

  it('rejects fingerprint longer than 255', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, fingerprint: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.fingerprint).toContain('Fingerprint');
  });

  it('requires fingerprint', () => {
    const values: Record<string, unknown> = { ...discoveredAssetInput };
    delete values.fingerprint;
    const result = validateDiscoveredAsset(values);
    expect(result.ok).toBe(false);
    expect(result.errors.fingerprint).toBeDefined();
  });

  it('rejects rawPayload longer than 4000', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, rawPayload: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.rawPayload).toContain('Raw Payload');
  });

  it('rejects malformed matchedAssetId', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, matchedAssetId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.matchedAssetId).toContain('UUID');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateDiscoveredAsset({ ...discoveredAssetInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
