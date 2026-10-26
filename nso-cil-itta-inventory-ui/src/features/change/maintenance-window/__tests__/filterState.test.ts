import { describe, expect, it } from 'vitest';
import { countMaintenanceWindowActiveFilters, parseMaintenanceWindowFilter, serializeMaintenanceWindowFilter, toMaintenanceWindowQuery } from '../filterState';

describe('maintenanceWindow filter state', () => {
  it('is empty by default', () => {
    expect(parseMaintenanceWindowFilter(new URLSearchParams())).toEqual({});
    expect(countMaintenanceWindowActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).externalRef).toBe('abc');
  });

  it('round trips windowName', () => {
    const filter = { windowName: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).windowName).toBe('abc');
  });

  it('round trips startsAt window', () => {
    const filter = { startsAtFrom: '2026-01-01', startsAtTo: '2026-02-01' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
  });

  it('round trips endsAt window', () => {
    const filter = { endsAtFrom: '2026-01-01', endsAtTo: '2026-02-01' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
  });

  it('round trips scope', () => {
    const filter = { scope: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).scope).toBe('abc');
  });

  it('round trips impactLevel selection', () => {
    const filter = { impactLevel: ['NONE'] as never };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).impactLevel).toBe('NONE');
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseMaintenanceWindowFilter(serializeMaintenanceWindowFilter(filter))).toEqual(filter);
    expect(toMaintenanceWindowQuery(filter).region).toBe('abc');
  });
});
