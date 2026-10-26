import { describe, expect, it } from 'vitest';
import { cleanHardwareModelValues, validateHardwareModel } from '../schema';
import { hardwareModelInput } from './fixtures';

describe('hardwareModel schema', () => {
  it('accepts the fixture', () => {
    expect(validateHardwareModel(hardwareModelInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanHardwareModelValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...hardwareModelInput };
    delete values.code;
    const result = validateHardwareModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...hardwareModelInput };
    delete values.name;
    const result = validateHardwareModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...hardwareModelInput };
    delete values.status;
    const result = validateHardwareModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires vendorId', () => {
    const values: Record<string, unknown> = { ...hardwareModelInput };
    delete values.vendorId;
    const result = validateHardwareModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toBeDefined();
  });

  it('rejects malformed vendorId', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, vendorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toContain('UUID');
  });

  it('rejects modelNumber longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, modelNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.modelNumber).toContain('Model Number');
  });

  it('requires modelNumber', () => {
    const values: Record<string, unknown> = { ...hardwareModelInput };
    delete values.modelNumber;
    const result = validateHardwareModel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.modelNumber).toBeDefined();
  });

  it('rejects formFactor longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, formFactor: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.formFactor).toContain('Form Factor');
  });

  it('rejects negative powerWatts', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, powerWatts: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.powerWatts).toContain('negative');
  });

  it('rejects malformed date in eolDate', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, eolDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in eosDate', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, eosDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateHardwareModel({ ...hardwareModelInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
