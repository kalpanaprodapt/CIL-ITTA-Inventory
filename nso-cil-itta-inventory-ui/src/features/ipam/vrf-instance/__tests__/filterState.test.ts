import { describe, expect, it } from 'vitest';
import { countVrfInstanceActiveFilters, parseVrfInstanceFilter, serializeVrfInstanceFilter, toVrfInstanceQuery } from '../filterState';

describe('vrfInstance filter state', () => {
  it('is empty by default', () => {
    expect(parseVrfInstanceFilter(new URLSearchParams())).toEqual({});
    expect(countVrfInstanceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips vrfName', () => {
    const filter = { vrfName: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).vrfName).toBe('abc');
  });

  it('round trips routeDistinguisher', () => {
    const filter = { routeDistinguisher: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).routeDistinguisher).toBe('abc');
  });

  it('round trips importTargets', () => {
    const filter = { importTargets: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).importTargets).toBe('abc');
  });

  it('round trips exportTargets', () => {
    const filter = { exportTargets: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).exportTargets).toBe('abc');
  });

  it('round trips deviceId', () => {
    const filter = { deviceId: '00000000-0000-4000-8000-000000000011' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseVrfInstanceFilter(serializeVrfInstanceFilter(filter))).toEqual(filter);
    expect(toVrfInstanceQuery(filter).owner).toBe('abc');
  });
});
