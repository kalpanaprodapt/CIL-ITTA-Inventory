import { describe, expect, it } from 'vitest';
import { cleanFloorValues, validateFloor } from '../schema';
import { floorInput } from './fixtures';

describe('floor schema', () => {
  it('accepts the fixture', () => {
    expect(validateFloor(floorInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanFloorValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateFloor({ ...floorInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...floorInput };
    delete values.code;
    const result = validateFloor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateFloor({ ...floorInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...floorInput };
    delete values.name;
    const result = validateFloor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateFloor({ ...floorInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...floorInput };
    delete values.status;
    const result = validateFloor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateFloor({ ...floorInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateFloor({ ...floorInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires buildingId', () => {
    const values: Record<string, unknown> = { ...floorInput };
    delete values.buildingId;
    const result = validateFloor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.buildingId).toBeDefined();
  });

  it('rejects malformed buildingId', () => {
    const result = validateFloor({ ...floorInput, buildingId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.buildingId).toContain('UUID');
  });

  it('rejects negative areaSqm', () => {
    const result = validateFloor({ ...floorInput, areaSqm: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.areaSqm).toContain('negative');
  });

  it('rejects negative loadCapacityKg', () => {
    const result = validateFloor({ ...floorInput, loadCapacityKg: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.loadCapacityKg).toContain('negative');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateFloor({ ...floorInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateFloor({ ...floorInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateFloor({ ...floorInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateFloor({ ...floorInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateFloor({ ...floorInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
