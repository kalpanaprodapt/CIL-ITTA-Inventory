import { describe, expect, it } from 'vitest';
import { cleanSyncJobValues, validateSyncJob } from '../schema';
import { syncJobInput } from './fixtures';

describe('syncJob schema', () => {
  it('accepts the fixture', () => {
    expect(validateSyncJob(syncJobInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSyncJobValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSyncJob({ ...syncJobInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...syncJobInput };
    delete values.code;
    const result = validateSyncJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSyncJob({ ...syncJobInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...syncJobInput };
    delete values.name;
    const result = validateSyncJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSyncJob({ ...syncJobInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...syncJobInput };
    delete values.status;
    const result = validateSyncJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSyncJob({ ...syncJobInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSyncJob({ ...syncJobInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires endpointId', () => {
    const values: Record<string, unknown> = { ...syncJobInput };
    delete values.endpointId;
    const result = validateSyncJob(values);
    expect(result.ok).toBe(false);
    expect(result.errors.endpointId).toBeDefined();
  });

  it('rejects malformed endpointId', () => {
    const result = validateSyncJob({ ...syncJobInput, endpointId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.endpointId).toContain('UUID');
  });

  it('rejects unknown direction', () => {
    const result = validateSyncJob({ ...syncJobInput, direction: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects scheduleCron longer than 255', () => {
    const result = validateSyncJob({ ...syncJobInput, scheduleCron: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.scheduleCron).toContain('Schedule Cron');
  });

  it('rejects unknown lastStatus', () => {
    const result = validateSyncJob({ ...syncJobInput, lastStatus: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects owner longer than 255', () => {
    const result = validateSyncJob({ ...syncJobInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSyncJob({ ...syncJobInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSyncJob({ ...syncJobInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateSyncJob({ ...syncJobInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateSyncJob({ ...syncJobInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
