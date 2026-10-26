import { describe, expect, it } from 'vitest';
import { countOtnLinkActiveFilters, parseOtnLinkFilter, serializeOtnLinkFilter, toOtnLinkQuery } from '../filterState';

describe('otnLink filter state', () => {
  it('is empty by default', () => {
    expect(parseOtnLinkFilter(new URLSearchParams())).toEqual({});
    expect(countOtnLinkActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).externalRef).toBe('abc');
  });

  it('round trips aNodeId', () => {
    const filter = { aNodeId: '00000000-0000-4000-8000-000000000011' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
  });

  it('round trips zNodeId', () => {
    const filter = { zNodeId: '00000000-0000-4000-8000-000000000011' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
  });

  it('round trips odu selection', () => {
    const filter = { odu: ['ODU0'] as never };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).odu).toBe('ODU0');
  });

  it('round trips latencyUs range', () => {
    const filter = { latencyUsMin: 1, latencyUsMax: 9 };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).latencyUsMax).toBe(9);
  });

  it('round trips protection', () => {
    const filter = { protection: 'abc' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).protection).toBe('abc');
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseOtnLinkFilter(serializeOtnLinkFilter(filter))).toEqual(filter);
    expect(toOtnLinkQuery(filter).region).toBe('abc');
  });
});
