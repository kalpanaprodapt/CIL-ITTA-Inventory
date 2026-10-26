import { describe, expect, it } from 'vitest';
import { countAlarmDefinitionActiveFilters, parseAlarmDefinitionFilter, serializeAlarmDefinitionFilter, toAlarmDefinitionQuery } from '../filterState';

describe('alarmDefinition filter state', () => {
  it('is empty by default', () => {
    expect(parseAlarmDefinitionFilter(new URLSearchParams())).toEqual({});
    expect(countAlarmDefinitionActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).externalRef).toBe('abc');
  });

  it('round trips alarmCode', () => {
    const filter = { alarmCode: 'abc' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).alarmCode).toBe('abc');
  });

  it('round trips severity selection', () => {
    const filter = { severity: ['INDETERMINATE'] as never };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).severity).toBe('INDETERMINATE');
  });

  it('round trips probableCause', () => {
    const filter = { probableCause: 'abc' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).probableCause).toBe('abc');
  });

  it('round trips autoClear flag', () => {
    const filter = { autoClear: false };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseAlarmDefinitionFilter(serializeAlarmDefinitionFilter(filter))).toEqual(filter);
    expect(toAlarmDefinitionQuery(filter).labels).toBe('abc');
  });
});
