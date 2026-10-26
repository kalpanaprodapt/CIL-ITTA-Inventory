import { describe, expect, it } from 'vitest';
import { countContractActiveFilters, parseContractFilter, serializeContractFilter, toContractQuery } from '../filterState';

describe('contract filter state', () => {
  it('is empty by default', () => {
    expect(parseContractFilter(new URLSearchParams())).toEqual({});
    expect(countContractActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).externalRef).toBe('abc');
  });

  it('round trips customerId', () => {
    const filter = { customerId: '00000000-0000-4000-8000-000000000011' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
  });

  it('round trips contractNumber', () => {
    const filter = { contractNumber: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).contractNumber).toBe('abc');
  });

  it('round trips startDate window', () => {
    const filter = { startDateFrom: '2026-01-01', startDateTo: '2026-02-01' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
  });

  it('round trips endDate window', () => {
    const filter = { endDateFrom: '2026-01-01', endDateTo: '2026-02-01' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
  });

  it('round trips termMonths range', () => {
    const filter = { termMonthsMin: 1, termMonthsMax: 9 };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).termMonthsMax).toBe(9);
  });

  it('round trips autoRenew flag', () => {
    const filter = { autoRenew: false };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseContractFilter(serializeContractFilter(filter))).toEqual(filter);
    expect(toContractQuery(filter).costCenter).toBe('abc');
  });
});
