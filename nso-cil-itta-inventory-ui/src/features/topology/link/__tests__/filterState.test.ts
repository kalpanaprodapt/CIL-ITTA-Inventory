import { describe, expect, it } from 'vitest';
import { countLinkActiveFilters, parseLinkFilter, serializeLinkFilter, toLinkQuery } from '../filterState';

describe('link filter state', () => {
  it('is empty by default', () => {
    expect(parseLinkFilter(new URLSearchParams())).toEqual({});
    expect(countLinkActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).externalRef).toBe('abc');
  });

  it('round trips aInterfaceId', () => {
    const filter = { aInterfaceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
  });

  it('round trips zInterfaceId', () => {
    const filter = { zInterfaceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
  });

  it('round trips linkType selection', () => {
    const filter = { linkType: ['PHYSICAL'] as never };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).linkType).toBe('PHYSICAL');
  });

  it('round trips speedMbps range', () => {
    const filter = { speedMbpsMin: 1, speedMbpsMax: 9 };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).speedMbpsMax).toBe(9);
  });

  it('round trips discoveredBy', () => {
    const filter = { discoveredBy: 'abc' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).discoveredBy).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseLinkFilter(serializeLinkFilter(filter))).toEqual(filter);
    expect(toLinkQuery(filter).costCenter).toBe('abc');
  });
});
