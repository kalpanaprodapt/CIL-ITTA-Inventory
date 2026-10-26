import { describe, expect, it } from 'vitest';
import { countBgpSessionActiveFilters, parseBgpSessionFilter, serializeBgpSessionFilter, toBgpSessionQuery } from '../filterState';

describe('bgpSession filter state', () => {
  it('is empty by default', () => {
    expect(parseBgpSessionFilter(new URLSearchParams())).toEqual({});
    expect(countBgpSessionActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).externalRef).toBe('abc');
  });

  it('round trips localDeviceId', () => {
    const filter = { localDeviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
  });

  it('round trips peerAddress', () => {
    const filter = { peerAddress: 'abc' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).peerAddress).toBe('abc');
  });

  it('round trips localAsn range', () => {
    const filter = { localAsnMin: 1, localAsnMax: 9 };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).localAsnMax).toBe(9);
  });

  it('round trips remoteAsn range', () => {
    const filter = { remoteAsnMin: 1, remoteAsnMax: 9 };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).remoteAsnMax).toBe(9);
  });

  it('round trips sessionState selection', () => {
    const filter = { sessionState: ['IDLE'] as never };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).sessionState).toBe('IDLE');
  });

  it('round trips prefixLimit range', () => {
    const filter = { prefixLimitMin: 1, prefixLimitMax: 9 };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).prefixLimitMax).toBe(9);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseBgpSessionFilter(serializeBgpSessionFilter(filter))).toEqual(filter);
    expect(toBgpSessionQuery(filter).costCenter).toBe('abc');
  });
});
