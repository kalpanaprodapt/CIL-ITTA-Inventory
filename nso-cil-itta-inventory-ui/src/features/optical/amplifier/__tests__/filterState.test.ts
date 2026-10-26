import { describe, expect, it } from 'vitest';
import { countAmplifierActiveFilters, parseAmplifierFilter, serializeAmplifierFilter, toAmplifierQuery } from '../filterState';

describe('amplifier filter state', () => {
  it('is empty by default', () => {
    expect(parseAmplifierFilter(new URLSearchParams())).toEqual({});
    expect(countAmplifierActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).externalRef).toBe('abc');
  });

  it('round trips systemId', () => {
    const filter = { systemId: '00000000-0000-4000-8000-000000000011' };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
  });

  it('round trips ampType selection', () => {
    const filter = { ampType: ['EDFA'] as never };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).ampType).toBe('EDFA');
  });

  it('round trips gainDb range', () => {
    const filter = { gainDbMin: 1, gainDbMax: 9 };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).gainDbMax).toBe(9);
  });

  it('round trips outputPowerDbm range', () => {
    const filter = { outputPowerDbmMin: 1, outputPowerDbmMax: 9 };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).outputPowerDbmMax).toBe(9);
  });

  it('round trips noiseFigureDb range', () => {
    const filter = { noiseFigureDbMin: 1, noiseFigureDbMax: 9 };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).noiseFigureDbMax).toBe(9);
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseAmplifierFilter(serializeAmplifierFilter(filter))).toEqual(filter);
    expect(toAmplifierQuery(filter).criticality).toBe('LOW');
  });
});
