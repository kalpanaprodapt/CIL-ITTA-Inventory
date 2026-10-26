import { describe, expect, it } from 'vitest';
import { countOpticalChannelActiveFilters, parseOpticalChannelFilter, serializeOpticalChannelFilter, toOpticalChannelQuery } from '../filterState';

describe('opticalChannel filter state', () => {
  it('is empty by default', () => {
    expect(parseOpticalChannelFilter(new URLSearchParams())).toEqual({});
    expect(countOpticalChannelActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).externalRef).toBe('abc');
  });

  it('round trips wavelengthNm range', () => {
    const filter = { wavelengthNmMin: 1, wavelengthNmMax: 9 };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).wavelengthNmMax).toBe(9);
  });

  it('round trips channelNumber range', () => {
    const filter = { channelNumberMin: 1, channelNumberMax: 9 };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).channelNumberMax).toBe(9);
  });

  it('round trips gridType selection', () => {
    const filter = { gridType: ['FIXED'] as never };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).gridType).toBe('FIXED');
  });

  it('round trips bitrateGbps range', () => {
    const filter = { bitrateGbpsMin: 1, bitrateGbpsMax: 9 };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).bitrateGbpsMax).toBe(9);
  });

  it('round trips systemId', () => {
    const filter = { systemId: '00000000-0000-4000-8000-000000000011' };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
    expect(toOpticalChannelQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseOpticalChannelFilter(serializeOpticalChannelFilter(filter))).toEqual(filter);
  });
});
