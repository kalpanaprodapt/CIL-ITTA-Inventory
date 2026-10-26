import { describe, expect, it } from 'vitest';
import { cleanCustomerContactValues, validateCustomerContact } from '../schema';
import { customerContactInput } from './fixtures';

describe('customerContact schema', () => {
  it('accepts the fixture', () => {
    expect(validateCustomerContact(customerContactInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCustomerContactValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...customerContactInput };
    delete values.code;
    const result = validateCustomerContact(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...customerContactInput };
    delete values.name;
    const result = validateCustomerContact(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCustomerContact({ ...customerContactInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...customerContactInput };
    delete values.status;
    const result = validateCustomerContact(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCustomerContact({ ...customerContactInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires customerId', () => {
    const values: Record<string, unknown> = { ...customerContactInput };
    delete values.customerId;
    const result = validateCustomerContact(values);
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toBeDefined();
  });

  it('rejects malformed customerId', () => {
    const result = validateCustomerContact({ ...customerContactInput, customerId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toContain('UUID');
  });

  it('rejects fullName longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, fullName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.fullName).toContain('Full Name');
  });

  it('requires fullName', () => {
    const values: Record<string, unknown> = { ...customerContactInput };
    delete values.fullName;
    const result = validateCustomerContact(values);
    expect(result.ok).toBe(false);
    expect(result.errors.fullName).toBeDefined();
  });

  it('rejects email longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, email: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.email).toContain('Email');
  });

  it('rejects phone longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, phone: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.phone).toContain('Phone');
  });

  it('rejects unknown contactRole', () => {
    const result = validateCustomerContact({ ...customerContactInput, contactRole: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateCustomerContact({ ...customerContactInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateCustomerContact({ ...customerContactInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
