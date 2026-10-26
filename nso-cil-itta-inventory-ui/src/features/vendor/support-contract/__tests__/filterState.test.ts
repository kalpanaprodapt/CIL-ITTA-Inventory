import { describe, expect, it } from 'vitest';
import { countSupportContractActiveFilters, parseSupportContractFilter, serializeSupportContractFilter, toSupportContractQuery } from '../filterState';

describe('supportContract filter state', () => {
  it('is empty by default', () => {
    expect(parseSupportContractFilter(new URLSearchParams())).toEqual({});
    expect(countSupportContractActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vendorId', () => {
    const filter = { vendorId: '00000000-0000-4000-8000-000000000011' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
  });

  it('round trips contractNumber', () => {
    const filter = { contractNumber: 'abc' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).contractNumber).toBe('abc');
  });

  it('round trips startDate window', () => {
    const filter = { startDateFrom: '2026-01-01', startDateTo: '2026-02-01' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
  });

  it('round trips endDate window', () => {
    const filter = { endDateFrom: '2026-01-01', endDateTo: '2026-02-01' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
  });

  it('round trips coverageLevel selection', () => {
    const filter = { coverageLevel: ['BRONZE'] as never };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).coverageLevel).toBe('BRONZE');
  });

  it('round trips annualCost range', () => {
    const filter = { annualCostMin: 1, annualCostMax: 9 };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).annualCostMax).toBe(9);
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
    expect(toSupportContractQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseSupportContractFilter(serializeSupportContractFilter(filter))).toEqual(filter);
  });
});
