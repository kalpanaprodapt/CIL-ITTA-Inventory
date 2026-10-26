import { describe, expect, it } from 'vitest';
import { cleanMplsLspValues, validateMplsLsp } from '../schema';
import { mplsLspInput } from './fixtures';

describe('mplsLsp schema', () => {
  it('accepts the fixture', () => {
    expect(validateMplsLsp(mplsLspInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanMplsLspValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...mplsLspInput };
    delete values.code;
    const result = validateMplsLsp(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...mplsLspInput };
    delete values.name;
    const result = validateMplsLsp(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateMplsLsp({ ...mplsLspInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...mplsLspInput };
    delete values.status;
    const result = validateMplsLsp(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateMplsLsp({ ...mplsLspInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects lspName longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, lspName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.lspName).toContain('Lsp Name');
  });

  it('requires lspName', () => {
    const values: Record<string, unknown> = { ...mplsLspInput };
    delete values.lspName;
    const result = validateMplsLsp(values);
    expect(result.ok).toBe(false);
    expect(result.errors.lspName).toBeDefined();
  });

  it('requires headEndId', () => {
    const values: Record<string, unknown> = { ...mplsLspInput };
    delete values.headEndId;
    const result = validateMplsLsp(values);
    expect(result.ok).toBe(false);
    expect(result.errors.headEndId).toBeDefined();
  });

  it('rejects malformed headEndId', () => {
    const result = validateMplsLsp({ ...mplsLspInput, headEndId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.headEndId).toContain('UUID');
  });

  it('requires tailEndId', () => {
    const values: Record<string, unknown> = { ...mplsLspInput };
    delete values.tailEndId;
    const result = validateMplsLsp(values);
    expect(result.ok).toBe(false);
    expect(result.errors.tailEndId).toBeDefined();
  });

  it('rejects malformed tailEndId', () => {
    const result = validateMplsLsp({ ...mplsLspInput, tailEndId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.tailEndId).toContain('UUID');
  });

  it('rejects negative bandwidthMbps', () => {
    const result = validateMplsLsp({ ...mplsLspInput, bandwidthMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.bandwidthMbps).toContain('negative');
  });

  it('rejects negative setupPriority', () => {
    const result = validateMplsLsp({ ...mplsLspInput, setupPriority: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.setupPriority).toContain('negative');
  });

  it('rejects unknown pathType', () => {
    const result = validateMplsLsp({ ...mplsLspInput, pathType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateMplsLsp({ ...mplsLspInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateMplsLsp({ ...mplsLspInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
