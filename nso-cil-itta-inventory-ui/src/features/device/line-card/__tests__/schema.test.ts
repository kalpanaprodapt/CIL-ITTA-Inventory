import { describe, expect, it } from 'vitest';
import { cleanLineCardValues, validateLineCard } from '../schema';
import { lineCardInput } from './fixtures';

describe('lineCard schema', () => {
  it('accepts the fixture', () => {
    expect(validateLineCard(lineCardInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanLineCardValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...lineCardInput };
    delete values.code;
    const result = validateLineCard(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...lineCardInput };
    delete values.name;
    const result = validateLineCard(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateLineCard({ ...lineCardInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...lineCardInput };
    delete values.status;
    const result = validateLineCard(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateLineCard({ ...lineCardInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires chassisId', () => {
    const values: Record<string, unknown> = { ...lineCardInput };
    delete values.chassisId;
    const result = validateLineCard(values);
    expect(result.ok).toBe(false);
    expect(result.errors.chassisId).toBeDefined();
  });

  it('rejects malformed chassisId', () => {
    const result = validateLineCard({ ...lineCardInput, chassisId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.chassisId).toContain('UUID');
  });

  it('requires slotNumber', () => {
    const values: Record<string, unknown> = { ...lineCardInput };
    delete values.slotNumber;
    const result = validateLineCard(values);
    expect(result.ok).toBe(false);
    expect(result.errors.slotNumber).toBeDefined();
  });

  it('rejects negative slotNumber', () => {
    const result = validateLineCard({ ...lineCardInput, slotNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.slotNumber).toContain('negative');
  });

  it('rejects cardType longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, cardType: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.cardType).toContain('Card Type');
  });

  it('rejects negative portCount', () => {
    const result = validateLineCard({ ...lineCardInput, portCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.portCount).toContain('negative');
  });

  it('rejects serialNumber longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, serialNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.serialNumber).toContain('Serial Number');
  });

  it('rejects firmware longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, firmware: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.firmware).toContain('Firmware');
  });

  it('rejects region longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateLineCard({ ...lineCardInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateLineCard({ ...lineCardInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateLineCard({ ...lineCardInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
