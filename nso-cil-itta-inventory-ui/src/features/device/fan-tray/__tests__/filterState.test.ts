import { describe, expect, it } from 'vitest';
import { countFanTrayActiveFilters, parseFanTrayFilter, serializeFanTrayFilter, toFanTrayQuery } from '../filterState';

describe('fanTray filter state', () => {
  it('is empty by default', () => {
    expect(parseFanTrayFilter(new URLSearchParams())).toEqual({});
    expect(countFanTrayActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).externalRef).toBe('abc');
  });

  it('round trips chassisId', () => {
    const filter = { chassisId: '00000000-0000-4000-8000-000000000011' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
  });

  it('round trips slotNumber range', () => {
    const filter = { slotNumberMin: 1, slotNumberMax: 9 };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).slotNumberMax).toBe(9);
  });

  it('round trips fanCount range', () => {
    const filter = { fanCountMin: 1, fanCountMax: 9 };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).fanCountMax).toBe(9);
  });

  it('round trips airflow selection', () => {
    const filter = { airflow: ['FRONT_TO_BACK'] as never };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).airflow).toBe('FRONT_TO_BACK');
  });

  it('round trips serialNumber', () => {
    const filter = { serialNumber: 'abc' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).serialNumber).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseFanTrayFilter(serializeFanTrayFilter(filter))).toEqual(filter);
    expect(toFanTrayQuery(filter).lifecycleStage).toBe('PLAN');
  });
});
