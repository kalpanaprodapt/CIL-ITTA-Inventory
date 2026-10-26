import { describe, expect, it } from 'vitest';
import { cleanLicensePoolValues, validateLicensePool } from '../schema';
import { licensePoolInput } from './fixtures';

describe('licensePool schema', () => {
  it('accepts the fixture', () => {
    expect(validateLicensePool(licensePoolInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanLicensePoolValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateLicensePool({ ...licensePoolInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...licensePoolInput };
    delete values.code;
    const result = validateLicensePool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateLicensePool({ ...licensePoolInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...licensePoolInput };
    delete values.name;
    const result = validateLicensePool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateLicensePool({ ...licensePoolInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...licensePoolInput };
    delete values.status;
    const result = validateLicensePool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateLicensePool({ ...licensePoolInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateLicensePool({ ...licensePoolInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires vendorId', () => {
    const values: Record<string, unknown> = { ...licensePoolInput };
    delete values.vendorId;
    const result = validateLicensePool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toBeDefined();
  });

  it('rejects malformed vendorId', () => {
    const result = validateLicensePool({ ...licensePoolInput, vendorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toContain('UUID');
  });

  it('rejects poolName longer than 255', () => {
    const result = validateLicensePool({ ...licensePoolInput, poolName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.poolName).toContain('Pool Name');
  });

  it('requires poolName', () => {
    const values: Record<string, unknown> = { ...licensePoolInput };
    delete values.poolName;
    const result = validateLicensePool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.poolName).toBeDefined();
  });

  it('requires totalSeats', () => {
    const values: Record<string, unknown> = { ...licensePoolInput };
    delete values.totalSeats;
    const result = validateLicensePool(values);
    expect(result.ok).toBe(false);
    expect(result.errors.totalSeats).toBeDefined();
  });

  it('rejects negative totalSeats', () => {
    const result = validateLicensePool({ ...licensePoolInput, totalSeats: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.totalSeats).toContain('negative');
  });

  it('rejects negative usedSeats', () => {
    const result = validateLicensePool({ ...licensePoolInput, usedSeats: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.usedSeats).toContain('negative');
  });

  it('rejects malformed date in renewalDate', () => {
    const result = validateLicensePool({ ...licensePoolInput, renewalDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateLicensePool({ ...licensePoolInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateLicensePool({ ...licensePoolInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateLicensePool({ ...licensePoolInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateLicensePool({ ...licensePoolInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateLicensePool({ ...licensePoolInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
