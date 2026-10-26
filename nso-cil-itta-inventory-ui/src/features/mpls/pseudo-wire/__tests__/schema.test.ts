import { describe, expect, it } from 'vitest';
import { cleanPseudoWireValues, validatePseudoWire } from '../schema';
import { pseudoWireInput } from './fixtures';

describe('pseudoWire schema', () => {
  it('accepts the fixture', () => {
    expect(validatePseudoWire(pseudoWireInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPseudoWireValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...pseudoWireInput };
    delete values.code;
    const result = validatePseudoWire(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...pseudoWireInput };
    delete values.name;
    const result = validatePseudoWire(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...pseudoWireInput };
    delete values.status;
    const result = validatePseudoWire(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires vcId', () => {
    const values: Record<string, unknown> = { ...pseudoWireInput };
    delete values.vcId;
    const result = validatePseudoWire(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vcId).toBeDefined();
  });

  it('rejects negative vcId', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, vcId: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.vcId).toContain('negative');
  });

  it('requires aEndId', () => {
    const values: Record<string, unknown> = { ...pseudoWireInput };
    delete values.aEndId;
    const result = validatePseudoWire(values);
    expect(result.ok).toBe(false);
    expect(result.errors.aEndId).toBeDefined();
  });

  it('rejects malformed aEndId', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, aEndId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.aEndId).toContain('UUID');
  });

  it('requires zEndId', () => {
    const values: Record<string, unknown> = { ...pseudoWireInput };
    delete values.zEndId;
    const result = validatePseudoWire(values);
    expect(result.ok).toBe(false);
    expect(result.errors.zEndId).toBeDefined();
  });

  it('rejects malformed zEndId', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, zEndId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.zEndId).toContain('UUID');
  });

  it('rejects encapsulation longer than 255', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, encapsulation: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.encapsulation).toContain('Encapsulation');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validatePseudoWire({ ...pseudoWireInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
