import { describe, expect, it } from 'vitest';
import { cleanAntennaValues, validateAntenna } from '../schema';
import { antennaInput } from './fixtures';

describe('antenna schema', () => {
  it('accepts the fixture', () => {
    expect(validateAntenna(antennaInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanAntennaValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...antennaInput };
    delete values.code;
    const result = validateAntenna(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...antennaInput };
    delete values.name;
    const result = validateAntenna(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateAntenna({ ...antennaInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...antennaInput };
    delete values.status;
    const result = validateAntenna(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateAntenna({ ...antennaInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires sectorId', () => {
    const values: Record<string, unknown> = { ...antennaInput };
    delete values.sectorId;
    const result = validateAntenna(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sectorId).toBeDefined();
  });

  it('rejects malformed sectorId', () => {
    const result = validateAntenna({ ...antennaInput, sectorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.sectorId).toContain('UUID');
  });

  it('rejects model longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, model: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.model).toContain('Model');
  });

  it('rejects negative heightM', () => {
    const result = validateAntenna({ ...antennaInput, heightM: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.heightM).toContain('negative');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateAntenna({ ...antennaInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateAntenna({ ...antennaInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
