import { describe, expect, it } from 'vitest';
import { cleanBuildingValues, validateBuilding } from '../schema';
import { buildingInput } from './fixtures';

describe('building schema', () => {
  it('accepts the fixture', () => {
    expect(validateBuilding(buildingInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanBuildingValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateBuilding({ ...buildingInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...buildingInput };
    delete values.code;
    const result = validateBuilding(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateBuilding({ ...buildingInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...buildingInput };
    delete values.name;
    const result = validateBuilding(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateBuilding({ ...buildingInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...buildingInput };
    delete values.status;
    const result = validateBuilding(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateBuilding({ ...buildingInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateBuilding({ ...buildingInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires siteId', () => {
    const values: Record<string, unknown> = { ...buildingInput };
    delete values.siteId;
    const result = validateBuilding(values);
    expect(result.ok).toBe(false);
    expect(result.errors.siteId).toBeDefined();
  });

  it('rejects malformed siteId', () => {
    const result = validateBuilding({ ...buildingInput, siteId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.siteId).toContain('UUID');
  });

  it('rejects negative floors', () => {
    const result = validateBuilding({ ...buildingInput, floors: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.floors).toContain('negative');
  });

  it('rejects negative yearBuilt', () => {
    const result = validateBuilding({ ...buildingInput, yearBuilt: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.yearBuilt).toContain('negative');
  });

  it('rejects unknown ownership', () => {
    const result = validateBuilding({ ...buildingInput, ownership: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in leaseExpiry', () => {
    const result = validateBuilding({ ...buildingInput, leaseExpiry: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative securityLevel', () => {
    const result = validateBuilding({ ...buildingInput, securityLevel: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.securityLevel).toContain('negative');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateBuilding({ ...buildingInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateBuilding({ ...buildingInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateBuilding({ ...buildingInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateBuilding({ ...buildingInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateBuilding({ ...buildingInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
