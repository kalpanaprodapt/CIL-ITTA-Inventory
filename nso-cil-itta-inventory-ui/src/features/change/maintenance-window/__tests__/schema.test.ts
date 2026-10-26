import { describe, expect, it } from 'vitest';
import { cleanMaintenanceWindowValues, validateMaintenanceWindow } from '../schema';
import { maintenanceWindowInput } from './fixtures';

describe('maintenanceWindow schema', () => {
  it('accepts the fixture', () => {
    expect(validateMaintenanceWindow(maintenanceWindowInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanMaintenanceWindowValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...maintenanceWindowInput };
    delete values.code;
    const result = validateMaintenanceWindow(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...maintenanceWindowInput };
    delete values.name;
    const result = validateMaintenanceWindow(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...maintenanceWindowInput };
    delete values.status;
    const result = validateMaintenanceWindow(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects windowName longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, windowName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.windowName).toContain('Window Name');
  });

  it('requires windowName', () => {
    const values: Record<string, unknown> = { ...maintenanceWindowInput };
    delete values.windowName;
    const result = validateMaintenanceWindow(values);
    expect(result.ok).toBe(false);
    expect(result.errors.windowName).toBeDefined();
  });

  it('requires startsAt', () => {
    const values: Record<string, unknown> = { ...maintenanceWindowInput };
    delete values.startsAt;
    const result = validateMaintenanceWindow(values);
    expect(result.ok).toBe(false);
    expect(result.errors.startsAt).toBeDefined();
  });

  it('requires endsAt', () => {
    const values: Record<string, unknown> = { ...maintenanceWindowInput };
    delete values.endsAt;
    const result = validateMaintenanceWindow(values);
    expect(result.ok).toBe(false);
    expect(result.errors.endsAt).toBeDefined();
  });

  it('rejects scope longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, scope: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.scope).toContain('Scope');
  });

  it('rejects unknown impactLevel', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, impactLevel: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateMaintenanceWindow({ ...maintenanceWindowInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
