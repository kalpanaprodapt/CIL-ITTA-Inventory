import { describe, expect, it } from 'vitest';
import { cleanWarrantyValues, validateWarranty } from '../schema';
import { warrantyInput } from './fixtures';

describe('warranty schema', () => {
  it('accepts the fixture', () => {
    expect(validateWarranty(warrantyInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanWarrantyValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateWarranty({ ...warrantyInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...warrantyInput };
    delete values.code;
    const result = validateWarranty(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateWarranty({ ...warrantyInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...warrantyInput };
    delete values.name;
    const result = validateWarranty(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateWarranty({ ...warrantyInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...warrantyInput };
    delete values.status;
    const result = validateWarranty(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateWarranty({ ...warrantyInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateWarranty({ ...warrantyInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires assetId', () => {
    const values: Record<string, unknown> = { ...warrantyInput };
    delete values.assetId;
    const result = validateWarranty(values);
    expect(result.ok).toBe(false);
    expect(result.errors.assetId).toBeDefined();
  });

  it('rejects malformed assetId', () => {
    const result = validateWarranty({ ...warrantyInput, assetId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.assetId).toContain('UUID');
  });

  it('requires startDate', () => {
    const values: Record<string, unknown> = { ...warrantyInput };
    delete values.startDate;
    const result = validateWarranty(values);
    expect(result.ok).toBe(false);
    expect(result.errors.startDate).toBeDefined();
  });

  it('rejects malformed date in startDate', () => {
    const result = validateWarranty({ ...warrantyInput, startDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('requires endDate', () => {
    const values: Record<string, unknown> = { ...warrantyInput };
    delete values.endDate;
    const result = validateWarranty(values);
    expect(result.ok).toBe(false);
    expect(result.errors.endDate).toBeDefined();
  });

  it('rejects malformed date in endDate', () => {
    const result = validateWarranty({ ...warrantyInput, endDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown warrantyType', () => {
    const result = validateWarranty({ ...warrantyInput, warrantyType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects terms longer than 4000', () => {
    const result = validateWarranty({ ...warrantyInput, terms: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.terms).toContain('Terms');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateWarranty({ ...warrantyInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateWarranty({ ...warrantyInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateWarranty({ ...warrantyInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateWarranty({ ...warrantyInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateWarranty({ ...warrantyInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
