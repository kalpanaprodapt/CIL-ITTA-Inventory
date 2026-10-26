import { describe, expect, it } from 'vitest';
import { cleanCityValues, validateCity } from '../schema';
import { cityInput } from './fixtures';

describe('city schema', () => {
  it('accepts the fixture', () => {
    expect(validateCity(cityInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCityValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCity({ ...cityInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...cityInput };
    delete values.code;
    const result = validateCity(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCity({ ...cityInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...cityInput };
    delete values.name;
    const result = validateCity(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCity({ ...cityInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...cityInput };
    delete values.status;
    const result = validateCity(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCity({ ...cityInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCity({ ...cityInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires regionId', () => {
    const values: Record<string, unknown> = { ...cityInput };
    delete values.regionId;
    const result = validateCity(values);
    expect(result.ok).toBe(false);
    expect(result.errors.regionId).toBeDefined();
  });

  it('rejects malformed regionId', () => {
    const result = validateCity({ ...cityInput, regionId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.regionId).toContain('UUID');
  });

  it('rejects postalPrefix longer than 255', () => {
    const result = validateCity({ ...cityInput, postalPrefix: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.postalPrefix).toContain('Postal Prefix');
  });

  it('rejects negative population', () => {
    const result = validateCity({ ...cityInput, population: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.population).toContain('negative');
  });

  it('rejects metroArea longer than 255', () => {
    const result = validateCity({ ...cityInput, metroArea: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.metroArea).toContain('Metro Area');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateCity({ ...cityInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCity({ ...cityInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCity({ ...cityInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCity({ ...cityInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateCity({ ...cityInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
