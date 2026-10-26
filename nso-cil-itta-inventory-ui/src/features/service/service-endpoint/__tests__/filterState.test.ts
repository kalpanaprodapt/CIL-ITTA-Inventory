import { describe, expect, it } from 'vitest';
import { countServiceEndpointActiveFilters, parseServiceEndpointFilter, serializeServiceEndpointFilter, toServiceEndpointQuery } from '../filterState';

describe('serviceEndpoint filter state', () => {
  it('is empty by default', () => {
    expect(parseServiceEndpointFilter(new URLSearchParams())).toEqual({});
    expect(countServiceEndpointActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).externalRef).toBe('abc');
  });

  it('round trips serviceId', () => {
    const filter = { serviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips siteId', () => {
    const filter = { siteId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips portId', () => {
    const filter = { portId: '00000000-0000-4000-8000-000000000011' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips endpointRole selection', () => {
    const filter = { endpointRole: ['HUB'] as never };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).endpointRole).toBe('HUB');
  });

  it('round trips handoff', () => {
    const filter = { handoff: 'abc' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).handoff).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseServiceEndpointFilter(serializeServiceEndpointFilter(filter))).toEqual(filter);
    expect(toServiceEndpointQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
