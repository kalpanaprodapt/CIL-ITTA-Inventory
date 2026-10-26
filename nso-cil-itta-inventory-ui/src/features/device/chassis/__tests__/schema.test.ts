import { describe, expect, it } from 'vitest';
import { cleanChassisValues, validateChassis } from '../schema';
import { chassisInput } from './fixtures';

describe('chassis schema', () => {
  it('accepts the fixture', () => {
    expect(validateChassis(chassisInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanChassisValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateChassis({ ...chassisInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...chassisInput };
    delete values.code;
    const result = validateChassis(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateChassis({ ...chassisInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...chassisInput };
    delete values.name;
    const result = validateChassis(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateChassis({ ...chassisInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...chassisInput };
    delete values.status;
    const result = validateChassis(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateChassis({ ...chassisInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateChassis({ ...chassisInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires deviceId', () => {
    const values: Record<string, unknown> = { ...chassisInput };
    delete values.deviceId;
    const result = validateChassis(values);
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toBeDefined();
  });

  it('rejects malformed deviceId', () => {
    const result = validateChassis({ ...chassisInput, deviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toContain('UUID');
  });

  it('requires slotCount', () => {
    const values: Record<string, unknown> = { ...chassisInput };
    delete values.slotCount;
    const result = validateChassis(values);
    expect(result.ok).toBe(false);
    expect(result.errors.slotCount).toBeDefined();
  });

  it('rejects negative slotCount', () => {
    const result = validateChassis({ ...chassisInput, slotCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.slotCount).toContain('negative');
  });

  it('rejects serialNumber longer than 255', () => {
    const result = validateChassis({ ...chassisInput, serialNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.serialNumber).toContain('Serial Number');
  });

  it('rejects negative backplaneGbps', () => {
    const result = validateChassis({ ...chassisInput, backplaneGbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.backplaneGbps).toContain('negative');
  });

  it('rejects redundancyMode longer than 255', () => {
    const result = validateChassis({ ...chassisInput, redundancyMode: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.redundancyMode).toContain('Redundancy Mode');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateChassis({ ...chassisInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateChassis({ ...chassisInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateChassis({ ...chassisInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateChassis({ ...chassisInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateChassis({ ...chassisInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
