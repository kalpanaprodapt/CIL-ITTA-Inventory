import { describe, expect, it } from 'vitest';
import { cleanSoftwareVersionValues, validateSoftwareVersion } from '../schema';
import { softwareVersionInput } from './fixtures';

describe('softwareVersion schema', () => {
  it('accepts the fixture', () => {
    expect(validateSoftwareVersion(softwareVersionInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSoftwareVersionValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...softwareVersionInput };
    delete values.code;
    const result = validateSoftwareVersion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...softwareVersionInput };
    delete values.name;
    const result = validateSoftwareVersion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...softwareVersionInput };
    delete values.status;
    const result = validateSoftwareVersion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires imageId', () => {
    const values: Record<string, unknown> = { ...softwareVersionInput };
    delete values.imageId;
    const result = validateSoftwareVersion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.imageId).toBeDefined();
  });

  it('rejects malformed imageId', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, imageId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.imageId).toContain('UUID');
  });

  it('rejects versionString longer than 255', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, versionString: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.versionString).toContain('Version String');
  });

  it('requires versionString', () => {
    const values: Record<string, unknown> = { ...softwareVersionInput };
    delete values.versionString;
    const result = validateSoftwareVersion(values);
    expect(result.ok).toBe(false);
    expect(result.errors.versionString).toBeDefined();
  });

  it('rejects malformed date in releaseDate', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, releaseDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in endOfSupport', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, endOfSupport: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown releaseTrain', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, releaseTrain: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative cveCount', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, cveCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.cveCount).toContain('negative');
  });

  it('rejects region longer than 255', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateSoftwareVersion({ ...softwareVersionInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
