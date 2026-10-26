import { describe, expect, it } from 'vitest';
import { cleanOspfAreaValues, validateOspfArea } from '../schema';
import { ospfAreaInput } from './fixtures';

describe('ospfArea schema', () => {
  it('accepts the fixture', () => {
    expect(validateOspfArea(ospfAreaInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanOspfAreaValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateOspfArea({ ...ospfAreaInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...ospfAreaInput };
    delete values.code;
    const result = validateOspfArea(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateOspfArea({ ...ospfAreaInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...ospfAreaInput };
    delete values.name;
    const result = validateOspfArea(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateOspfArea({ ...ospfAreaInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...ospfAreaInput };
    delete values.status;
    const result = validateOspfArea(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateOspfArea({ ...ospfAreaInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateOspfArea({ ...ospfAreaInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects areaId longer than 255', () => {
    const result = validateOspfArea({ ...ospfAreaInput, areaId: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.areaId).toContain('Area Id');
  });

  it('requires areaId', () => {
    const values: Record<string, unknown> = { ...ospfAreaInput };
    delete values.areaId;
    const result = validateOspfArea(values);
    expect(result.ok).toBe(false);
    expect(result.errors.areaId).toBeDefined();
  });

  it('rejects unknown areaType', () => {
    const result = validateOspfArea({ ...ospfAreaInput, areaType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative deviceCount', () => {
    const result = validateOspfArea({ ...ospfAreaInput, deviceCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.deviceCount).toContain('negative');
  });

  it('rejects authType longer than 255', () => {
    const result = validateOspfArea({ ...ospfAreaInput, authType: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.authType).toContain('Auth Type');
  });

  it('rejects unknown criticality', () => {
    const result = validateOspfArea({ ...ospfAreaInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateOspfArea({ ...ospfAreaInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateOspfArea({ ...ospfAreaInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateOspfArea({ ...ospfAreaInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateOspfArea({ ...ospfAreaInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
