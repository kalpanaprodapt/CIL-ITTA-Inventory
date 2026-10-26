import { describe, expect, it } from 'vitest';
import { countBuildingActiveFilters, parseBuildingFilter, serializeBuildingFilter, toBuildingQuery } from '../filterState';

describe('building filter state', () => {
  it('is empty by default', () => {
    expect(parseBuildingFilter(new URLSearchParams())).toEqual({});
    expect(countBuildingActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).externalRef).toBe('abc');
  });

  it('round trips siteId', () => {
    const filter = { siteId: '00000000-0000-4000-8000-000000000011' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
  });

  it('round trips floors range', () => {
    const filter = { floorsMin: 1, floorsMax: 9 };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).floorsMax).toBe(9);
  });

  it('round trips yearBuilt range', () => {
    const filter = { yearBuiltMin: 1, yearBuiltMax: 9 };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).yearBuiltMax).toBe(9);
  });

  it('round trips ownership selection', () => {
    const filter = { ownership: ['OWNED'] as never };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).ownership).toBe('OWNED');
  });

  it('round trips leaseExpiry window', () => {
    const filter = { leaseExpiryFrom: '2026-01-01', leaseExpiryTo: '2026-02-01' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
  });

  it('round trips securityLevel range', () => {
    const filter = { securityLevelMin: 1, securityLevelMax: 9 };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).securityLevelMax).toBe(9);
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).region).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parseBuildingFilter(serializeBuildingFilter(filter))).toEqual(filter);
    expect(toBuildingQuery(filter).criticality).toBe('LOW');
  });
});
