import { describe, expect, it } from 'vitest';
import { countTransceiverActiveFilters, parseTransceiverFilter, serializeTransceiverFilter, toTransceiverQuery } from '../filterState';

describe('transceiver filter state', () => {
  it('is empty by default', () => {
    expect(parseTransceiverFilter(new URLSearchParams())).toEqual({});
    expect(countTransceiverActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).externalRef).toBe('abc');
  });

  it('round trips portId', () => {
    const filter = { portId: '00000000-0000-4000-8000-000000000011' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
  });

  it('round trips formFactor selection', () => {
    const filter = { formFactor: ['SFP'] as never };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).formFactor).toBe('SFP');
  });

  it('round trips wavelengthNm range', () => {
    const filter = { wavelengthNmMin: 1, wavelengthNmMax: 9 };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).wavelengthNmMax).toBe(9);
  });

  it('round trips reachKm range', () => {
    const filter = { reachKmMin: 1, reachKmMax: 9 };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).reachKmMax).toBe(9);
  });

  it('round trips serialNumber', () => {
    const filter = { serialNumber: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).serialNumber).toBe('abc');
  });

  it('round trips vendorPart', () => {
    const filter = { vendorPart: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).vendorPart).toBe('abc');
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseTransceiverFilter(serializeTransceiverFilter(filter))).toEqual(filter);
    expect(toTransceiverQuery(filter).owner).toBe('abc');
  });
});
