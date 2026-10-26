import { describe, expect, it } from 'vitest';
import { cleanPowerSupplyValues, validatePowerSupply } from '../schema';
import { powerSupplyInput } from './fixtures';

describe('powerSupply schema', () => {
  it('accepts the fixture', () => {
    expect(validatePowerSupply(powerSupplyInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPowerSupplyValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...powerSupplyInput };
    delete values.code;
    const result = validatePowerSupply(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...powerSupplyInput };
    delete values.name;
    const result = validatePowerSupply(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...powerSupplyInput };
    delete values.status;
    const result = validatePowerSupply(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires chassisId', () => {
    const values: Record<string, unknown> = { ...powerSupplyInput };
    delete values.chassisId;
    const result = validatePowerSupply(values);
    expect(result.ok).toBe(false);
    expect(result.errors.chassisId).toBeDefined();
  });

  it('rejects malformed chassisId', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, chassisId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.chassisId).toContain('UUID');
  });

  it('rejects negative slotNumber', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, slotNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.slotNumber).toContain('negative');
  });

  it('rejects negative wattage', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, wattage: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.wattage).toContain('negative');
  });

  it('rejects malformed feedId', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, feedId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.feedId).toContain('UUID');
  });

  it('rejects serialNumber longer than 255', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, serialNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.serialNumber).toContain('Serial Number');
  });

  it('rejects negative efficiency', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, efficiency: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.efficiency).toContain('negative');
  });

  it('rejects unknown criticality', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validatePowerSupply({ ...powerSupplyInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
