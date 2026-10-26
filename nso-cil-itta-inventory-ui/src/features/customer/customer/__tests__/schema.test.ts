import { describe, expect, it } from 'vitest';
import { cleanCustomerValues, validateCustomer } from '../schema';
import { customerInput } from './fixtures';

describe('customer schema', () => {
  it('accepts the fixture', () => {
    expect(validateCustomer(customerInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCustomerValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCustomer({ ...customerInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...customerInput };
    delete values.code;
    const result = validateCustomer(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCustomer({ ...customerInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...customerInput };
    delete values.name;
    const result = validateCustomer(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCustomer({ ...customerInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...customerInput };
    delete values.status;
    const result = validateCustomer(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCustomer({ ...customerInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCustomer({ ...customerInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects legalName longer than 255', () => {
    const result = validateCustomer({ ...customerInput, legalName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.legalName).toContain('Legal Name');
  });

  it('requires legalName', () => {
    const values: Record<string, unknown> = { ...customerInput };
    delete values.legalName;
    const result = validateCustomer(values);
    expect(result.ok).toBe(false);
    expect(result.errors.legalName).toBeDefined();
  });

  it('rejects unknown segment', () => {
    const result = validateCustomer({ ...customerInput, segment: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects taxId longer than 255', () => {
    const result = validateCustomer({ ...customerInput, taxId: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.taxId).toContain('Tax Id');
  });

  it('rejects industry longer than 255', () => {
    const result = validateCustomer({ ...customerInput, industry: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.industry).toContain('Industry');
  });

  it('rejects accountManager longer than 255', () => {
    const result = validateCustomer({ ...customerInput, accountManager: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.accountManager).toContain('Account Manager');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateCustomer({ ...customerInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateCustomer({ ...customerInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCustomer({ ...customerInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCustomer({ ...customerInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCustomer({ ...customerInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
