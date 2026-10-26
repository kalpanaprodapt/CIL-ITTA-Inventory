import { describe, expect, it } from 'vitest';
import { cleanRadioUnitValues, validateRadioUnit } from '../schema';
import { radioUnitInput } from './fixtures';

describe('radioUnit schema', () => {
  it('accepts the fixture', () => {
    expect(validateRadioUnit(radioUnitInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanRadioUnitValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateRadioUnit({ ...radioUnitInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...radioUnitInput };
    delete values.code;
    const result = validateRadioUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateRadioUnit({ ...radioUnitInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...radioUnitInput };
    delete values.name;
    const result = validateRadioUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateRadioUnit({ ...radioUnitInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...radioUnitInput };
    delete values.status;
    const result = validateRadioUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateRadioUnit({ ...radioUnitInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateRadioUnit({ ...radioUnitInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires siteId', () => {
    const values: Record<string, unknown> = { ...radioUnitInput };
    delete values.siteId;
    const result = validateRadioUnit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.siteId).toBeDefined();
  });

  it('rejects malformed siteId', () => {
    const result = validateRadioUnit({ ...radioUnitInput, siteId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.siteId).toContain('UUID');
  });

  it('rejects unknown unitType', () => {
    const result = validateRadioUnit({ ...radioUnitInput, unitType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative bandMhz', () => {
    const result = validateRadioUnit({ ...radioUnitInput, bandMhz: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.bandMhz).toContain('negative');
  });

  it('rejects serialNumber longer than 255', () => {
    const result = validateRadioUnit({ ...radioUnitInput, serialNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.serialNumber).toContain('Serial Number');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateRadioUnit({ ...radioUnitInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateRadioUnit({ ...radioUnitInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateRadioUnit({ ...radioUnitInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateRadioUnit({ ...radioUnitInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateRadioUnit({ ...radioUnitInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
