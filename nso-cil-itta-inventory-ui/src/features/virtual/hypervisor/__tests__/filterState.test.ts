import { describe, expect, it } from 'vitest';
import { countHypervisorActiveFilters, parseHypervisorFilter, serializeHypervisorFilter, toHypervisorQuery } from '../filterState';

describe('hypervisor filter state', () => {
  it('is empty by default', () => {
    expect(parseHypervisorFilter(new URLSearchParams())).toEqual({});
    expect(countHypervisorActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).externalRef).toBe('abc');
  });

  it('round trips hostDeviceId', () => {
    const filter = { hostDeviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
  });

  it('round trips hypervisorType selection', () => {
    const filter = { hypervisorType: ['KVM'] as never };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).hypervisorType).toBe('KVM');
  });

  it('round trips hvVersion', () => {
    const filter = { hvVersion: 'abc' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).hvVersion).toBe('abc');
  });

  it('round trips vcpuCapacity range', () => {
    const filter = { vcpuCapacityMin: 1, vcpuCapacityMax: 9 };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).vcpuCapacityMax).toBe(9);
  });

  it('round trips memoryGbCapacity range', () => {
    const filter = { memoryGbCapacityMin: 1, memoryGbCapacityMax: 9 };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).memoryGbCapacityMax).toBe(9);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseHypervisorFilter(serializeHypervisorFilter(filter))).toEqual(filter);
    expect(toHypervisorQuery(filter).costCenter).toBe('abc');
  });
});
