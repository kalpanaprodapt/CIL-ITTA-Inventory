import { describe, expect, it } from 'vitest';
import { cleanSupportContractValues, validateSupportContract } from '../schema';
import { supportContractInput } from './fixtures';

describe('supportContract schema', () => {
  it('accepts the fixture', () => {
    expect(validateSupportContract(supportContractInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSupportContractValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSupportContract({ ...supportContractInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...supportContractInput };
    delete values.code;
    const result = validateSupportContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSupportContract({ ...supportContractInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...supportContractInput };
    delete values.name;
    const result = validateSupportContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSupportContract({ ...supportContractInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...supportContractInput };
    delete values.status;
    const result = validateSupportContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSupportContract({ ...supportContractInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSupportContract({ ...supportContractInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires vendorId', () => {
    const values: Record<string, unknown> = { ...supportContractInput };
    delete values.vendorId;
    const result = validateSupportContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toBeDefined();
  });

  it('rejects malformed vendorId', () => {
    const result = validateSupportContract({ ...supportContractInput, vendorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toContain('UUID');
  });

  it('rejects contractNumber longer than 255', () => {
    const result = validateSupportContract({ ...supportContractInput, contractNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.contractNumber).toContain('Contract Number');
  });

  it('requires contractNumber', () => {
    const values: Record<string, unknown> = { ...supportContractInput };
    delete values.contractNumber;
    const result = validateSupportContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.contractNumber).toBeDefined();
  });

  it('requires startDate', () => {
    const values: Record<string, unknown> = { ...supportContractInput };
    delete values.startDate;
    const result = validateSupportContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.startDate).toBeDefined();
  });

  it('rejects malformed date in startDate', () => {
    const result = validateSupportContract({ ...supportContractInput, startDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('requires endDate', () => {
    const values: Record<string, unknown> = { ...supportContractInput };
    delete values.endDate;
    const result = validateSupportContract(values);
    expect(result.ok).toBe(false);
    expect(result.errors.endDate).toBeDefined();
  });

  it('rejects malformed date in endDate', () => {
    const result = validateSupportContract({ ...supportContractInput, endDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown coverageLevel', () => {
    const result = validateSupportContract({ ...supportContractInput, coverageLevel: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative annualCost', () => {
    const result = validateSupportContract({ ...supportContractInput, annualCost: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.annualCost).toContain('negative');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSupportContract({ ...supportContractInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSupportContract({ ...supportContractInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSupportContract({ ...supportContractInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateSupportContract({ ...supportContractInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateSupportContract({ ...supportContractInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
