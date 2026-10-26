import { describe, expect, it } from 'vitest';
import { cleanRegionValues, validateRegion } from '../schema';
import { regionInput } from './fixtures';

describe('region schema', () => {
  it('accepts the fixture', () => {
    expect(validateRegion(regionInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanRegionValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateRegion({ ...regionInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...regionInput };
    delete values.code;
    const result = validateRegion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateRegion({ ...regionInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...regionInput };
    delete values.name;
    const result = validateRegion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateRegion({ ...regionInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...regionInput };
    delete values.status;
    const result = validateRegion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateRegion({ ...regionInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateRegion({ ...regionInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires countryId', () => {
    const values: Record<string, unknown> = { ...regionInput };
    delete values.countryId;
    const result = validateRegion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.countryId).toBeDefined();
  });

  it('rejects malformed countryId', () => {
    const result = validateRegion({ ...regionInput, countryId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.countryId).toContain('UUID');
  });

  it('rejects unknown regionType', () => {
    const result = validateRegion({ ...regionInput, regionType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative population', () => {
    const result = validateRegion({ ...regionInput, population: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.population).toContain('negative');
  });

  it('rejects unknown criticality', () => {
    const result = validateRegion({ ...regionInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateRegion({ ...regionInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateRegion({ ...regionInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateRegion({ ...regionInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateRegion({ ...regionInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
