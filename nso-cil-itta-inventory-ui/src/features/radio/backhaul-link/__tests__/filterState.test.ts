import { describe, expect, it } from 'vitest';
import { countBackhaulLinkActiveFilters, parseBackhaulLinkFilter, serializeBackhaulLinkFilter, toBackhaulLinkQuery } from '../filterState';

describe('backhaulLink filter state', () => {
  it('is empty by default', () => {
    expect(parseBackhaulLinkFilter(new URLSearchParams())).toEqual({});
    expect(countBackhaulLinkActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).externalRef).toBe('abc');
  });

  it('round trips baseStationId', () => {
    const filter = { baseStationId: '00000000-0000-4000-8000-000000000011' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
  });

  it('round trips linkMedium selection', () => {
    const filter = { linkMedium: ['FIBER'] as never };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).linkMedium).toBe('FIBER');
  });

  it('round trips capacityMbps range', () => {
    const filter = { capacityMbpsMin: 1, capacityMbpsMax: 9 };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).capacityMbpsMax).toBe(9);
  });

  it('round trips latencyMs range', () => {
    const filter = { latencyMsMin: 1, latencyMsMax: 9 };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).latencyMsMax).toBe(9);
  });

  it('round trips circuitRef', () => {
    const filter = { circuitRef: 'abc' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).circuitRef).toBe('abc');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseBackhaulLinkFilter(serializeBackhaulLinkFilter(filter))).toEqual(filter);
    expect(toBackhaulLinkQuery(filter).labels).toBe('abc');
  });
});
