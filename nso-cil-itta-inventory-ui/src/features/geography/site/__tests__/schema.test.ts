import { describe, expect, it } from 'vitest';
import { cleanSiteValues, validateSite } from '../schema';
import { siteInput } from './fixtures';

describe('site schema', () => {
  it('accepts the fixture', () => {
    expect(validateSite(siteInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSiteValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSite({ ...siteInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...siteInput };
    delete values.code;
    const result = validateSite(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSite({ ...siteInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...siteInput };
    delete values.name;
    const result = validateSite(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSite({ ...siteInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...siteInput };
    delete values.status;
    const result = validateSite(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSite({ ...siteInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSite({ ...siteInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires cityId', () => {
    const values: Record<string, unknown> = { ...siteInput };
    delete values.cityId;
    const result = validateSite(values);
    expect(result.ok).toBe(false);
    expect(result.errors.cityId).toBeDefined();
  });

  it('rejects malformed cityId', () => {
    const result = validateSite({ ...siteInput, cityId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.cityId).toContain('UUID');
  });

  it('rejects unknown siteType', () => {
    const result = validateSite({ ...siteInput, siteType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects address longer than 255', () => {
    const result = validateSite({ ...siteInput, address: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.address).toContain('Address');
  });

  it('rejects negative powerKw', () => {
    const result = validateSite({ ...siteInput, powerKw: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.powerKw).toContain('negative');
  });

  it('rejects accessHours longer than 255', () => {
    const result = validateSite({ ...siteInput, accessHours: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.accessHours).toContain('Access Hours');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateSite({ ...siteInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateSite({ ...siteInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateSite({ ...siteInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateSite({ ...siteInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateSite({ ...siteInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
