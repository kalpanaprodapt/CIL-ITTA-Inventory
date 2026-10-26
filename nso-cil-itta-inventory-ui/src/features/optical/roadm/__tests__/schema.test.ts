import { describe, expect, it } from 'vitest';
import { cleanRoadmValues, validateRoadm } from '../schema';
import { roadmInput } from './fixtures';

describe('roadm schema', () => {
  it('accepts the fixture', () => {
    expect(validateRoadm(roadmInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanRoadmValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateRoadm({ ...roadmInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...roadmInput };
    delete values.code;
    const result = validateRoadm(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateRoadm({ ...roadmInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...roadmInput };
    delete values.name;
    const result = validateRoadm(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateRoadm({ ...roadmInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...roadmInput };
    delete values.status;
    const result = validateRoadm(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateRoadm({ ...roadmInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateRoadm({ ...roadmInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires systemId', () => {
    const values: Record<string, unknown> = { ...roadmInput };
    delete values.systemId;
    const result = validateRoadm(values);
    expect(result.ok).toBe(false);
    expect(result.errors.systemId).toBeDefined();
  });

  it('rejects malformed systemId', () => {
    const result = validateRoadm({ ...roadmInput, systemId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.systemId).toContain('UUID');
  });

  it('requires degrees', () => {
    const values: Record<string, unknown> = { ...roadmInput };
    delete values.degrees;
    const result = validateRoadm(values);
    expect(result.ok).toBe(false);
    expect(result.errors.degrees).toBeDefined();
  });

  it('rejects negative degrees', () => {
    const result = validateRoadm({ ...roadmInput, degrees: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.degrees).toContain('negative');
  });

  it('rejects region longer than 255', () => {
    const result = validateRoadm({ ...roadmInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateRoadm({ ...roadmInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateRoadm({ ...roadmInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateRoadm({ ...roadmInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateRoadm({ ...roadmInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
