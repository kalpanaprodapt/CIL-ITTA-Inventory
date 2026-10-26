import { describe, expect, it } from 'vitest';
import { countVnfActiveFilters, parseVnfFilter, serializeVnfFilter, toVnfQuery } from '../filterState';

describe('vnf filter state', () => {
  it('is empty by default', () => {
    expect(parseVnfFilter(new URLSearchParams())).toEqual({});
    expect(countVnfActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vnfName', () => {
    const filter = { vnfName: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).vnfName).toBe('abc');
  });

  it('round trips vnfType', () => {
    const filter = { vnfType: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).vnfType).toBe('abc');
  });

  it('round trips flavor', () => {
    const filter = { flavor: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).flavor).toBe('abc');
  });

  it('round trips vcpu range', () => {
    const filter = { vcpuMin: 1, vcpuMax: 9 };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).vcpuMax).toBe(9);
  });

  it('round trips memoryGb range', () => {
    const filter = { memoryGbMin: 1, memoryGbMax: 9 };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).memoryGbMax).toBe(9);
  });

  it('round trips instanceCount range', () => {
    const filter = { instanceCountMin: 1, instanceCountMax: 9 };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).instanceCountMax).toBe(9);
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseVnfFilter(serializeVnfFilter(filter))).toEqual(filter);
    expect(toVnfQuery(filter).criticality).toBe('LOW');
  });
});
