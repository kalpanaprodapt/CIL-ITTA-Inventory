import { describe, expect, it } from 'vitest';
import { cleanCloudAccountValues, validateCloudAccount } from '../schema';
import { cloudAccountInput } from './fixtures';

describe('cloudAccount schema', () => {
  it('accepts the fixture', () => {
    expect(validateCloudAccount(cloudAccountInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCloudAccountValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...cloudAccountInput };
    delete values.code;
    const result = validateCloudAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...cloudAccountInput };
    delete values.name;
    const result = validateCloudAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...cloudAccountInput };
    delete values.status;
    const result = validateCloudAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects unknown provider', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, provider: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects accountRef longer than 255', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, accountRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.accountRef).toContain('Account Ref');
  });

  it('requires accountRef', () => {
    const values: Record<string, unknown> = { ...cloudAccountInput };
    delete values.accountRef;
    const result = validateCloudAccount(values);
    expect(result.ok).toBe(false);
    expect(result.errors.accountRef).toBeDefined();
  });

  it('rejects subscriptionName longer than 255', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, subscriptionName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.subscriptionName).toContain('Subscription Name');
  });

  it('rejects negative monthlyBudget', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, monthlyBudget: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.monthlyBudget).toContain('negative');
  });

  it('rejects environment longer than 255', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, environment: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.environment).toContain('Environment');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateCloudAccount({ ...cloudAccountInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
