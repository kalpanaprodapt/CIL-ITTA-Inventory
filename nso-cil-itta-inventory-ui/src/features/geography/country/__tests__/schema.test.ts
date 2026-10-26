import { describe, expect, it } from 'vitest';
import { cleanCountryValues, validateCountry } from '../schema';
import { countryInput } from './fixtures';

describe('country schema', () => {
  it('accepts the fixture', () => {
    expect(validateCountry(countryInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCountryValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCountry({ ...countryInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...countryInput };
    delete values.code;
    const result = validateCountry(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCountry({ ...countryInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...countryInput };
    delete values.name;
    const result = validateCountry(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCountry({ ...countryInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...countryInput };
    delete values.status;
    const result = validateCountry(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCountry({ ...countryInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCountry({ ...countryInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects isoCode longer than 255', () => {
    const result = validateCountry({ ...countryInput, isoCode: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.isoCode).toContain('Iso Code');
  });

  it('requires isoCode', () => {
    const values: Record<string, unknown> = { ...countryInput };
    delete values.isoCode;
    const result = validateCountry(values);
    expect(result.ok).toBe(false);
    expect(result.errors.isoCode).toBeDefined();
  });

  it('rejects negative isoNumeric', () => {
    const result = validateCountry({ ...countryInput, isoNumeric: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.isoNumeric).toContain('negative');
  });

  it('rejects dialCode longer than 255', () => {
    const result = validateCountry({ ...countryInput, dialCode: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.dialCode).toContain('Dial Code');
  });

  it('rejects currency longer than 255', () => {
    const result = validateCountry({ ...countryInput, currency: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.currency).toContain('Currency');
  });

  it('rejects regulatoryBody longer than 255', () => {
    const result = validateCountry({ ...countryInput, regulatoryBody: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.regulatoryBody).toContain('Regulatory Body');
  });

  it('rejects timezone longer than 255', () => {
    const result = validateCountry({ ...countryInput, timezone: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.timezone).toContain('Timezone');
  });

  it('rejects region longer than 255', () => {
    const result = validateCountry({ ...countryInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateCountry({ ...countryInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateCountry({ ...countryInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateCountry({ ...countryInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateCountry({ ...countryInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
