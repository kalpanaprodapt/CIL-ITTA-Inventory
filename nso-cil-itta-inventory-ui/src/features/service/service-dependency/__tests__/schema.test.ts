import { describe, expect, it } from 'vitest';
import { cleanServiceDependencyValues, validateServiceDependency } from '../schema';
import { serviceDependencyInput } from './fixtures';

describe('serviceDependency schema', () => {
  it('accepts the fixture', () => {
    expect(validateServiceDependency(serviceDependencyInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanServiceDependencyValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...serviceDependencyInput };
    delete values.code;
    const result = validateServiceDependency(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...serviceDependencyInput };
    delete values.name;
    const result = validateServiceDependency(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...serviceDependencyInput };
    delete values.status;
    const result = validateServiceDependency(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires serviceId', () => {
    const values: Record<string, unknown> = { ...serviceDependencyInput };
    delete values.serviceId;
    const result = validateServiceDependency(values);
    expect(result.ok).toBe(false);
    expect(result.errors.serviceId).toBeDefined();
  });

  it('rejects malformed serviceId', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, serviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.serviceId).toContain('UUID');
  });

  it('requires dependsOnId', () => {
    const values: Record<string, unknown> = { ...serviceDependencyInput };
    delete values.dependsOnId;
    const result = validateServiceDependency(values);
    expect(result.ok).toBe(false);
    expect(result.errors.dependsOnId).toBeDefined();
  });

  it('rejects malformed dependsOnId', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, dependsOnId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.dependsOnId).toContain('UUID');
  });

  it('rejects unknown dependencyType', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, dependencyType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative impactScore', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, impactScore: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.impactScore).toContain('negative');
  });

  it('rejects unknown criticality', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateServiceDependency({ ...serviceDependencyInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
