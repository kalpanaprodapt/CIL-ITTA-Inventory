import { describe, expect, it } from 'vitest';
import { countCustomerAccountActiveFilters, parseCustomerAccountFilter, serializeCustomerAccountFilter, toCustomerAccountQuery } from '../filterState';

describe('customerAccount filter state', () => {
  it('is empty by default', () => {
    expect(parseCustomerAccountFilter(new URLSearchParams())).toEqual({});
    expect(countCustomerAccountActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).externalRef).toBe('abc');
  });

  it('round trips customerId', () => {
    const filter = { customerId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
  });

  it('round trips accountNumber', () => {
    const filter = { accountNumber: 'abc' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).accountNumber).toBe('abc');
  });

  it('round trips billingCycle selection', () => {
    const filter = { billingCycle: ['MONTHLY'] as never };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).billingCycle).toBe('MONTHLY');
  });

  it('round trips currency', () => {
    const filter = { currency: 'abc' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).currency).toBe('abc');
  });

  it('round trips creditLimit range', () => {
    const filter = { creditLimitMin: 1, creditLimitMax: 9 };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).creditLimitMax).toBe(9);
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseCustomerAccountFilter(serializeCustomerAccountFilter(filter))).toEqual(filter);
    expect(toCustomerAccountQuery(filter).sourceSystem).toBe('abc');
  });
});
