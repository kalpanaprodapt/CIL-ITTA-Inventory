import { describe, expect, it } from 'vitest';
import { cleanSectorValues, validateSector } from '../schema';
import { sectorInput } from './fixtures';

describe('sector schema', () => {
  it('accepts the fixture', () => {
    expect(validateSector(sectorInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanSectorValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateSector({ ...sectorInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...sectorInput };
    delete values.code;
    const result = validateSector(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateSector({ ...sectorInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...sectorInput };
    delete values.name;
    const result = validateSector(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateSector({ ...sectorInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...sectorInput };
    delete values.status;
    const result = validateSector(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateSector({ ...sectorInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateSector({ ...sectorInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires baseStationId', () => {
    const values: Record<string, unknown> = { ...sectorInput };
    delete values.baseStationId;
    const result = validateSector(values);
    expect(result.ok).toBe(false);
    expect(result.errors.baseStationId).toBeDefined();
  });

  it('rejects malformed baseStationId', () => {
    const result = validateSector({ ...sectorInput, baseStationId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.baseStationId).toContain('UUID');
  });

  it('requires sectorNumber', () => {
    const values: Record<string, unknown> = { ...sectorInput };
    delete values.sectorNumber;
    const result = validateSector(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sectorNumber).toBeDefined();
  });

  it('rejects negative sectorNumber', () => {
    const result = validateSector({ ...sectorInput, sectorNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.sectorNumber).toContain('negative');
  });

  it('rejects negative pci', () => {
    const result = validateSector({ ...sectorInput, pci: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.pci).toContain('negative');
  });

  it('rejects negative frequencyMhz', () => {
    const result = validateSector({ ...sectorInput, frequencyMhz: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.frequencyMhz).toContain('negative');
  });

  it('rejects negative bandwidthMhz', () => {
    const result = validateSector({ ...sectorInput, bandwidthMhz: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.bandwidthMhz).toContain('negative');
  });

  it('rejects owner longer than 255', () => {
    const result = validateSector({ ...sectorInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateSector({ ...sectorInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateSector({ ...sectorInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateSector({ ...sectorInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateSector({ ...sectorInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
