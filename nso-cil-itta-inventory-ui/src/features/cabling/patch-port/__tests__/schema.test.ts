import { describe, expect, it } from 'vitest';
import { cleanPatchPortValues, validatePatchPort } from '../schema';
import { patchPortInput } from './fixtures';

describe('patchPort schema', () => {
  it('accepts the fixture', () => {
    expect(validatePatchPort(patchPortInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPatchPortValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePatchPort({ ...patchPortInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...patchPortInput };
    delete values.code;
    const result = validatePatchPort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePatchPort({ ...patchPortInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...patchPortInput };
    delete values.name;
    const result = validatePatchPort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePatchPort({ ...patchPortInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...patchPortInput };
    delete values.status;
    const result = validatePatchPort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePatchPort({ ...patchPortInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePatchPort({ ...patchPortInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires panelId', () => {
    const values: Record<string, unknown> = { ...patchPortInput };
    delete values.panelId;
    const result = validatePatchPort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.panelId).toBeDefined();
  });

  it('rejects malformed panelId', () => {
    const result = validatePatchPort({ ...patchPortInput, panelId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.panelId).toContain('UUID');
  });

  it('requires portNumber', () => {
    const values: Record<string, unknown> = { ...patchPortInput };
    delete values.portNumber;
    const result = validatePatchPort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.portNumber).toBeDefined();
  });

  it('rejects negative portNumber', () => {
    const result = validatePatchPort({ ...patchPortInput, portNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.portNumber).toContain('negative');
  });

  it('rejects malformed connectedCableId', () => {
    const result = validatePatchPort({ ...patchPortInput, connectedCableId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.connectedCableId).toContain('UUID');
  });

  it('rejects polarity longer than 255', () => {
    const result = validatePatchPort({ ...patchPortInput, polarity: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.polarity).toContain('Polarity');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validatePatchPort({ ...patchPortInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validatePatchPort({ ...patchPortInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validatePatchPort({ ...patchPortInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validatePatchPort({ ...patchPortInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validatePatchPort({ ...patchPortInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
