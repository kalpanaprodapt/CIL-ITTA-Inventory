import { describe, expect, it } from 'vitest';
import { cleanRackValues, validateRack } from '../schema';
import { rackInput } from './fixtures';

describe('rack schema', () => {
  it('accepts the fixture', () => {
    expect(validateRack(rackInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanRackValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateRack({ ...rackInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...rackInput };
    delete values.code;
    const result = validateRack(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateRack({ ...rackInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...rackInput };
    delete values.name;
    const result = validateRack(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateRack({ ...rackInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...rackInput };
    delete values.status;
    const result = validateRack(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateRack({ ...rackInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateRack({ ...rackInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires roomId', () => {
    const values: Record<string, unknown> = { ...rackInput };
    delete values.roomId;
    const result = validateRack(values);
    expect(result.ok).toBe(false);
    expect(result.errors.roomId).toBeDefined();
  });

  it('rejects malformed roomId', () => {
    const result = validateRack({ ...rackInput, roomId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.roomId).toContain('UUID');
  });

  it('requires rackUnits', () => {
    const values: Record<string, unknown> = { ...rackInput };
    delete values.rackUnits;
    const result = validateRack(values);
    expect(result.ok).toBe(false);
    expect(result.errors.rackUnits).toBeDefined();
  });

  it('rejects negative rackUnits', () => {
    const result = validateRack({ ...rackInput, rackUnits: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.rackUnits).toContain('negative');
  });

  it('rejects negative widthMm', () => {
    const result = validateRack({ ...rackInput, widthMm: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.widthMm).toContain('negative');
  });

  it('rejects negative depthMm', () => {
    const result = validateRack({ ...rackInput, depthMm: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.depthMm).toContain('negative');
  });

  it('rejects negative maxWeightKg', () => {
    const result = validateRack({ ...rackInput, maxWeightKg: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.maxWeightKg).toContain('negative');
  });

  it('rejects rowLabel longer than 255', () => {
    const result = validateRack({ ...rackInput, rowLabel: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.rowLabel).toContain('Row Label');
  });

  it('rejects position longer than 255', () => {
    const result = validateRack({ ...rackInput, position: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.position).toContain('Position');
  });

  it('rejects labels longer than 255', () => {
    const result = validateRack({ ...rackInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateRack({ ...rackInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateRack({ ...rackInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateRack({ ...rackInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateRack({ ...rackInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
