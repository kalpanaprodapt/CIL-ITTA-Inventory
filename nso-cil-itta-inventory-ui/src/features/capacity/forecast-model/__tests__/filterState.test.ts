import { describe, expect, it } from 'vitest';
import { countForecastModelActiveFilters, parseForecastModelFilter, serializeForecastModelFilter, toForecastModelQuery } from '../filterState';

describe('forecastModel filter state', () => {
  it('is empty by default', () => {
    expect(parseForecastModelFilter(new URLSearchParams())).toEqual({});
    expect(countForecastModelActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).externalRef).toBe('abc');
  });

  it('round trips modelName', () => {
    const filter = { modelName: 'abc' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).modelName).toBe('abc');
  });

  it('round trips algorithm selection', () => {
    const filter = { algorithm: ['LINEAR'] as never };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).algorithm).toBe('LINEAR');
  });

  it('round trips trainedOn window', () => {
    const filter = { trainedOnFrom: '2026-01-01', trainedOnTo: '2026-02-01' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
  });

  it('round trips mape range', () => {
    const filter = { mapeMin: 1, mapeMax: 9 };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).mapeMax).toBe(9);
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
    expect(toForecastModelQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseForecastModelFilter(serializeForecastModelFilter(filter))).toEqual(filter);
  });
});
