import { describe, expect, it } from 'vitest';
import { cleanReservationValues, validateReservation } from '../schema';
import { reservationInput } from './fixtures';

describe('reservation schema', () => {
  it('accepts the fixture', () => {
    expect(validateReservation(reservationInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanReservationValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateReservation({ ...reservationInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...reservationInput };
    delete values.code;
    const result = validateReservation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateReservation({ ...reservationInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...reservationInput };
    delete values.name;
    const result = validateReservation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateReservation({ ...reservationInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...reservationInput };
    delete values.status;
    const result = validateReservation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateReservation({ ...reservationInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateReservation({ ...reservationInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects resourceRef longer than 255', () => {
    const result = validateReservation({ ...reservationInput, resourceRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.resourceRef).toContain('Resource Ref');
  });

  it('requires resourceRef', () => {
    const values: Record<string, unknown> = { ...reservationInput };
    delete values.resourceRef;
    const result = validateReservation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.resourceRef).toBeDefined();
  });

  it('rejects reservedFor longer than 255', () => {
    const result = validateReservation({ ...reservationInput, reservedFor: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.reservedFor).toContain('Reserved For');
  });

  it('requires reservedFor', () => {
    const values: Record<string, unknown> = { ...reservationInput };
    delete values.reservedFor;
    const result = validateReservation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.reservedFor).toBeDefined();
  });

  it('requires quantity', () => {
    const values: Record<string, unknown> = { ...reservationInput };
    delete values.quantity;
    const result = validateReservation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.quantity).toBeDefined();
  });

  it('rejects negative quantity', () => {
    const result = validateReservation({ ...reservationInput, quantity: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.quantity).toContain('negative');
  });

  it('requires startDate', () => {
    const values: Record<string, unknown> = { ...reservationInput };
    delete values.startDate;
    const result = validateReservation(values);
    expect(result.ok).toBe(false);
    expect(result.errors.startDate).toBeDefined();
  });

  it('rejects malformed date in startDate', () => {
    const result = validateReservation({ ...reservationInput, startDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in endDate', () => {
    const result = validateReservation({ ...reservationInput, endDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects notes longer than 4000', () => {
    const result = validateReservation({ ...reservationInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateReservation({ ...reservationInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateReservation({ ...reservationInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateReservation({ ...reservationInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateReservation({ ...reservationInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
