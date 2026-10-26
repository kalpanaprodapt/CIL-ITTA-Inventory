import { describe, expect, it } from 'vitest';
import { countIntegrationEndpointActiveFilters, parseIntegrationEndpointFilter, serializeIntegrationEndpointFilter, toIntegrationEndpointQuery } from '../filterState';

describe('integrationEndpoint filter state', () => {
  it('is empty by default', () => {
    expect(parseIntegrationEndpointFilter(new URLSearchParams())).toEqual({});
    expect(countIntegrationEndpointActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).externalRef).toBe('abc');
  });

  it('round trips endpointName', () => {
    const filter = { endpointName: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).endpointName).toBe('abc');
  });

  it('round trips targetSystem', () => {
    const filter = { targetSystem: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).targetSystem).toBe('abc');
  });

  it('round trips baseUrl', () => {
    const filter = { baseUrl: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).baseUrl).toBe('abc');
  });

  it('round trips authType selection', () => {
    const filter = { authType: ['BASIC'] as never };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).authType).toBe('BASIC');
  });

  it('round trips timeoutMs range', () => {
    const filter = { timeoutMsMin: 1, timeoutMsMax: 9 };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).timeoutMsMax).toBe(9);
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseIntegrationEndpointFilter(serializeIntegrationEndpointFilter(filter))).toEqual(filter);
    expect(toIntegrationEndpointQuery(filter).costCenter).toBe('abc');
  });
});
