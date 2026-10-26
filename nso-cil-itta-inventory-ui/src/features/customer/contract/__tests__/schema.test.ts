import { describe, expect, it } from 'vitest';
import { cleanContractValues, validateContract } from '../schema';
import { contractInput } from './fixtures';

describe('contract schema', () => {
  it('accepts the fixture', () => {
    expect(validateContract(contractInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanContractValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateContract({ ...contractInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...contractInput };
    delete values.code;
    const result = validateContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateContract({ ...contractInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...contractInput };
    delete values.name;
    const result = validateContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateContract({ ...contractInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...contractInput };
    delete values.status;
    const result = validateContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateContract({ ...contractInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateContract({ ...contractInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires customerId', () => {
    const values: Record<string, unknown> = { ...contractInput };
    delete values.customerId;
    const result = validateContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toBeDefined();
  });

  it('rejects malformed customerId', () => {
    const result = validateContract({ ...contractInput, customerId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toContain('UUID');
  });

  it('rejects contractNumber longer than 255', () => {
    const result = validateContract({ ...contractInput, contractNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.contractNumber).toContain('Contract Number');
  });

  it('requires contractNumber', () => {
    const values: Record<string, unknown> = { ...contractInput };
    delete values.contractNumber;
    const result = validateContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.contractNumber).toBeDefined();
  });

  it('requires startDate', () => {
    const values: Record<string, unknown> = { ...contractInput };
    delete values.startDate;
    const result = validateContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.startDate).toBeDefined();
  });

  it('rejects malformed date in startDate', () => {
    const result = validateContract({ ...contractInput, startDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in endDate', () => {
    const result = validateContract({ ...contractInput, endDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative termMonths', () => {
    const result = validateContract({ ...contractInput, termMonths: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.termMonths).toContain('negative');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateContract({ ...contractInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateContract({ ...contractInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateContract({ ...contractInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateContract({ ...contractInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateContract({ ...contractInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
