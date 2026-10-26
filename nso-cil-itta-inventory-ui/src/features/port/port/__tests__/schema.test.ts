import { describe, expect, it } from 'vitest';
import { cleanPortValues, validatePort } from '../schema';
import { portInput } from './fixtures';

describe('port schema', () => {
  it('accepts the fixture', () => {
    expect(validatePort(portInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPortValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePort({ ...portInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...portInput };
    delete values.code;
    const result = validatePort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePort({ ...portInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...portInput };
    delete values.name;
    const result = validatePort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePort({ ...portInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...portInput };
    delete values.status;
    const result = validatePort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePort({ ...portInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePort({ ...portInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires deviceId', () => {
    const values: Record<string, unknown> = { ...portInput };
    delete values.deviceId;
    const result = validatePort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toBeDefined();
  });

  it('rejects malformed deviceId', () => {
    const result = validatePort({ ...portInput, deviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toContain('UUID');
  });

  it('rejects malformed cardId', () => {
    const result = validatePort({ ...portInput, cardId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.cardId).toContain('UUID');
  });

  it('rejects portNumber longer than 255', () => {
    const result = validatePort({ ...portInput, portNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.portNumber).toContain('Port Number');
  });

  it('requires portNumber', () => {
    const values: Record<string, unknown> = { ...portInput };
    delete values.portNumber;
    const result = validatePort(values);
    expect(result.ok).toBe(false);
    expect(result.errors.portNumber).toBeDefined();
  });

  it('rejects negative speedMbps', () => {
    const result = validatePort({ ...portInput, speedMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.speedMbps).toContain('negative');
  });

  it('rejects unknown mediaType', () => {
    const result = validatePort({ ...portInput, mediaType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown adminState', () => {
    const result = validatePort({ ...portInput, adminState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validatePort({ ...portInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validatePort({ ...portInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validatePort({ ...portInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validatePort({ ...portInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validatePort({ ...portInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
