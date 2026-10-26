import { describe, expect, it } from 'vitest';
import { cleanTechnicianValues, validateTechnician } from '../schema';
import { technicianInput } from './fixtures';

describe('technician schema', () => {
  it('accepts the fixture', () => {
    expect(validateTechnician(technicianInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanTechnicianValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateTechnician({ ...technicianInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...technicianInput };
    delete values.code;
    const result = validateTechnician(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateTechnician({ ...technicianInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...technicianInput };
    delete values.name;
    const result = validateTechnician(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateTechnician({ ...technicianInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...technicianInput };
    delete values.status;
    const result = validateTechnician(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateTechnician({ ...technicianInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateTechnician({ ...technicianInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects employeeId longer than 255', () => {
    const result = validateTechnician({ ...technicianInput, employeeId: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.employeeId).toContain('Employee Id');
  });

  it('requires employeeId', () => {
    const values: Record<string, unknown> = { ...technicianInput };
    delete values.employeeId;
    const result = validateTechnician(values);
    expect(result.ok).toBe(false);
    expect(result.errors.employeeId).toBeDefined();
  });

  it('rejects fullName longer than 255', () => {
    const result = validateTechnician({ ...technicianInput, fullName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.fullName).toContain('Full Name');
  });

  it('requires fullName', () => {
    const values: Record<string, unknown> = { ...technicianInput };
    delete values.fullName;
    const result = validateTechnician(values);
    expect(result.ok).toBe(false);
    expect(result.errors.fullName).toBeDefined();
  });

  it('rejects skillSet longer than 255', () => {
    const result = validateTechnician({ ...technicianInput, skillSet: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.skillSet).toContain('Skill Set');
  });

  it('rejects malformed homeSiteId', () => {
    const result = validateTechnician({ ...technicianInput, homeSiteId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.homeSiteId).toContain('UUID');
  });

  it('rejects negative certificationLevel', () => {
    const result = validateTechnician({ ...technicianInput, certificationLevel: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.certificationLevel).toContain('negative');
  });

  it('rejects unknown criticality', () => {
    const result = validateTechnician({ ...technicianInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateTechnician({ ...technicianInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateTechnician({ ...technicianInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateTechnician({ ...technicianInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateTechnician({ ...technicianInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
