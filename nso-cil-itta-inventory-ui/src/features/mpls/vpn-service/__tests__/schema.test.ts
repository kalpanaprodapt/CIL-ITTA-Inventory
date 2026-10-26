import { describe, expect, it } from 'vitest';
import { cleanVpnServiceValues, validateVpnService } from '../schema';
import { vpnServiceInput } from './fixtures';

describe('vpnService schema', () => {
  it('accepts the fixture', () => {
    expect(validateVpnService(vpnServiceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanVpnServiceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateVpnService({ ...vpnServiceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...vpnServiceInput };
    delete values.code;
    const result = validateVpnService(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateVpnService({ ...vpnServiceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...vpnServiceInput };
    delete values.name;
    const result = validateVpnService(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateVpnService({ ...vpnServiceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...vpnServiceInput };
    delete values.status;
    const result = validateVpnService(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateVpnService({ ...vpnServiceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateVpnService({ ...vpnServiceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects vpnName longer than 255', () => {
    const result = validateVpnService({ ...vpnServiceInput, vpnName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.vpnName).toContain('Vpn Name');
  });

  it('requires vpnName', () => {
    const values: Record<string, unknown> = { ...vpnServiceInput };
    delete values.vpnName;
    const result = validateVpnService(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vpnName).toBeDefined();
  });

  it('rejects unknown vpnType', () => {
    const result = validateVpnService({ ...vpnServiceInput, vpnType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed vrfId', () => {
    const result = validateVpnService({ ...vpnServiceInput, vrfId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vrfId).toContain('UUID');
  });

  it('rejects malformed customerId', () => {
    const result = validateVpnService({ ...vpnServiceInput, customerId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toContain('UUID');
  });

  it('rejects unknown topology', () => {
    const result = validateVpnService({ ...vpnServiceInput, topology: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects owner longer than 255', () => {
    const result = validateVpnService({ ...vpnServiceInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateVpnService({ ...vpnServiceInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateVpnService({ ...vpnServiceInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateVpnService({ ...vpnServiceInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateVpnService({ ...vpnServiceInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
