import { describe, expect, it } from 'vitest';
import { countServiceInstanceActiveFilters, parseServiceInstanceFilter, serializeServiceInstanceFilter, toServiceInstanceQuery } from '../filterState';

describe('serviceInstance filter state', () => {
  it('is empty by default', () => {
    expect(parseServiceInstanceFilter(new URLSearchParams())).toEqual({});
    expect(countServiceInstanceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips catalogItemId', () => {
    const filter = { catalogItemId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips customerId', () => {
    const filter = { customerId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips activatedOn window', () => {
    const filter = { activatedOnFrom: '2026-01-01', activatedOnTo: '2026-02-01' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips bandwidthMbps range', () => {
    const filter = { bandwidthMbpsMin: 1, bandwidthMbpsMax: 9 };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).bandwidthMbpsMax).toBe(9);
  });

  it('round trips serviceState selection', () => {
    const filter = { serviceState: ['PLANNED'] as never };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).serviceState).toBe('PLANNED');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseServiceInstanceFilter(serializeServiceInstanceFilter(filter))).toEqual(filter);
    expect(toServiceInstanceQuery(filter).criticality).toBe('LOW');
  });
});
