import { describe, expect, it } from 'vitest';
import { cleanCableValues, validateCable } from '../schema';
import { cableInput } from './fixtures';

describe('cable schema', () => {
  it('accepts the fixture', () => {
    expect(validateCable(cableInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCableValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCable({ ...cableInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...cableInput };
    delete values.code;
    const result = validateCable(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCable({ ...cableInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...cableInput };
    delete values.name;
    const result = validateCable(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCable({ ...cableInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...cableInput };
    delete values.status;
    const result = validateCable(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCable({ ...cableInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCable({ ...cableInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects unknown cableType', () => {
    const result = validateCable({ ...cableInput, cableType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative lengthM', () => {
    const result = validateCable({ ...cableInput, lengthM: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.lengthM).toContain('negative');
  });

  it('rejects malformed aEndPortId', () => {
    const result = validateCable({ ...cableInput, aEndPortId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.aEndPortId).toContain('UUID');
  });

  it('rejects malformed zEndPortId', () => {
    const result = validateCable({ ...cableInput, zEndPortId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.zEndPortId).toContain('UUID');
  });

  it('rejects color longer than 255', () => {
    const result = validateCable({ ...cableInput, color: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.color).toContain('Color');
  });

  it('rejects label longer than 255', () => {
    const result = validateCable({ ...cableInput, label: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.label).toContain('Label');
  });

  it('rejects unknown criticality', () => {
    const result = validateCable({ ...cableInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateCable({ ...cableInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateCable({ ...cableInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCable({ ...cableInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCable({ ...cableInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
