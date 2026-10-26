import { describe, expect, it } from 'vitest';
import { countCountryActiveFilters, parseCountryFilter, serializeCountryFilter, toCountryQuery } from '../filterState';

describe('country filter state', () => {
  it('is empty by default', () => {
    expect(parseCountryFilter(new URLSearchParams())).toEqual({});
    expect(countCountryActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).externalRef).toBe('abc');
  });

  it('round trips isoCode', () => {
    const filter = { isoCode: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).isoCode).toBe('abc');
  });

  it('round trips isoNumeric range', () => {
    const filter = { isoNumericMin: 1, isoNumericMax: 9 };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).isoNumericMax).toBe(9);
  });

  it('round trips dialCode', () => {
    const filter = { dialCode: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).dialCode).toBe('abc');
  });

  it('round trips currency', () => {
    const filter = { currency: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).currency).toBe('abc');
  });

  it('round trips regulatoryBody', () => {
    const filter = { regulatoryBody: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).regulatoryBody).toBe('abc');
  });

  it('round trips timezone', () => {
    const filter = { timezone: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).timezone).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseCountryFilter(serializeCountryFilter(filter))).toEqual(filter);
    expect(toCountryQuery(filter).costCenter).toBe('abc');
  });
});
