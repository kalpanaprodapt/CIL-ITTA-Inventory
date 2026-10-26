import { describe, expect, it } from 'vitest';
import { cleanBaseStationValues, validateBaseStation } from '../schema';
import { baseStationInput } from './fixtures';

describe('baseStation schema', () => {
  it('accepts the fixture', () => {
    expect(validateBaseStation(baseStationInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanBaseStationValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateBaseStation({ ...baseStationInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...baseStationInput };
    delete values.code;
    const result = validateBaseStation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateBaseStation({ ...baseStationInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...baseStationInput };
    delete values.name;
    const result = validateBaseStation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateBaseStation({ ...baseStationInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...baseStationInput };
    delete values.status;
    const result = validateBaseStation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateBaseStation({ ...baseStationInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateBaseStation({ ...baseStationInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires siteId', () => {
    const values: Record<string, unknown> = { ...baseStationInput };
    delete values.siteId;
    const result = validateBaseStation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.siteId).toBeDefined();
  });

  it('rejects malformed siteId', () => {
    const result = validateBaseStation({ ...baseStationInput, siteId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.siteId).toContain('UUID');
  });

  it('requires enbId', () => {
    const values: Record<string, unknown> = { ...baseStationInput };
    delete values.enbId;
    const result = validateBaseStation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.enbId).toBeDefined();
  });

  it('rejects negative enbId', () => {
    const result = validateBaseStation({ ...baseStationInput, enbId: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.enbId).toContain('negative');
  });

  it('rejects unknown technology', () => {
    const result = validateBaseStation({ ...baseStationInput, technology: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative sectorCount', () => {
    const result = validateBaseStation({ ...baseStationInput, sectorCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.sectorCount).toContain('negative');
  });

  it('rejects negative cellCount', () => {
    const result = validateBaseStation({ ...baseStationInput, cellCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.cellCount).toContain('negative');
  });

  it('rejects labels longer than 255', () => {
    const result = validateBaseStation({ ...baseStationInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateBaseStation({ ...baseStationInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateBaseStation({ ...baseStationInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateBaseStation({ ...baseStationInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateBaseStation({ ...baseStationInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
