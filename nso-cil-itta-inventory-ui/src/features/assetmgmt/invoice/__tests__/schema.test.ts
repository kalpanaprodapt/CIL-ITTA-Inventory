import { describe, expect, it } from 'vitest';
import { cleanInvoiceValues, validateInvoice } from '../schema';
import { invoiceInput } from './fixtures';

describe('invoice schema', () => {
  it('accepts the fixture', () => {
    expect(validateInvoice(invoiceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanInvoiceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateInvoice({ ...invoiceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...invoiceInput };
    delete values.code;
    const result = validateInvoice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateInvoice({ ...invoiceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...invoiceInput };
    delete values.name;
    const result = validateInvoice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateInvoice({ ...invoiceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...invoiceInput };
    delete values.status;
    const result = validateInvoice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateInvoice({ ...invoiceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateInvoice({ ...invoiceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects invoiceNumber longer than 255', () => {
    const result = validateInvoice({ ...invoiceInput, invoiceNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.invoiceNumber).toContain('Invoice Number');
  });

  it('requires invoiceNumber', () => {
    const values: Record<string, unknown> = { ...invoiceInput };
    delete values.invoiceNumber;
    const result = validateInvoice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.invoiceNumber).toBeDefined();
  });

  it('rejects malformed poId', () => {
    const result = validateInvoice({ ...invoiceInput, poId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.poId).toContain('UUID');
  });

  it('requires amount', () => {
    const values: Record<string, unknown> = { ...invoiceInput };
    delete values.amount;
    const result = validateInvoice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.amount).toBeDefined();
  });

  it('rejects negative amount', () => {
    const result = validateInvoice({ ...invoiceInput, amount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.amount).toContain('negative');
  });

  it('rejects malformed date in dueDate', () => {
    const result = validateInvoice({ ...invoiceInput, dueDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateInvoice({ ...invoiceInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateInvoice({ ...invoiceInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateInvoice({ ...invoiceInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateInvoice({ ...invoiceInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateInvoice({ ...invoiceInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
