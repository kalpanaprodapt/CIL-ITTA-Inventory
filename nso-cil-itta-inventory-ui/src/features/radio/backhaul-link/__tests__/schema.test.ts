import { describe, expect, it } from 'vitest';
import { cleanBackhaulLinkValues, validateBackhaulLink } from '../schema';
import { backhaulLinkInput } from './fixtures';

describe('backhaulLink schema', () => {
  it('accepts the fixture', () => {
    expect(validateBackhaulLink(backhaulLinkInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanBackhaulLinkValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...backhaulLinkInput };
    delete values.code;
    const result = validateBackhaulLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...backhaulLinkInput };
    delete values.name;
    const result = validateBackhaulLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...backhaulLinkInput };
    delete values.status;
    const result = validateBackhaulLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires baseStationId', () => {
    const values: Record<string, unknown> = { ...backhaulLinkInput };
    delete values.baseStationId;
    const result = validateBackhaulLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.baseStationId).toBeDefined();
  });

  it('rejects malformed baseStationId', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, baseStationId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.baseStationId).toContain('UUID');
  });

  it('rejects unknown linkMedium', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, linkMedium: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires capacityMbps', () => {
    const values: Record<string, unknown> = { ...backhaulLinkInput };
    delete values.capacityMbps;
    const result = validateBackhaulLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.capacityMbps).toBeDefined();
  });

  it('rejects negative capacityMbps', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, capacityMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.capacityMbps).toContain('negative');
  });

  it('rejects negative latencyMs', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, latencyMs: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.latencyMs).toContain('negative');
  });

  it('rejects circuitRef longer than 255', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, circuitRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.circuitRef).toContain('Circuit Ref');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateBackhaulLink({ ...backhaulLinkInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
