import { describe, expect, it } from 'vitest';
import { cleanSoftwareImageValues, validateSoftwareImage } from '../schema';
import { softwareImageInput } from './fixtures';

describe('softwareImage schema', () => {
  it('accepts the fixture', () => {
    expect(validateSoftwareImage(softwareImageInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSoftwareImageValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...softwareImageInput };
    delete values.code;
    const result = validateSoftwareImage(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...softwareImageInput };
    delete values.name;
    const result = validateSoftwareImage(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...softwareImageInput };
    delete values.status;
    const result = validateSoftwareImage(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires vendorId', () => {
    const values: Record<string, unknown> = { ...softwareImageInput };
    delete values.vendorId;
    const result = validateSoftwareImage(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toBeDefined();
  });

  it('rejects malformed vendorId', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, vendorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toContain('UUID');
  });

  it('rejects imageName longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, imageName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.imageName).toContain('Image Name');
  });

  it('requires imageName', () => {
    const values: Record<string, unknown> = { ...softwareImageInput };
    delete values.imageName;
    const result = validateSoftwareImage(values);
    expect(result.ok).toBe(false);
    expect(result.errors.imageName).toBeDefined();
  });

  it('rejects negative sizeMb', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, sizeMb: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.sizeMb).toContain('negative');
  });

  it('rejects checksum longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, checksum: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.checksum).toContain('Checksum');
  });

  it('rejects platform longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, platform: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.platform).toContain('Platform');
  });

  it('rejects storagePath longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, storagePath: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.storagePath).toContain('Storage Path');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSoftwareImage({ ...softwareImageInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
