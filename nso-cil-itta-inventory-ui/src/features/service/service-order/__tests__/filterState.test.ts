import { describe, expect, it } from 'vitest';
import { countServiceOrderActiveFilters, parseServiceOrderFilter, serializeServiceOrderFilter, toServiceOrderQuery } from '../filterState';

describe('serviceOrder filter state', () => {
  it('is empty by default', () => {
    expect(parseServiceOrderFilter(new URLSearchParams())).toEqual({});
    expect(countServiceOrderActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).externalRef).toBe('abc');
  });

  it('round trips customerId', () => {
    const filter = { customerId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
  });

  it('round trips orderType selection', () => {
    const filter = { orderType: ['NEW'] as never };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).orderType).toBe('NEW');
  });

  it('round trips requestedOn window', () => {
    const filter = { requestedOnFrom: '2026-01-01', requestedOnTo: '2026-02-01' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
  });

  it('round trips targetDate window', () => {
    const filter = { targetDateFrom: '2026-01-01', targetDateTo: '2026-02-01' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
  });

  it('round trips priority range', () => {
    const filter = { priorityMin: 1, priorityMax: 9 };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).priorityMax).toBe(9);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseServiceOrderFilter(serializeServiceOrderFilter(filter))).toEqual(filter);
    expect(toServiceOrderQuery(filter).costCenter).toBe('abc');
  });
});
