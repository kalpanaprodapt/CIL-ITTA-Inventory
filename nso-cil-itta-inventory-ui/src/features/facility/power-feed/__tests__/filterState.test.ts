import { describe, expect, it } from 'vitest';
import { countPowerFeedActiveFilters, parsePowerFeedFilter, serializePowerFeedFilter, toPowerFeedQuery } from '../filterState';

describe('powerFeed filter state', () => {
  it('is empty by default', () => {
    expect(parsePowerFeedFilter(new URLSearchParams())).toEqual({});
    expect(countPowerFeedActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).externalRef).toBe('abc');
  });

  it('round trips roomId', () => {
    const filter = { roomId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
  });

  it('round trips feedType selection', () => {
    const filter = { feedType: ['AC'] as never };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).feedType).toBe('AC');
  });

  it('round trips voltage range', () => {
    const filter = { voltageMin: 1, voltageMax: 9 };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).voltageMax).toBe(9);
  });

  it('round trips amperage range', () => {
    const filter = { amperageMin: 1, amperageMax: 9 };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).amperageMax).toBe(9);
  });

  it('round trips phase', () => {
    const filter = { phase: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).phase).toBe('abc');
  });

  it('round trips breakerLabel', () => {
    const filter = { breakerLabel: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).breakerLabel).toBe('abc');
  });

  it('round trips redundancyGroup', () => {
    const filter = { redundancyGroup: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).redundancyGroup).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
    expect(toPowerFeedQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parsePowerFeedFilter(serializePowerFeedFilter(filter))).toEqual(filter);
  });
});
