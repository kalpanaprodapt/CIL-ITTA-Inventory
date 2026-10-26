import { describe, expect, it } from 'vitest';
import { cleanVrfInstanceValues, validateVrfInstance } from '../schema';
import { vrfInstanceInput } from './fixtures';

describe('vrfInstance schema', () => {
  it('accepts the fixture', () => {
    expect(validateVrfInstance(vrfInstanceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanVrfInstanceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...vrfInstanceInput };
    delete values.code;
    const result = validateVrfInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...vrfInstanceInput };
    delete values.name;
    const result = validateVrfInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...vrfInstanceInput };
    delete values.status;
    const result = validateVrfInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects vrfName longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, vrfName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.vrfName).toContain('Vrf Name');
  });

  it('requires vrfName', () => {
    const values: Record<string, unknown> = { ...vrfInstanceInput };
    delete values.vrfName;
    const result = validateVrfInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vrfName).toBeDefined();
  });

  it('rejects routeDistinguisher longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, routeDistinguisher: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.routeDistinguisher).toContain('Route Distinguisher');
  });

  it('requires routeDistinguisher', () => {
    const values: Record<string, unknown> = { ...vrfInstanceInput };
    delete values.routeDistinguisher;
    const result = validateVrfInstance(values);
    expect(result.ok).toBe(false);
    expect(result.errors.routeDistinguisher).toBeDefined();
  });

  it('rejects importTargets longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, importTargets: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.importTargets).toContain('Import Targets');
  });

  it('rejects exportTargets longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, exportTargets: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.exportTargets).toContain('Export Targets');
  });

  it('rejects malformed deviceId', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, deviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toContain('UUID');
  });

  it('rejects labels longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateVrfInstance({ ...vrfInstanceInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
