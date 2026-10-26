import { describe, expect, it } from 'vitest';
import { cleanAlarmInstanceValues, validateAlarmInstance } from '../schema';
import { alarmInstanceInput } from './fixtures';

describe('alarmInstance schema', () => {
  it('accepts the fixture', () => {
    expect(validateAlarmInstance(alarmInstanceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanAlarmInstanceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...alarmInstanceInput };
    delete values.code;
    const result = validateAlarmInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...alarmInstanceInput };
    delete values.name;
    const result = validateAlarmInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...alarmInstanceInput };
    delete values.status;
    const result = validateAlarmInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires definitionId', () => {
    const values: Record<string, unknown> = { ...alarmInstanceInput };
    delete values.definitionId;
    const result = validateAlarmInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.definitionId).toBeDefined();
  });

  it('rejects malformed definitionId', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, definitionId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.definitionId).toContain('UUID');
  });

  it('rejects sourceRef longer than 255', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, sourceRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceRef).toContain('Source Ref');
  });

  it('requires sourceRef', () => {
    const values: Record<string, unknown> = { ...alarmInstanceInput };
    delete values.sourceRef;
    const result = validateAlarmInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sourceRef).toBeDefined();
  });

  it('requires raisedAt', () => {
    const values: Record<string, unknown> = { ...alarmInstanceInput };
    delete values.raisedAt;
    const result = validateAlarmInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.raisedAt).toBeDefined();
  });

  it('rejects ackedBy longer than 255', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, ackedBy: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ackedBy).toContain('Acked By');
  });

  it('rejects unknown alarmState', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, alarmState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateAlarmInstance({ ...alarmInstanceInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
