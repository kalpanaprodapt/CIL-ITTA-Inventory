import { describe, expect, it } from 'vitest';
import { cleanFieldTaskValues, validateFieldTask } from '../schema';
import { fieldTaskInput } from './fixtures';

describe('fieldTask schema', () => {
  it('accepts the fixture', () => {
    expect(validateFieldTask(fieldTaskInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanFieldTaskValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateFieldTask({ ...fieldTaskInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...fieldTaskInput };
    delete values.code;
    const result = validateFieldTask(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateFieldTask({ ...fieldTaskInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...fieldTaskInput };
    delete values.name;
    const result = validateFieldTask(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateFieldTask({ ...fieldTaskInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...fieldTaskInput };
    delete values.status;
    const result = validateFieldTask(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateFieldTask({ ...fieldTaskInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateFieldTask({ ...fieldTaskInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires workOrderId', () => {
    const values: Record<string, unknown> = { ...fieldTaskInput };
    delete values.workOrderId;
    const result = validateFieldTask(values);
    expect(result.ok).toBe(false);
    expect(result.errors.workOrderId).toBeDefined();
  });

  it('rejects malformed workOrderId', () => {
    const result = validateFieldTask({ ...fieldTaskInput, workOrderId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.workOrderId).toContain('UUID');
  });

  it('rejects taskName longer than 255', () => {
    const result = validateFieldTask({ ...fieldTaskInput, taskName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.taskName).toContain('Task Name');
  });

  it('requires taskName', () => {
    const values: Record<string, unknown> = { ...fieldTaskInput };
    delete values.taskName;
    const result = validateFieldTask(values);
    expect(result.ok).toBe(false);
    expect(result.errors.taskName).toBeDefined();
  });

  it('requires sequence', () => {
    const values: Record<string, unknown> = { ...fieldTaskInput };
    delete values.sequence;
    const result = validateFieldTask(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sequence).toBeDefined();
  });

  it('rejects negative sequence', () => {
    const result = validateFieldTask({ ...fieldTaskInput, sequence: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.sequence).toContain('negative');
  });

  it('rejects negative durationMinutes', () => {
    const result = validateFieldTask({ ...fieldTaskInput, durationMinutes: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.durationMinutes).toContain('negative');
  });

  it('rejects region longer than 255', () => {
    const result = validateFieldTask({ ...fieldTaskInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateFieldTask({ ...fieldTaskInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateFieldTask({ ...fieldTaskInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateFieldTask({ ...fieldTaskInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateFieldTask({ ...fieldTaskInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
