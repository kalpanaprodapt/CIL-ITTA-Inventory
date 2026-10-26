import { describe, expect, it } from 'vitest';
import { countInvoiceActiveFilters, parseInvoiceFilter, serializeInvoiceFilter, toInvoiceQuery } from '../filterState';

describe('invoice filter state', () => {
  it('is empty by default', () => {
    expect(parseInvoiceFilter(new URLSearchParams())).toEqual({});
    expect(countInvoiceActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).externalRef).toBe('abc');
  });

  it('round trips invoiceNumber', () => {
    const filter = { invoiceNumber: 'abc' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).invoiceNumber).toBe('abc');
  });

  it('round trips poId', () => {
    const filter = { poId: '00000000-0000-4000-8000-000000000011' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
  });

  it('round trips amount range', () => {
    const filter = { amountMin: 1, amountMax: 9 };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).amountMax).toBe(9);
  });

  it('round trips dueDate window', () => {
    const filter = { dueDateFrom: '2026-01-01', dueDateTo: '2026-02-01' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
  });

  it('round trips paid flag', () => {
    const filter = { paid: false };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseInvoiceFilter(serializeInvoiceFilter(filter))).toEqual(filter);
    expect(toInvoiceQuery(filter).labels).toBe('abc');
  });
});
