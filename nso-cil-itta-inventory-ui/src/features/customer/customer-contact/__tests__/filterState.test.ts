import { describe, expect, it } from 'vitest';
import { countCustomerContactActiveFilters, parseCustomerContactFilter, serializeCustomerContactFilter, toCustomerContactQuery } from '../filterState';

describe('customerContact filter state', () => {
  it('is empty by default', () => {
    expect(parseCustomerContactFilter(new URLSearchParams())).toEqual({});
    expect(countCustomerContactActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).externalRef).toBe('abc');
  });

  it('round trips customerId', () => {
    const filter = { customerId: '00000000-0000-4000-8000-000000000011' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
  });

  it('round trips fullName', () => {
    const filter = { fullName: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).fullName).toBe('abc');
  });

  it('round trips email', () => {
    const filter = { email: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).email).toBe('abc');
  });

  it('round trips phone', () => {
    const filter = { phone: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).phone).toBe('abc');
  });

  it('round trips contactRole selection', () => {
    const filter = { contactRole: ['TECHNICAL'] as never };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).contactRole).toBe('TECHNICAL');
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseCustomerContactFilter(serializeCustomerContactFilter(filter))).toEqual(filter);
    expect(toCustomerContactQuery(filter).owner).toBe('abc');
  });
});
