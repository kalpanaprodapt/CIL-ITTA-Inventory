import { describe, expect, it } from 'vitest';
import { cleanIpAddressValues, validateIpAddress } from '../schema';
import { ipAddressInput } from './fixtures';

describe('ipAddress schema', () => {
  it('accepts the fixture', () => {
    expect(validateIpAddress(ipAddressInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanIpAddressValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateIpAddress({ ...ipAddressInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...ipAddressInput };
    delete values.code;
    const result = validateIpAddress(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateIpAddress({ ...ipAddressInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...ipAddressInput };
    delete values.name;
    const result = validateIpAddress(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateIpAddress({ ...ipAddressInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...ipAddressInput };
    delete values.status;
    const result = validateIpAddress(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateIpAddress({ ...ipAddressInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateIpAddress({ ...ipAddressInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires subnetId', () => {
    const values: Record<string, unknown> = { ...ipAddressInput };
    delete values.subnetId;
    const result = validateIpAddress(values);
    expect(result.ok).toBe(false);
    expect(result.errors.subnetId).toBeDefined();
  });

  it('rejects malformed subnetId', () => {
    const result = validateIpAddress({ ...ipAddressInput, subnetId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.subnetId).toContain('UUID');
  });

  it('rejects address longer than 255', () => {
    const result = validateIpAddress({ ...ipAddressInput, address: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.address).toContain('Address');
  });

  it('requires address', () => {
    const values: Record<string, unknown> = { ...ipAddressInput };
    delete values.address;
    const result = validateIpAddress(values);
    expect(result.ok).toBe(false);
    expect(result.errors.address).toBeDefined();
  });

  it('rejects malformed assignedToId', () => {
    const result = validateIpAddress({ ...ipAddressInput, assignedToId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.assignedToId).toContain('UUID');
  });

  it('rejects unknown assignmentType', () => {
    const result = validateIpAddress({ ...ipAddressInput, assignmentType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects dnsName longer than 255', () => {
    const result = validateIpAddress({ ...ipAddressInput, dnsName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.dnsName).toContain('Dns Name');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateIpAddress({ ...ipAddressInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateIpAddress({ ...ipAddressInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateIpAddress({ ...ipAddressInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateIpAddress({ ...ipAddressInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateIpAddress({ ...ipAddressInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
