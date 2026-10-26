import { describe, expect, it } from 'vitest';
import { cleanPatchValues, validatePatch } from '../schema';
import { patchInput } from './fixtures';

describe('patch schema', () => {
  it('accepts the fixture', () => {
    expect(validatePatch(patchInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPatchValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePatch({ ...patchInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...patchInput };
    delete values.code;
    const result = validatePatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePatch({ ...patchInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...patchInput };
    delete values.name;
    const result = validatePatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePatch({ ...patchInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...patchInput };
    delete values.status;
    const result = validatePatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePatch({ ...patchInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePatch({ ...patchInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires versionId', () => {
    const values: Record<string, unknown> = { ...patchInput };
    delete values.versionId;
    const result = validatePatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.versionId).toBeDefined();
  });

  it('rejects malformed versionId', () => {
    const result = validatePatch({ ...patchInput, versionId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.versionId).toContain('UUID');
  });

  it('rejects patchId longer than 255', () => {
    const result = validatePatch({ ...patchInput, patchId: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.patchId).toContain('Patch Id');
  });

  it('requires patchId', () => {
    const values: Record<string, unknown> = { ...patchInput };
    delete values.patchId;
    const result = validatePatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.patchId).toBeDefined();
  });

  it('rejects unknown severity', () => {
    const result = validatePatch({ ...patchInput, severity: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in releasedOn', () => {
    const result = validatePatch({ ...patchInput, releasedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown criticality', () => {
    const result = validatePatch({ ...patchInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validatePatch({ ...patchInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validatePatch({ ...patchInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validatePatch({ ...patchInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validatePatch({ ...patchInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
