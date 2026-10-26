import { describe, expect, it } from 'vitest';
import { countPortActiveFilters, parsePortFilter, serializePortFilter, toPortQuery } from '../filterState';

describe('port filter state', () => {
  it('is empty by default', () => {
    expect(parsePortFilter(new URLSearchParams())).toEqual({});
    expect(countPortActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).externalRef).toBe('abc');
  });

  it('round trips deviceId', () => {
    const filter = { deviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
  });

  it('round trips cardId', () => {
    const filter = { cardId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
  });

  it('round trips portNumber', () => {
    const filter = { portNumber: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).portNumber).toBe('abc');
  });

  it('round trips speedMbps range', () => {
    const filter = { speedMbpsMin: 1, speedMbpsMax: 9 };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).speedMbpsMax).toBe(9);
  });

  it('round trips mediaType selection', () => {
    const filter = { mediaType: ['COPPER'] as never };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).mediaType).toBe('COPPER');
  });

  it('round trips adminState selection', () => {
    const filter = { adminState: ['UP'] as never };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).adminState).toBe('UP');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parsePortFilter(serializePortFilter(filter))).toEqual(filter);
    expect(toPortQuery(filter).costCenter).toBe('abc');
  });
});
