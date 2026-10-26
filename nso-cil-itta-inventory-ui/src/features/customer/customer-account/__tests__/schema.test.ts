import { describe, expect, it } from 'vitest';
import { cleanCustomerAccountValues, validateCustomerAccount } from '../schema';
import { customerAccountInput } from './fixtures';

describe('customerAccount schema', () => {
  it('accepts the fixture', () => {
    expect(validateCustomerAccount(customerAccountInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCustomerAccountValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...customerAccountInput };
    delete values.code;
    const result = validateCustomerAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...customerAccountInput };
    delete values.name;
    const result = validateCustomerAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...customerAccountInput };
    delete values.status;
    const result = validateCustomerAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires customerId', () => {
    const values: Record<string, unknown> = { ...customerAccountInput };
    delete values.customerId;
    const result = validateCustomerAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toBeDefined();
  });

  it('rejects malformed customerId', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, customerId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toContain('UUID');
  });

  it('rejects accountNumber longer than 255', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, accountNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.accountNumber).toContain('Account Number');
  });

  it('requires accountNumber', () => {
    const values: Record<string, unknown> = { ...customerAccountInput };
    delete values.accountNumber;
    const result = validateCustomerAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.accountNumber).toBeDefined();
  });

  it('rejects unknown billingCycle', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, billingCycle: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects currency longer than 255', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, currency: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.currency).toContain('Currency');
  });

  it('rejects negative creditLimit', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, creditLimit: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.creditLimit).toContain('negative');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateCustomerAccount({ ...customerAccountInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
