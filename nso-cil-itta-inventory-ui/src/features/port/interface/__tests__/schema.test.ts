import { describe, expect, it } from 'vitest';
import { cleanInterfaceValues, validateInterface } from '../schema';
import { interfaceInput } from './fixtures';

describe('interface schema', () => {
  it('accepts the fixture', () => {
    expect(validateInterface(interfaceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanInterfaceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateInterface({ ...interfaceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...interfaceInput };
    delete values.code;
    const result = validateInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateInterface({ ...interfaceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...interfaceInput };
    delete values.name;
    const result = validateInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateInterface({ ...interfaceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...interfaceInput };
    delete values.status;
    const result = validateInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateInterface({ ...interfaceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateInterface({ ...interfaceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires portId', () => {
    const values: Record<string, unknown> = { ...interfaceInput };
    delete values.portId;
    const result = validateInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.portId).toBeDefined();
  });

  it('rejects malformed portId', () => {
    const result = validateInterface({ ...interfaceInput, portId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.portId).toContain('UUID');
  });

  it('rejects ifName longer than 255', () => {
    const result = validateInterface({ ...interfaceInput, ifName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ifName).toContain('If Name');
  });

  it('requires ifName', () => {
    const values: Record<string, unknown> = { ...interfaceInput };
    delete values.ifName;
    const result = validateInterface(values);
    expect(result.ok).toBe(false);
    expect(result.errors.ifName).toBeDefined();
  });

  it('rejects negative ifIndex', () => {
    const result = validateInterface({ ...interfaceInput, ifIndex: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.ifIndex).toContain('negative');
  });

  it('rejects macAddress longer than 255', () => {
    const result = validateInterface({ ...interfaceInput, macAddress: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.macAddress).toContain('Mac Address');
  });

  it('rejects negative mtu', () => {
    const result = validateInterface({ ...interfaceInput, mtu: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.mtu).toContain('negative');
  });

  it('rejects unknown operState', () => {
    const result = validateInterface({ ...interfaceInput, operState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects owner longer than 255', () => {
    const result = validateInterface({ ...interfaceInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateInterface({ ...interfaceInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateInterface({ ...interfaceInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateInterface({ ...interfaceInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateInterface({ ...interfaceInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
