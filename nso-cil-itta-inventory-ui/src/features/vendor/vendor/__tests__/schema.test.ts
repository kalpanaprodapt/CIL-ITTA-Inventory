import { describe, expect, it } from 'vitest';
import { cleanVendorValues, validateVendor } from '../schema';
import { vendorInput } from './fixtures';

describe('vendor schema', () => {
  it('accepts the fixture', () => {
    expect(validateVendor(vendorInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanVendorValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateVendor({ ...vendorInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...vendorInput };
    delete values.code;
    const result = validateVendor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateVendor({ ...vendorInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...vendorInput };
    delete values.name;
    const result = validateVendor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateVendor({ ...vendorInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...vendorInput };
    delete values.status;
    const result = validateVendor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateVendor({ ...vendorInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateVendor({ ...vendorInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects legalName longer than 255', () => {
    const result = validateVendor({ ...vendorInput, legalName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.legalName).toContain('Legal Name');
  });

  it('requires legalName', () => {
    const values: Record<string, unknown> = { ...vendorInput };
    delete values.legalName;
    const result = validateVendor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.legalName).toBeDefined();
  });

  it('rejects supportEmail longer than 255', () => {
    const result = validateVendor({ ...vendorInput, supportEmail: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.supportEmail).toContain('Support Email');
  });

  it('rejects supportPhone longer than 255', () => {
    const result = validateVendor({ ...vendorInput, supportPhone: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.supportPhone).toContain('Support Phone');
  });

  it('rejects website longer than 255', () => {
    const result = validateVendor({ ...vendorInput, website: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.website).toContain('Website');
  });

  it('rejects unknown tier', () => {
    const result = validateVendor({ ...vendorInput, tier: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateVendor({ ...vendorInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateVendor({ ...vendorInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateVendor({ ...vendorInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateVendor({ ...vendorInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateVendor({ ...vendorInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
