import { describe, expect, it } from 'vitest';
import { cleanDiscoveryJobValues, validateDiscoveryJob } from '../schema';
import { discoveryJobInput } from './fixtures';

describe('discoveryJob schema', () => {
  it('accepts the fixture', () => {
    expect(validateDiscoveryJob(discoveryJobInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanDiscoveryJobValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...discoveryJobInput };
    delete values.code;
    const result = validateDiscoveryJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...discoveryJobInput };
    delete values.name;
    const result = validateDiscoveryJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...discoveryJobInput };
    delete values.status;
    const result = validateDiscoveryJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires profileId', () => {
    const values: Record<string, unknown> = { ...discoveryJobInput };
    delete values.profileId;
    const result = validateDiscoveryJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.profileId).toBeDefined();
  });

  it('rejects malformed profileId', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, profileId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.profileId).toContain('UUID');
  });

  it('rejects unknown jobState', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, jobState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative assetsFound', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, assetsFound: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.assetsFound).toContain('negative');
  });

  it('rejects owner longer than 255', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateDiscoveryJob({ ...discoveryJobInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
