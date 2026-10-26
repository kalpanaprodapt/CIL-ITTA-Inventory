import { describe, expect, it } from 'vitest';
import { countPurchaseOrderActiveFilters, parsePurchaseOrderFilter, serializePurchaseOrderFilter, toPurchaseOrderQuery } from '../filterState';

describe('purchaseOrder filter state', () => {
  it('is empty by default', () => {
    expect(parsePurchaseOrderFilter(new URLSearchParams())).toEqual({});
    expect(countPurchaseOrderActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).externalRef).toBe('abc');
  });

  it('round trips poNumber', () => {
    const filter = { poNumber: 'abc' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).poNumber).toBe('abc');
  });

  it('round trips vendorId', () => {
    const filter = { vendorId: '00000000-0000-4000-8000-000000000011' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
  });

  it('round trips orderedOn window', () => {
    const filter = { orderedOnFrom: '2026-01-01', orderedOnTo: '2026-02-01' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
  });

  it('round trips totalAmount range', () => {
    const filter = { totalAmountMin: 1, totalAmountMax: 9 };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).totalAmountMax).toBe(9);
  });

  it('round trips currency', () => {
    const filter = { currency: 'abc' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).currency).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).owner).toBe('abc');
  });

  it('round trips criticality selection', () => {
    const filter = { criticality: ['LOW'] as never };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).criticality).toBe('LOW');
  });

  it('round trips costCenter', () => {
    const filter = { costCenter: 'abc' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
    expect(toPurchaseOrderQuery(filter).costCenter).toBe('abc');
  });

  it('round trips effectiveFrom window', () => {
    const filter = { effectiveFromFrom: '2026-01-01', effectiveFromTo: '2026-02-01' };
    expect(parsePurchaseOrderFilter(serializePurchaseOrderFilter(filter))).toEqual(filter);
  });
});
