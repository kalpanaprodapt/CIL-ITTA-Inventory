import { describe, expect, it } from 'vitest';
import { cleanFanTrayValues, validateFanTray } from '../schema';
import { fanTrayInput } from './fixtures';

describe('fanTray schema', () => {
  it('accepts the fixture', () => {
    expect(validateFanTray(fanTrayInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanFanTrayValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateFanTray({ ...fanTrayInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...fanTrayInput };
    delete values.code;
    const result = validateFanTray(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateFanTray({ ...fanTrayInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...fanTrayInput };
    delete values.name;
    const result = validateFanTray(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateFanTray({ ...fanTrayInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...fanTrayInput };
    delete values.status;
    const result = validateFanTray(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateFanTray({ ...fanTrayInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateFanTray({ ...fanTrayInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires chassisId', () => {
    const values: Record<string, unknown> = { ...fanTrayInput };
    delete values.chassisId;
    const result = validateFanTray(values);
    expect(result.ok).toBe(false);
    expect(result.errors.chassisId).toBeDefined();
  });

  it('rejects malformed chassisId', () => {
    const result = validateFanTray({ ...fanTrayInput, chassisId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.chassisId).toContain('UUID');
  });

  it('rejects negative slotNumber', () => {
    const result = validateFanTray({ ...fanTrayInput, slotNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.slotNumber).toContain('negative');
  });

  it('rejects negative fanCount', () => {
    const result = validateFanTray({ ...fanTrayInput, fanCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.fanCount).toContain('negative');
  });

  it('rejects unknown airflow', () => {
    const result = validateFanTray({ ...fanTrayInput, airflow: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects serialNumber longer than 255', () => {
    const result = validateFanTray({ ...fanTrayInput, serialNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.serialNumber).toContain('Serial Number');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateFanTray({ ...fanTrayInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateFanTray({ ...fanTrayInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateFanTray({ ...fanTrayInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateFanTray({ ...fanTrayInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateFanTray({ ...fanTrayInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
