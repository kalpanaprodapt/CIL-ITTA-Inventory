import { describe, expect, it } from 'vitest';
import { cleanBgpSessionValues, validateBgpSession } from '../schema';
import { bgpSessionInput } from './fixtures';

describe('bgpSession schema', () => {
  it('accepts the fixture', () => {
    expect(validateBgpSession(bgpSessionInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanBgpSessionValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateBgpSession({ ...bgpSessionInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...bgpSessionInput };
    delete values.code;
    const result = validateBgpSession(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateBgpSession({ ...bgpSessionInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...bgpSessionInput };
    delete values.name;
    const result = validateBgpSession(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateBgpSession({ ...bgpSessionInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...bgpSessionInput };
    delete values.status;
    const result = validateBgpSession(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateBgpSession({ ...bgpSessionInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateBgpSession({ ...bgpSessionInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires localDeviceId', () => {
    const values: Record<string, unknown> = { ...bgpSessionInput };
    delete values.localDeviceId;
    const result = validateBgpSession(values);
    expect(result.ok).toBe(false);
    expect(result.errors.localDeviceId).toBeDefined();
  });

  it('rejects malformed localDeviceId', () => {
    const result = validateBgpSession({ ...bgpSessionInput, localDeviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.localDeviceId).toContain('UUID');
  });

  it('rejects peerAddress longer than 255', () => {
    const result = validateBgpSession({ ...bgpSessionInput, peerAddress: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.peerAddress).toContain('Peer Address');
  });

  it('requires peerAddress', () => {
    const values: Record<string, unknown> = { ...bgpSessionInput };
    delete values.peerAddress;
    const result = validateBgpSession(values);
    expect(result.ok).toBe(false);
    expect(result.errors.peerAddress).toBeDefined();
  });

  it('requires localAsn', () => {
    const values: Record<string, unknown> = { ...bgpSessionInput };
    delete values.localAsn;
    const result = validateBgpSession(values);
    expect(result.ok).toBe(false);
    expect(result.errors.localAsn).toBeDefined();
  });

  it('rejects negative localAsn', () => {
    const result = validateBgpSession({ ...bgpSessionInput, localAsn: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.localAsn).toContain('negative');
  });

  it('requires remoteAsn', () => {
    const values: Record<string, unknown> = { ...bgpSessionInput };
    delete values.remoteAsn;
    const result = validateBgpSession(values);
    expect(result.ok).toBe(false);
    expect(result.errors.remoteAsn).toBeDefined();
  });

  it('rejects negative remoteAsn', () => {
    const result = validateBgpSession({ ...bgpSessionInput, remoteAsn: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.remoteAsn).toContain('negative');
  });

  it('rejects unknown sessionState', () => {
    const result = validateBgpSession({ ...bgpSessionInput, sessionState: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative prefixLimit', () => {
    const result = validateBgpSession({ ...bgpSessionInput, prefixLimit: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.prefixLimit).toContain('negative');
  });

  it('rejects region longer than 255', () => {
    const result = validateBgpSession({ ...bgpSessionInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateBgpSession({ ...bgpSessionInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateBgpSession({ ...bgpSessionInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateBgpSession({ ...bgpSessionInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateBgpSession({ ...bgpSessionInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
