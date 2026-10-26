import { describe, expect, it } from 'vitest';
import { cleanIpSubnetValues, validateIpSubnet } from '../schema';
import { ipSubnetInput } from './fixtures';

describe('ipSubnet schema', () => {
  it('accepts the fixture', () => {
    expect(validateIpSubnet(ipSubnetInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanIpSubnetValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...ipSubnetInput };
    delete values.code;
    const result = validateIpSubnet(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...ipSubnetInput };
    delete values.name;
    const result = validateIpSubnet(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...ipSubnetInput };
    delete values.status;
    const result = validateIpSubnet(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects cidr longer than 255', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, cidr: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.cidr).toContain('Cidr');
  });

  it('requires cidr', () => {
    const values: Record<string, unknown> = { ...ipSubnetInput };
    delete values.cidr;
    const result = validateIpSubnet(values);
    expect(result.ok).toBe(false);
    expect(result.errors.cidr).toBeDefined();
  });

  it('rejects unknown ipVersion', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, ipVersion: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed vrfId', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, vrfId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vrfId).toContain('UUID');
  });

  it('rejects gateway longer than 255', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, gateway: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.gateway).toContain('Gateway');
  });

  it('rejects malformed vlanId', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, vlanId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vlanId).toContain('UUID');
  });

  it('rejects negative utilizationPct', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, utilizationPct: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.utilizationPct).toContain('negative');
  });

  it('rejects owner longer than 255', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateIpSubnet({ ...ipSubnetInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
