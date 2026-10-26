import { describe, expect, it } from 'vitest';
import { cleanRoomValues, validateRoom } from '../schema';
import { roomInput } from './fixtures';

describe('room schema', () => {
  it('accepts the fixture', () => {
    expect(validateRoom(roomInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanRoomValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateRoom({ ...roomInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...roomInput };
    delete values.code;
    const result = validateRoom(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateRoom({ ...roomInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...roomInput };
    delete values.name;
    const result = validateRoom(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateRoom({ ...roomInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...roomInput };
    delete values.status;
    const result = validateRoom(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateRoom({ ...roomInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateRoom({ ...roomInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires floorId', () => {
    const values: Record<string, unknown> = { ...roomInput };
    delete values.floorId;
    const result = validateRoom(values);
    expect(result.ok).toBe(false);
    expect(result.errors.floorId).toBeDefined();
  });

  it('rejects malformed floorId', () => {
    const result = validateRoom({ ...roomInput, floorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.floorId).toContain('UUID');
  });

  it('rejects unknown roomType', () => {
    const result = validateRoom({ ...roomInput, roomType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative areaSqm', () => {
    const result = validateRoom({ ...roomInput, areaSqm: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.areaSqm).toContain('negative');
  });

  it('rejects negative coolingTons', () => {
    const result = validateRoom({ ...roomInput, coolingTons: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.coolingTons).toContain('negative');
  });

  it('rejects fireZone longer than 255', () => {
    const result = validateRoom({ ...roomInput, fireZone: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.fireZone).toContain('Fire Zone');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateRoom({ ...roomInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateRoom({ ...roomInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateRoom({ ...roomInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateRoom({ ...roomInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateRoom({ ...roomInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
