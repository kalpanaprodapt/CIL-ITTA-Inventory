import { describe, expect, it } from 'vitest';
import { countVirtualMachineActiveFilters, parseVirtualMachineFilter, serializeVirtualMachineFilter, toVirtualMachineQuery } from '../filterState';

describe('virtualMachine filter state', () => {
  it('is empty by default', () => {
    expect(parseVirtualMachineFilter(new URLSearchParams())).toEqual({});
    expect(countVirtualMachineActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).externalRef).toBe('abc');
  });

  it('round trips hypervisorId', () => {
    const filter = { hypervisorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
  });

  it('round trips vmName', () => {
    const filter = { vmName: 'abc' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).vmName).toBe('abc');
  });

  it('round trips vcpu range', () => {
    const filter = { vcpuMin: 1, vcpuMax: 9 };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).vcpuMax).toBe(9);
  });

  it('round trips memoryGb range', () => {
    const filter = { memoryGbMin: 1, memoryGbMax: 9 };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).memoryGbMax).toBe(9);
  });

  it('round trips diskGb range', () => {
    const filter = { diskGbMin: 1, diskGbMax: 9 };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).diskGbMax).toBe(9);
  });

  it('round trips powerState selection', () => {
    const filter = { powerState: ['ON'] as never };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).powerState).toBe('ON');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
    expect(toVirtualMachineQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseVirtualMachineFilter(serializeVirtualMachineFilter(filter))).toEqual(filter);
  });
});
