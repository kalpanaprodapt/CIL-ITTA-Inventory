import { describe, expect, it } from 'vitest';
import { countRouteTargetActiveFilters, parseRouteTargetFilter, serializeRouteTargetFilter, toRouteTargetQuery } from '../filterState';

describe('routeTarget filter state', () => {
  it('is empty by default', () => {
    expect(parseRouteTargetFilter(new URLSearchParams())).toEqual({});
    expect(countRouteTargetActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).externalRef).toBe('abc');
  });

  it('round trips asn range', () => {
    const filter = { asnMin: 1, asnMax: 9 };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).asnMax).toBe(9);
  });

  it('round trips assignedNumber range', () => {
    const filter = { assignedNumberMin: 1, assignedNumberMax: 9 };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).assignedNumberMax).toBe(9);
  });

  it('round trips rtType selection', () => {
    const filter = { rtType: ['IMPORT'] as never };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).rtType).toBe('IMPORT');
  });

  it('round trips vrfId', () => {
    const filter = { vrfId: '00000000-0000-4000-8000-000000000011' };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseRouteTargetFilter(serializeRouteTargetFilter(filter))).toEqual(filter);
    expect(toRouteTargetQuery(filter).criticality).toBe('LOW');
  });
});
