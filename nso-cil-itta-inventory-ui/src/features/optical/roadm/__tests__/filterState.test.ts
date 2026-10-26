import { describe, expect, it } from 'vitest';
import { countRoadmActiveFilters, parseRoadmFilter, serializeRoadmFilter, toRoadmQuery } from '../filterState';

describe('roadm filter state', () => {
  it('is empty by default', () => {
    expect(parseRoadmFilter(new URLSearchParams())).toEqual({});
    expect(countRoadmActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).externalRef).toBe('abc');
  });

  it('round trips systemId', () => {
    const filter = { systemId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
  });

  it('round trips degrees range', () => {
    const filter = { degreesMin: 1, degreesMax: 9 };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).degreesMax).toBe(9);
  });

  it('round trips colorless flag', () => {
    const filter = { colorless: false };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
  });

  it('round trips directionless flag', () => {
    const filter = { directionless: false };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
  });

  it('round trips contentionless flag', () => {
    const filter = { contentionless: false };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseRoadmFilter(serializeRoadmFilter(filter))).toEqual(filter);
    expect(toRoadmQuery(filter).costCenter).toBe('abc');
  });
});
