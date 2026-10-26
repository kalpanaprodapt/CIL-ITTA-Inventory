import { describe, expect, it } from 'vitest';
import { cleanLagGroupValues, validateLagGroup } from '../schema';
import { lagGroupInput } from './fixtures';

describe('lagGroup schema', () => {
  it('accepts the fixture', () => {
    expect(validateLagGroup(lagGroupInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanLagGroupValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateLagGroup({ ...lagGroupInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...lagGroupInput };
    delete values.code;
    const result = validateLagGroup(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateLagGroup({ ...lagGroupInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...lagGroupInput };
    delete values.name;
    const result = validateLagGroup(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateLagGroup({ ...lagGroupInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...lagGroupInput };
    delete values.status;
    const result = validateLagGroup(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateLagGroup({ ...lagGroupInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateLagGroup({ ...lagGroupInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires deviceId', () => {
    const values: Record<string, unknown> = { ...lagGroupInput };
    delete values.deviceId;
    const result = validateLagGroup(values);
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toBeDefined();
  });

  it('rejects malformed deviceId', () => {
    const result = validateLagGroup({ ...lagGroupInput, deviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toContain('UUID');
  });

  it('rejects lagName longer than 255', () => {
    const result = validateLagGroup({ ...lagGroupInput, lagName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.lagName).toContain('Lag Name');
  });

  it('requires lagName', () => {
    const values: Record<string, unknown> = { ...lagGroupInput };
    delete values.lagName;
    const result = validateLagGroup(values);
    expect(result.ok).toBe(false);
    expect(result.errors.lagName).toBeDefined();
  });

  it('rejects negative memberCount', () => {
    const result = validateLagGroup({ ...lagGroupInput, memberCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.memberCount).toContain('negative');
  });

  it('rejects unknown lacpMode', () => {
    const result = validateLagGroup({ ...lagGroupInput, lacpMode: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative minLinks', () => {
    const result = validateLagGroup({ ...lagGroupInput, minLinks: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.minLinks).toContain('negative');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateLagGroup({ ...lagGroupInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateLagGroup({ ...lagGroupInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateLagGroup({ ...lagGroupInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateLagGroup({ ...lagGroupInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateLagGroup({ ...lagGroupInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
