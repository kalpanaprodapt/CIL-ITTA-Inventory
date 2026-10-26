import { describe, expect, it } from 'vitest';
import { countAlarmInstanceActiveFilters, parseAlarmInstanceFilter, serializeAlarmInstanceFilter, toAlarmInstanceQuery } from '../filterState';

describe('alarmInstance filter state', () => {
  it('is empty by default', () => {
    expect(parseAlarmInstanceFilter(new URLSearchParams())).toEqual({});
    expect(countAlarmInstanceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips definitionId', () => {
    const filter = { definitionId: '00000000-0000-4000-8000-000000000011' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips sourceRef', () => {
    const filter = { sourceRef: 'abc' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).sourceRef).toBe('abc');
  });

  it('round trips raisedAt window', () => {
    const filter = { raisedAtFrom: '2026-01-01', raisedAtTo: '2026-02-01' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips clearedAt window', () => {
    const filter = { clearedAtFrom: '2026-01-01', clearedAtTo: '2026-02-01' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips ackedBy', () => {
    const filter = { ackedBy: 'abc' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).ackedBy).toBe('abc');
  });

  it('round trips alarmState selection', () => {
    const filter = { alarmState: ['ACTIVE'] as never };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).alarmState).toBe('ACTIVE');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseAlarmInstanceFilter(serializeAlarmInstanceFilter(filter))).toEqual(filter);
    expect(toAlarmInstanceQuery(filter).sourceSystem).toBe('abc');
  });
});
