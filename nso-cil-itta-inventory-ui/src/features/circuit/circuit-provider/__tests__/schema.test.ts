import { describe, expect, it } from 'vitest';
import { cleanCircuitProviderValues, validateCircuitProvider } from '../schema';
import { circuitProviderInput } from './fixtures';

describe('circuitProvider schema', () => {
  it('accepts the fixture', () => {
    expect(validateCircuitProvider(circuitProviderInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCircuitProviderValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...circuitProviderInput };
    delete values.code;
    const result = validateCircuitProvider(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...circuitProviderInput };
    delete values.name;
    const result = validateCircuitProvider(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...circuitProviderInput };
    delete values.status;
    const result = validateCircuitProvider(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects providerName longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, providerName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.providerName).toContain('Provider Name');
  });

  it('requires providerName', () => {
    const values: Record<string, unknown> = { ...circuitProviderInput };
    delete values.providerName;
    const result = validateCircuitProvider(values);
    expect(result.ok).toBe(false);
    expect(result.errors.providerName).toBeDefined();
  });

  it('rejects accountNumber longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, accountNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.accountNumber).toContain('Account Number');
  });

  it('rejects noc longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, noc: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.noc).toContain('Noc');
  });

  it('rejects escalationEmail longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, escalationEmail: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.escalationEmail).toContain('Escalation Email');
  });

  it('rejects unknown providerType', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, providerType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects owner longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCircuitProvider({ ...circuitProviderInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
