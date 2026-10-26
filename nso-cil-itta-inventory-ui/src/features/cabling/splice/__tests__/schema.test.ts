import { describe, expect, it } from 'vitest';
import { cleanSpliceValues, validateSplice } from '../schema';
import { spliceInput } from './fixtures';

describe('splice schema', () => {
  it('accepts the fixture', () => {
    expect(validateSplice(spliceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSpliceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSplice({ ...spliceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...spliceInput };
    delete values.code;
    const result = validateSplice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSplice({ ...spliceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...spliceInput };
    delete values.name;
    const result = validateSplice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSplice({ ...spliceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...spliceInput };
    delete values.status;
    const result = validateSplice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSplice({ ...spliceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSplice({ ...spliceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects enclosureId longer than 255', () => {
    const result = validateSplice({ ...spliceInput, enclosureId: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.enclosureId).toContain('Enclosure Id');
  });

  it('requires enclosureId', () => {
    const values: Record<string, unknown> = { ...spliceInput };
    delete values.enclosureId;
    const result = validateSplice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.enclosureId).toBeDefined();
  });

  it('rejects malformed aStrandId', () => {
    const result = validateSplice({ ...spliceInput, aStrandId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.aStrandId).toContain('UUID');
  });

  it('rejects malformed zStrandId', () => {
    const result = validateSplice({ ...spliceInput, zStrandId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.zStrandId).toContain('UUID');
  });

  it('rejects malformed date in splicedOn', () => {
    const result = validateSplice({ ...spliceInput, splicedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateSplice({ ...spliceInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateSplice({ ...spliceInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateSplice({ ...spliceInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSplice({ ...spliceInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSplice({ ...spliceInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
