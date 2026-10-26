import { describe, expect, it } from 'vitest';
import { cleanDiscoveryProfileValues, validateDiscoveryProfile } from '../schema';
import { discoveryProfileInput } from './fixtures';

describe('discoveryProfile schema', () => {
  it('accepts the fixture', () => {
    expect(validateDiscoveryProfile(discoveryProfileInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanDiscoveryProfileValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...discoveryProfileInput };
    delete values.code;
    const result = validateDiscoveryProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...discoveryProfileInput };
    delete values.name;
    const result = validateDiscoveryProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...discoveryProfileInput };
    delete values.status;
    const result = validateDiscoveryProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects profileName longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, profileName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.profileName).toContain('Profile Name');
  });

  it('requires profileName', () => {
    const values: Record<string, unknown> = { ...discoveryProfileInput };
    delete values.profileName;
    const result = validateDiscoveryProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.profileName).toBeDefined();
  });

  it('rejects unknown protocol', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, protocol: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects credentialRef longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, credentialRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.credentialRef).toContain('Credential Ref');
  });

  it('rejects scopeCidr longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, scopeCidr: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.scopeCidr).toContain('Scope Cidr');
  });

  it('rejects scheduleCron longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, scheduleCron: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.scheduleCron).toContain('Schedule Cron');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateDiscoveryProfile({ ...discoveryProfileInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
