import { describe, expect, it } from 'vitest';
import { cleanWdmSystemValues, validateWdmSystem } from '../schema';
import { wdmSystemInput } from './fixtures';

describe('wdmSystem schema', () => {
  it('accepts the fixture', () => {
    expect(validateWdmSystem(wdmSystemInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanWdmSystemValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...wdmSystemInput };
    delete values.code;
    const result = validateWdmSystem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...wdmSystemInput };
    delete values.name;
    const result = validateWdmSystem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...wdmSystemInput };
    delete values.status;
    const result = validateWdmSystem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects systemName longer than 255', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, systemName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.systemName).toContain('System Name');
  });

  it('requires systemName', () => {
    const values: Record<string, unknown> = { ...wdmSystemInput };
    delete values.systemName;
    const result = validateWdmSystem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.systemName).toBeDefined();
  });

  it('requires channelCapacity', () => {
    const values: Record<string, unknown> = { ...wdmSystemInput };
    delete values.channelCapacity;
    const result = validateWdmSystem(values);
    expect(result.ok).toBe(false);
    expect(result.errors.channelCapacity).toBeDefined();
  });

  it('rejects negative channelCapacity', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, channelCapacity: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.channelCapacity).toContain('negative');
  });

  it('rejects unknown band', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, band: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative spanCount', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, spanCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.spanCount).toContain('negative');
  });

  it('rejects malformed vendorId', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, vendorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toContain('UUID');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateWdmSystem({ ...wdmSystemInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
