import { describe, expect, it } from 'vitest';
import { countSoftwareImageActiveFilters, parseSoftwareImageFilter, serializeSoftwareImageFilter, toSoftwareImageQuery } from '../filterState';

describe('softwareImage filter state', () => {
  it('is empty by default', () => {
    expect(parseSoftwareImageFilter(new URLSearchParams())).toEqual({});
    expect(countSoftwareImageActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vendorId', () => {
    const filter = { vendorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
  });

  it('round trips imageName', () => {
    const filter = { imageName: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).imageName).toBe('abc');
  });

  it('round trips sizeMb range', () => {
    const filter = { sizeMbMin: 1, sizeMbMax: 9 };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).sizeMbMax).toBe(9);
  });

  it('round trips checksum', () => {
    const filter = { checksum: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).checksum).toBe('abc');
  });

  it('round trips platform', () => {
    const filter = { platform: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).platform).toBe('abc');
  });

  it('round trips storagePath', () => {
    const filter = { storagePath: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).storagePath).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSoftwareImageFilter(serializeSoftwareImageFilter(filter))).toEqual(filter);
    expect(toSoftwareImageQuery(filter).criticality).toBe('LOW');
  });
});
