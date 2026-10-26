import { describe, expect, it } from 'vitest';
import { cleanAlarmDefinitionValues, validateAlarmDefinition } from '../schema';
import { alarmDefinitionInput } from './fixtures';

describe('alarmDefinition schema', () => {
  it('accepts the fixture', () => {
    expect(validateAlarmDefinition(alarmDefinitionInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanAlarmDefinitionValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...alarmDefinitionInput };
    delete values.code;
    const result = validateAlarmDefinition(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...alarmDefinitionInput };
    delete values.name;
    const result = validateAlarmDefinition(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...alarmDefinitionInput };
    delete values.status;
    const result = validateAlarmDefinition(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects alarmCode longer than 255', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, alarmCode: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.alarmCode).toContain('Alarm Code');
  });

  it('requires alarmCode', () => {
    const values: Record<string, unknown> = { ...alarmDefinitionInput };
    delete values.alarmCode;
    const result = validateAlarmDefinition(values);
    expect(result.ok).toBe(false);
    expect(result.errors.alarmCode).toBeDefined();
  });

  it('rejects unknown severity', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, severity: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects probableCause longer than 255', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, probableCause: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.probableCause).toContain('Probable Cause');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateAlarmDefinition({ ...alarmDefinitionInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
