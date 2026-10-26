import { describe, expect, it } from 'vitest';
import { cleanLicenseKeyValues, validateLicenseKey } from '../schema';
import { licenseKeyInput } from './fixtures';

describe('licenseKey schema', () => {
  it('accepts the fixture', () => {
    expect(validateLicenseKey(licenseKeyInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanLicenseKeyValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...licenseKeyInput };
    delete values.code;
    const result = validateLicenseKey(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...licenseKeyInput };
    delete values.name;
    const result = validateLicenseKey(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...licenseKeyInput };
    delete values.status;
    const result = validateLicenseKey(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires poolId', () => {
    const values: Record<string, unknown> = { ...licenseKeyInput };
    delete values.poolId;
    const result = validateLicenseKey(values);
    expect(result.ok).toBe(false);
    expect(result.errors.poolId).toBeDefined();
  });

  it('rejects malformed poolId', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, poolId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.poolId).toContain('UUID');
  });

  it('rejects keyValue longer than 255', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, keyValue: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.keyValue).toContain('Key Value');
  });

  it('requires keyValue', () => {
    const values: Record<string, unknown> = { ...licenseKeyInput };
    delete values.keyValue;
    const result = validateLicenseKey(values);
    expect(result.ok).toBe(false);
    expect(result.errors.keyValue).toBeDefined();
  });

  it('rejects feature longer than 255', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, feature: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.feature).toContain('Feature');
  });

  it('requires feature', () => {
    const values: Record<string, unknown> = { ...licenseKeyInput };
    delete values.feature;
    const result = validateLicenseKey(values);
    expect(result.ok).toBe(false);
    expect(result.errors.feature).toBeDefined();
  });

  it('rejects malformed date in expiresOn', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, expiresOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed assignedDeviceId', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, assignedDeviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.assignedDeviceId).toContain('UUID');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateLicenseKey({ ...licenseKeyInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
