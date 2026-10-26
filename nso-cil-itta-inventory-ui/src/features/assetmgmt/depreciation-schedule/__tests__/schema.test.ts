import { describe, expect, it } from 'vitest';
import { cleanDepreciationScheduleValues, validateDepreciationSchedule } from '../schema';
import { depreciationScheduleInput } from './fixtures';

describe('depreciationSchedule schema', () => {
  it('accepts the fixture', () => {
    expect(validateDepreciationSchedule(depreciationScheduleInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanDepreciationScheduleValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...depreciationScheduleInput };
    delete values.code;
    const result = validateDepreciationSchedule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...depreciationScheduleInput };
    delete values.name;
    const result = validateDepreciationSchedule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...depreciationScheduleInput };
    delete values.status;
    const result = validateDepreciationSchedule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires assetId', () => {
    const values: Record<string, unknown> = { ...depreciationScheduleInput };
    delete values.assetId;
    const result = validateDepreciationSchedule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.assetId).toBeDefined();
  });

  it('rejects malformed assetId', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, assetId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.assetId).toContain('UUID');
  });

  it('rejects unknown method', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, method: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires usefulLifeMonths', () => {
    const values: Record<string, unknown> = { ...depreciationScheduleInput };
    delete values.usefulLifeMonths;
    const result = validateDepreciationSchedule(values);
    expect(result.ok).toBe(false);
    expect(result.errors.usefulLifeMonths).toBeDefined();
  });

  it('rejects negative usefulLifeMonths', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, usefulLifeMonths: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.usefulLifeMonths).toContain('negative');
  });

  it('rejects negative salvageValue', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, salvageValue: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.salvageValue).toContain('negative');
  });

  it('rejects malformed date in startDate', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, startDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateDepreciationSchedule({ ...depreciationScheduleInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
