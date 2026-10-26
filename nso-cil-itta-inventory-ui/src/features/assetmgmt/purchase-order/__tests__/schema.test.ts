import { describe, expect, it } from 'vitest';
import { cleanPurchaseOrderValues, validatePurchaseOrder } from '../schema';
import { purchaseOrderInput } from './fixtures';

describe('purchaseOrder schema', () => {
  it('accepts the fixture', () => {
    expect(validatePurchaseOrder(purchaseOrderInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPurchaseOrderValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...purchaseOrderInput };
    delete values.code;
    const result = validatePurchaseOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...purchaseOrderInput };
    delete values.name;
    const result = validatePurchaseOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...purchaseOrderInput };
    delete values.status;
    const result = validatePurchaseOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects poNumber longer than 255', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, poNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.poNumber).toContain('Po Number');
  });

  it('requires poNumber', () => {
    const values: Record<string, unknown> = { ...purchaseOrderInput };
    delete values.poNumber;
    const result = validatePurchaseOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.poNumber).toBeDefined();
  });

  it('requires vendorId', () => {
    const values: Record<string, unknown> = { ...purchaseOrderInput };
    delete values.vendorId;
    const result = validatePurchaseOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toBeDefined();
  });

  it('rejects malformed vendorId', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, vendorId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vendorId).toContain('UUID');
  });

  it('requires orderedOn', () => {
    const values: Record<string, unknown> = { ...purchaseOrderInput };
    delete values.orderedOn;
    const result = validatePurchaseOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.orderedOn).toBeDefined();
  });

  it('rejects malformed date in orderedOn', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, orderedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('requires totalAmount', () => {
    const values: Record<string, unknown> = { ...purchaseOrderInput };
    delete values.totalAmount;
    const result = validatePurchaseOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.totalAmount).toBeDefined();
  });

  it('rejects negative totalAmount', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, totalAmount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.totalAmount).toContain('negative');
  });

  it('rejects currency longer than 255', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, currency: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.currency).toContain('Currency');
  });

  it('rejects owner longer than 255', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validatePurchaseOrder({ ...purchaseOrderInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
