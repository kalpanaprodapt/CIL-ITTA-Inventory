import { describe, expect, it } from 'vitest';
import { cleanHypervisorValues, validateHypervisor } from '../schema';
import { hypervisorInput } from './fixtures';

describe('hypervisor schema', () => {
  it('accepts the fixture', () => {
    expect(validateHypervisor(hypervisorInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanHypervisorValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateHypervisor({ ...hypervisorInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...hypervisorInput };
    delete values.code;
    const result = validateHypervisor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateHypervisor({ ...hypervisorInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...hypervisorInput };
    delete values.name;
    const result = validateHypervisor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateHypervisor({ ...hypervisorInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...hypervisorInput };
    delete values.status;
    const result = validateHypervisor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateHypervisor({ ...hypervisorInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateHypervisor({ ...hypervisorInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires hostDeviceId', () => {
    const values: Record<string, unknown> = { ...hypervisorInput };
    delete values.hostDeviceId;
    const result = validateHypervisor(values);
    expect(result.ok).toBe(false);
    expect(result.errors.hostDeviceId).toBeDefined();
  });

  it('rejects malformed hostDeviceId', () => {
    const result = validateHypervisor({ ...hypervisorInput, hostDeviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.hostDeviceId).toContain('UUID');
  });

  it('rejects unknown hypervisorType', () => {
    const result = validateHypervisor({ ...hypervisorInput, hypervisorType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects hvVersion longer than 255', () => {
    const result = validateHypervisor({ ...hypervisorInput, hvVersion: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.hvVersion).toContain('Hv Version');
  });

  it('rejects negative vcpuCapacity', () => {
    const result = validateHypervisor({ ...hypervisorInput, vcpuCapacity: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.vcpuCapacity).toContain('negative');
  });

  it('rejects negative memoryGbCapacity', () => {
    const result = validateHypervisor({ ...hypervisorInput, memoryGbCapacity: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.memoryGbCapacity).toContain('negative');
  });

  it('rejects region longer than 255', () => {
    const result = validateHypervisor({ ...hypervisorInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateHypervisor({ ...hypervisorInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateHypervisor({ ...hypervisorInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateHypervisor({ ...hypervisorInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateHypervisor({ ...hypervisorInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
