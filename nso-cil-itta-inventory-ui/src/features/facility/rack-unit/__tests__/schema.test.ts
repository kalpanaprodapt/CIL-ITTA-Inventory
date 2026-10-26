import { describe, expect, it } from 'vitest';
import { cleanRackUnitValues, validateRackUnit } from '../schema';
import { rackUnitInput } from './fixtures';

describe('rackUnit schema', () => {
  it('accepts the fixture', () => {
    expect(validateRackUnit(rackUnitInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanRackUnitValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateRackUnit({ ...rackUnitInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...rackUnitInput };
    delete values.code;
    const result = validateRackUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateRackUnit({ ...rackUnitInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...rackUnitInput };
    delete values.name;
    const result = validateRackUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateRackUnit({ ...rackUnitInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...rackUnitInput };
    delete values.status;
    const result = validateRackUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateRackUnit({ ...rackUnitInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateRackUnit({ ...rackUnitInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires rackId', () => {
    const values: Record<string, unknown> = { ...rackUnitInput };
    delete values.rackId;
    const result = validateRackUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.rackId).toBeDefined();
  });

  it('rejects malformed rackId', () => {
    const result = validateRackUnit({ ...rackUnitInput, rackId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.rackId).toContain('UUID');
  });

  it('requires unitNumber', () => {
    const values: Record<string, unknown> = { ...rackUnitInput };
    delete values.unitNumber;
    const result = validateRackUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.unitNumber).toBeDefined();
  });

  it('rejects negative unitNumber', () => {
    const result = validateRackUnit({ ...rackUnitInput, unitNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.unitNumber).toContain('negative');
  });

  it('rejects malformed mountedDeviceId', () => {
    const result = validateRackUnit({ ...rackUnitInput, mountedDeviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.mountedDeviceId).toContain('UUID');
  });

  it('rejects unknown face', () => {
    const result = validateRackUnit({ ...rackUnitInput, face: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateRackUnit({ ...rackUnitInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateRackUnit({ ...rackUnitInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateRackUnit({ ...rackUnitInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateRackUnit({ ...rackUnitInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateRackUnit({ ...rackUnitInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
