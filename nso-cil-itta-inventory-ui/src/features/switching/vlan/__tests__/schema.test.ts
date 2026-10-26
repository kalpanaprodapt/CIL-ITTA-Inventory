import { describe, expect, it } from 'vitest';
import { cleanVlanValues, validateVlan } from '../schema';
import { vlanInput } from './fixtures';

describe('vlan schema', () => {
  it('accepts the fixture', () => {
    expect(validateVlan(vlanInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanVlanValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateVlan({ ...vlanInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...vlanInput };
    delete values.code;
    const result = validateVlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateVlan({ ...vlanInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...vlanInput };
    delete values.name;
    const result = validateVlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateVlan({ ...vlanInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...vlanInput };
    delete values.status;
    const result = validateVlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateVlan({ ...vlanInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateVlan({ ...vlanInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires vlanId', () => {
    const values: Record<string, unknown> = { ...vlanInput };
    delete values.vlanId;
    const result = validateVlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vlanId).toBeDefined();
  });

  it('rejects negative vlanId', () => {
    const result = validateVlan({ ...vlanInput, vlanId: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.vlanId).toContain('negative');
  });

  it('rejects vlanName longer than 255', () => {
    const result = validateVlan({ ...vlanInput, vlanName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.vlanName).toContain('Vlan Name');
  });

  it('requires vlanName', () => {
    const values: Record<string, unknown> = { ...vlanInput };
    delete values.vlanName;
    const result = validateVlan(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vlanName).toBeDefined();
  });

  it('rejects unknown vlanType', () => {
    const result = validateVlan({ ...vlanInput, vlanType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed poolId', () => {
    const result = validateVlan({ ...vlanInput, poolId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.poolId).toContain('UUID');
  });

  it('rejects stpMode longer than 255', () => {
    const result = validateVlan({ ...vlanInput, stpMode: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.stpMode).toContain('Stp Mode');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateVlan({ ...vlanInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateVlan({ ...vlanInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateVlan({ ...vlanInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateVlan({ ...vlanInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateVlan({ ...vlanInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
