import { describe, expect, it } from 'vitest';
import { cleanTicketLinkValues, validateTicketLink } from '../schema';
import { ticketLinkInput } from './fixtures';

describe('ticketLink schema', () => {
  it('accepts the fixture', () => {
    expect(validateTicketLink(ticketLinkInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanTicketLinkValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateTicketLink({ ...ticketLinkInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...ticketLinkInput };
    delete values.code;
    const result = validateTicketLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateTicketLink({ ...ticketLinkInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...ticketLinkInput };
    delete values.name;
    const result = validateTicketLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateTicketLink({ ...ticketLinkInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...ticketLinkInput };
    delete values.status;
    const result = validateTicketLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateTicketLink({ ...ticketLinkInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateTicketLink({ ...ticketLinkInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires alarmId', () => {
    const values: Record<string, unknown> = { ...ticketLinkInput };
    delete values.alarmId;
    const result = validateTicketLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.alarmId).toBeDefined();
  });

  it('rejects malformed alarmId', () => {
    const result = validateTicketLink({ ...ticketLinkInput, alarmId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.alarmId).toContain('UUID');
  });

  it('rejects unknown ticketSystem', () => {
    const result = validateTicketLink({ ...ticketLinkInput, ticketSystem: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects ticketNumber longer than 255', () => {
    const result = validateTicketLink({ ...ticketLinkInput, ticketNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.ticketNumber).toContain('Ticket Number');
  });

  it('requires ticketNumber', () => {
    const values: Record<string, unknown> = { ...ticketLinkInput };
    delete values.ticketNumber;
    const result = validateTicketLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.ticketNumber).toBeDefined();
  });

  it('rejects owner longer than 255', () => {
    const result = validateTicketLink({ ...ticketLinkInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateTicketLink({ ...ticketLinkInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateTicketLink({ ...ticketLinkInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateTicketLink({ ...ticketLinkInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateTicketLink({ ...ticketLinkInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
