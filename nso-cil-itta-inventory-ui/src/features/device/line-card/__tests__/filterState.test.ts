import { describe, expect, it } from 'vitest';
import { countLineCardActiveFilters, parseLineCardFilter, serializeLineCardFilter, toLineCardQuery } from '../filterState';

describe('lineCard filter state', () => {
  it('is empty by default', () => {
    expect(parseLineCardFilter(new URLSearchParams())).toEqual({});
    expect(countLineCardActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).externalRef).toBe('abc');
  });

  it('round trips chassisId', () => {
    const filter = { chassisId: '00000000-0000-4000-8000-000000000011' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
  });

  it('round trips slotNumber range', () => {
    const filter = { slotNumberMin: 1, slotNumberMax: 9 };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).slotNumberMax).toBe(9);
  });

  it('round trips cardType', () => {
    const filter = { cardType: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).cardType).toBe('abc');
  });

  it('round trips portCount range', () => {
    const filter = { portCountMin: 1, portCountMax: 9 };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).portCountMax).toBe(9);
  });

  it('round trips serialNumber', () => {
    const filter = { serialNumber: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).serialNumber).toBe('abc');
  });

  it('round trips firmware', () => {
    const filter = { firmware: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).firmware).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseLineCardFilter(serializeLineCardFilter(filter))).toEqual(filter);
    expect(toLineCardQuery(filter).costCenter).toBe('abc');
  });
});
