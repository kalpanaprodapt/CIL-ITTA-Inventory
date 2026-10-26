import { describe, expect, it } from 'vitest';
import { cleanLinkValues, validateLink } from '../schema';
import { linkInput } from './fixtures';

describe('link schema', () => {
  it('accepts the fixture', () => {
    expect(validateLink(linkInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanLinkValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateLink({ ...linkInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...linkInput };
    delete values.code;
    const result = validateLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateLink({ ...linkInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...linkInput };
    delete values.name;
    const result = validateLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateLink({ ...linkInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...linkInput };
    delete values.status;
    const result = validateLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateLink({ ...linkInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateLink({ ...linkInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires aInterfaceId', () => {
    const values: Record<string, unknown> = { ...linkInput };
    delete values.aInterfaceId;
    const result = validateLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.aInterfaceId).toBeDefined();
  });

  it('rejects malformed aInterfaceId', () => {
    const result = validateLink({ ...linkInput, aInterfaceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.aInterfaceId).toContain('UUID');
  });

  it('requires zInterfaceId', () => {
    const values: Record<string, unknown> = { ...linkInput };
    delete values.zInterfaceId;
    const result = validateLink(values);
    expect(result.ok).toBe(false);
    expect(result.errors.zInterfaceId).toBeDefined();
  });

  it('rejects malformed zInterfaceId', () => {
    const result = validateLink({ ...linkInput, zInterfaceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.zInterfaceId).toContain('UUID');
  });

  it('rejects unknown linkType', () => {
    const result = validateLink({ ...linkInput, linkType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative speedMbps', () => {
    const result = validateLink({ ...linkInput, speedMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.speedMbps).toContain('negative');
  });

  it('rejects discoveredBy longer than 255', () => {
    const result = validateLink({ ...linkInput, discoveredBy: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.discoveredBy).toContain('Discovered By');
  });

  it('rejects region longer than 255', () => {
    const result = validateLink({ ...linkInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateLink({ ...linkInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateLink({ ...linkInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateLink({ ...linkInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateLink({ ...linkInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
