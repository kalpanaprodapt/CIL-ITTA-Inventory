import { describe, expect, it } from 'vitest';
import { cleanVirtualMachineValues, validateVirtualMachine } from '../schema';
import { virtualMachineInput } from './fixtures';

describe('virtualMachine schema', () => {
  it('accepts the fixture', () => {
    expect(validateVirtualMachine(virtualMachineInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanVirtualMachineValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...virtualMachineInput };
    delete values.code;
    const result = validateVirtualMachine(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...virtualMachineInput };
    delete values.name;
    const result = validateVirtualMachine(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...virtualMachineInput };
    delete values.status;
    const result = validateVirtualMachine(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires hypervisorId', () => {
    const values: Record<string, unknown> = { ...virtualMachineInput };
    delete values.hypervisorId;
    const result = validateVirtualMachine(values);
    expect(result.ok).toBe(false);
    expect(result.errors.hypervisorId).toBeDefined();
  });

  it('rejects malformed hypervisorId', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, hypervisorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.hypervisorId).toContain('UUID');
  });

  it('rejects vmName longer than 255', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, vmName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.vmName).toContain('Vm Name');
  });

  it('requires vmName', () => {
    const values: Record<string, unknown> = { ...virtualMachineInput };
    delete values.vmName;
    const result = validateVirtualMachine(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vmName).toBeDefined();
  });

  it('requires vcpu', () => {
    const values: Record<string, unknown> = { ...virtualMachineInput };
    delete values.vcpu;
    const result = validateVirtualMachine(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vcpu).toBeDefined();
  });

  it('rejects negative vcpu', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, vcpu: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.vcpu).toContain('negative');
  });

  it('requires memoryGb', () => {
    const values: Record<string, unknown> = { ...virtualMachineInput };
    delete values.memoryGb;
    const result = validateVirtualMachine(values);
    expect(result.ok).toBe(false);
    expect(result.errors.memoryGb).toBeDefined();
  });

  it('rejects negative memoryGb', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, memoryGb: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.memoryGb).toContain('negative');
  });

  it('rejects negative diskGb', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, diskGb: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.diskGb).toContain('negative');
  });

  it('rejects unknown powerState', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, powerState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown criticality', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateVirtualMachine({ ...virtualMachineInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
