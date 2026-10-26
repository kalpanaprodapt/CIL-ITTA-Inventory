import { describe, expect, it } from 'vitest';
import { cleanImportBatchValues, validateImportBatch } from '../schema';
import { importBatchInput } from './fixtures';

describe('importBatch schema', () => {
  it('accepts the fixture', () => {
    expect(validateImportBatch(importBatchInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanImportBatchValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateImportBatch({ ...importBatchInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...importBatchInput };
    delete values.code;
    const result = validateImportBatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateImportBatch({ ...importBatchInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...importBatchInput };
    delete values.name;
    const result = validateImportBatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateImportBatch({ ...importBatchInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...importBatchInput };
    delete values.status;
    const result = validateImportBatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateImportBatch({ ...importBatchInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateImportBatch({ ...importBatchInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects fileName longer than 255', () => {
    const result = validateImportBatch({ ...importBatchInput, fileName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.fileName).toContain('File Name');
  });

  it('requires fileName', () => {
    const values: Record<string, unknown> = { ...importBatchInput };
    delete values.fileName;
    const result = validateImportBatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.fileName).toBeDefined();
  });

  it('rejects entityType longer than 255', () => {
    const result = validateImportBatch({ ...importBatchInput, entityType: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.entityType).toContain('Entity Type');
  });

  it('requires entityType', () => {
    const values: Record<string, unknown> = { ...importBatchInput };
    delete values.entityType;
    const result = validateImportBatch(values);
    expect(result.ok).toBe(false);
    expect(result.errors.entityType).toBeDefined();
  });

  it('rejects negative totalRows', () => {
    const result = validateImportBatch({ ...importBatchInput, totalRows: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.totalRows).toContain('negative');
  });

  it('rejects negative processedRows', () => {
    const result = validateImportBatch({ ...importBatchInput, processedRows: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.processedRows).toContain('negative');
  });

  it('rejects negative failedRows', () => {
    const result = validateImportBatch({ ...importBatchInput, failedRows: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.failedRows).toContain('negative');
  });

  it('rejects unknown batchState', () => {
    const result = validateImportBatch({ ...importBatchInput, batchState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateImportBatch({ ...importBatchInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateImportBatch({ ...importBatchInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateImportBatch({ ...importBatchInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateImportBatch({ ...importBatchInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateImportBatch({ ...importBatchInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
