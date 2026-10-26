import { describe, expect, it } from 'vitest';
import { cleanAmplifierValues, validateAmplifier } from '../schema';
import { amplifierInput } from './fixtures';

describe('amplifier schema', () => {
  it('accepts the fixture', () => {
    expect(validateAmplifier(amplifierInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanAmplifierValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateAmplifier({ ...amplifierInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...amplifierInput };
    delete values.code;
    const result = validateAmplifier(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateAmplifier({ ...amplifierInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...amplifierInput };
    delete values.name;
    const result = validateAmplifier(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateAmplifier({ ...amplifierInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...amplifierInput };
    delete values.status;
    const result = validateAmplifier(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateAmplifier({ ...amplifierInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateAmplifier({ ...amplifierInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires systemId', () => {
    const values: Record<string, unknown> = { ...amplifierInput };
    delete values.systemId;
    const result = validateAmplifier(values);
    expect(result.ok).toBe(false);
    expect(result.errors.systemId).toBeDefined();
  });

  it('rejects malformed systemId', () => {
    const result = validateAmplifier({ ...amplifierInput, systemId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.systemId).toContain('UUID');
  });

  it('rejects unknown ampType', () => {
    const result = validateAmplifier({ ...amplifierInput, ampType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires gainDb', () => {
    const values: Record<string, unknown> = { ...amplifierInput };
    delete values.gainDb;
    const result = validateAmplifier(values);
    expect(result.ok).toBe(false);
    expect(result.errors.gainDb).toBeDefined();
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateAmplifier({ ...amplifierInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateAmplifier({ ...amplifierInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateAmplifier({ ...amplifierInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateAmplifier({ ...amplifierInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateAmplifier({ ...amplifierInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
