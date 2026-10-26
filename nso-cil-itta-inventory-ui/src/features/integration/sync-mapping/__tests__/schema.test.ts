import { describe, expect, it } from 'vitest';
import { cleanSyncMappingValues, validateSyncMapping } from '../schema';
import { syncMappingInput } from './fixtures';

describe('syncMapping schema', () => {
  it('accepts the fixture', () => {
    expect(validateSyncMapping(syncMappingInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSyncMappingValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...syncMappingInput };
    delete values.code;
    const result = validateSyncMapping(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...syncMappingInput };
    delete values.name;
    const result = validateSyncMapping(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSyncMapping({ ...syncMappingInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...syncMappingInput };
    delete values.status;
    const result = validateSyncMapping(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSyncMapping({ ...syncMappingInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires jobId', () => {
    const values: Record<string, unknown> = { ...syncMappingInput };
    delete values.jobId;
    const result = validateSyncMapping(values);
    expect(result.ok).toBe(false);
    expect(result.errors.jobId).toBeDefined();
  });

  it('rejects malformed jobId', () => {
    const result = validateSyncMapping({ ...syncMappingInput, jobId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.jobId).toContain('UUID');
  });

  it('rejects sourceField longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, sourceField: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceField).toContain('Source Field');
  });

  it('requires sourceField', () => {
    const values: Record<string, unknown> = { ...syncMappingInput };
    delete values.sourceField;
    const result = validateSyncMapping(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sourceField).toBeDefined();
  });

  it('rejects targetField longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, targetField: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.targetField).toContain('Target Field');
  });

  it('requires targetField', () => {
    const values: Record<string, unknown> = { ...syncMappingInput };
    delete values.targetField;
    const result = validateSyncMapping(values);
    expect(result.ok).toBe(false);
    expect(result.errors.targetField).toBeDefined();
  });

  it('rejects transform longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, transform: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.transform).toContain('Transform');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateSyncMapping({ ...syncMappingInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateSyncMapping({ ...syncMappingInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateSyncMapping({ ...syncMappingInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateSyncMapping({ ...syncMappingInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
