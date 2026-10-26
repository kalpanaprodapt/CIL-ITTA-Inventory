import { describe, expect, it } from 'vitest';
import { cleanSlaProfileValues, validateSlaProfile } from '../schema';
import { slaProfileInput } from './fixtures';

describe('slaProfile schema', () => {
  it('accepts the fixture', () => {
    expect(validateSlaProfile(slaProfileInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSlaProfileValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSlaProfile({ ...slaProfileInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...slaProfileInput };
    delete values.code;
    const result = validateSlaProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSlaProfile({ ...slaProfileInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...slaProfileInput };
    delete values.name;
    const result = validateSlaProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSlaProfile({ ...slaProfileInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...slaProfileInput };
    delete values.status;
    const result = validateSlaProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSlaProfile({ ...slaProfileInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSlaProfile({ ...slaProfileInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects profileName longer than 255', () => {
    const result = validateSlaProfile({ ...slaProfileInput, profileName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.profileName).toContain('Profile Name');
  });

  it('requires profileName', () => {
    const values: Record<string, unknown> = { ...slaProfileInput };
    delete values.profileName;
    const result = validateSlaProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.profileName).toBeDefined();
  });

  it('requires availabilityPct', () => {
    const values: Record<string, unknown> = { ...slaProfileInput };
    delete values.availabilityPct;
    const result = validateSlaProfile(values);
    expect(result.ok).toBe(false);
    expect(result.errors.availabilityPct).toBeDefined();
  });

  it('rejects negative availabilityPct', () => {
    const result = validateSlaProfile({ ...slaProfileInput, availabilityPct: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.availabilityPct).toContain('negative');
  });

  it('rejects negative mttrMinutes', () => {
    const result = validateSlaProfile({ ...slaProfileInput, mttrMinutes: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.mttrMinutes).toContain('negative');
  });

  it('rejects negative latencyMs', () => {
    const result = validateSlaProfile({ ...slaProfileInput, latencyMs: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.latencyMs).toContain('negative');
  });

  it('rejects negative jitterMs', () => {
    const result = validateSlaProfile({ ...slaProfileInput, jitterMs: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.jitterMs).toContain('negative');
  });

  it('rejects creditPolicy longer than 4000', () => {
    const result = validateSlaProfile({ ...slaProfileInput, creditPolicy: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.creditPolicy).toContain('Credit Policy');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSlaProfile({ ...slaProfileInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSlaProfile({ ...slaProfileInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSlaProfile({ ...slaProfileInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateSlaProfile({ ...slaProfileInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateSlaProfile({ ...slaProfileInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
