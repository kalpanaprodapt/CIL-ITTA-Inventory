import { describe, expect, it } from 'vitest';
import { countImportBatchActiveFilters, parseImportBatchFilter, serializeImportBatchFilter, toImportBatchQuery } from '../filterState';

describe('importBatch filter state', () => {
  it('is empty by default', () => {
    expect(parseImportBatchFilter(new URLSearchParams())).toEqual({});
    expect(countImportBatchActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).externalRef).toBe('abc');
  });

  it('round trips fileName', () => {
    const filter = { fileName: 'abc' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).fileName).toBe('abc');
  });

  it('round trips entityType', () => {
    const filter = { entityType: 'abc' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).entityType).toBe('abc');
  });

  it('round trips totalRows range', () => {
    const filter = { totalRowsMin: 1, totalRowsMax: 9 };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).totalRowsMax).toBe(9);
  });

  it('round trips processedRows range', () => {
    const filter = { processedRowsMin: 1, processedRowsMax: 9 };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).processedRowsMax).toBe(9);
  });

  it('round trips failedRows range', () => {
    const filter = { failedRowsMin: 1, failedRowsMax: 9 };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).failedRowsMax).toBe(9);
  });

  it('round trips batchState selection', () => {
    const filter = { batchState: ['UPLOADED'] as never };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).batchState).toBe('UPLOADED');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseImportBatchFilter(serializeImportBatchFilter(filter))).toEqual(filter);
    expect(toImportBatchQuery(filter).sourceSystem).toBe('abc');
  });
});
