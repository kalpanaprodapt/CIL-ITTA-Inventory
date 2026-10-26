import { describe, expect, it } from 'vitest';
import { cleanFiberStrandValues, validateFiberStrand } from '../schema';
import { fiberStrandInput } from './fixtures';

describe('fiberStrand schema', () => {
  it('accepts the fixture', () => {
    expect(validateFiberStrand(fiberStrandInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanFiberStrandValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...fiberStrandInput };
    delete values.code;
    const result = validateFiberStrand(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...fiberStrandInput };
    delete values.name;
    const result = validateFiberStrand(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...fiberStrandInput };
    delete values.status;
    const result = validateFiberStrand(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires cableId', () => {
    const values: Record<string, unknown> = { ...fiberStrandInput };
    delete values.cableId;
    const result = validateFiberStrand(values);
    expect(result.ok).toBe(false);
    expect(result.errors.cableId).toBeDefined();
  });

  it('rejects malformed cableId', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, cableId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.cableId).toContain('UUID');
  });

  it('requires strandNumber', () => {
    const values: Record<string, unknown> = { ...fiberStrandInput };
    delete values.strandNumber;
    const result = validateFiberStrand(values);
    expect(result.ok).toBe(false);
    expect(result.errors.strandNumber).toBeDefined();
  });

  it('rejects negative strandNumber', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, strandNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.strandNumber).toContain('negative');
  });

  it('rejects bufferTube longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, bufferTube: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.bufferTube).toContain('Buffer Tube');
  });

  it('rejects tubeColor longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, tubeColor: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.tubeColor).toContain('Tube Color');
  });

  it('rejects region longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateFiberStrand({ ...fiberStrandInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
