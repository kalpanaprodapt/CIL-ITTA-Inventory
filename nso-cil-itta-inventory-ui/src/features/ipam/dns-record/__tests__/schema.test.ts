import { describe, expect, it } from 'vitest';
import { cleanDnsRecordValues, validateDnsRecord } from '../schema';
import { dnsRecordInput } from './fixtures';

describe('dnsRecord schema', () => {
  it('accepts the fixture', () => {
    expect(validateDnsRecord(dnsRecordInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanDnsRecordValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...dnsRecordInput };
    delete values.code;
    const result = validateDnsRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...dnsRecordInput };
    delete values.name;
    const result = validateDnsRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...dnsRecordInput };
    delete values.status;
    const result = validateDnsRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects zoneName longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, zoneName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.zoneName).toContain('Zone Name');
  });

  it('requires zoneName', () => {
    const values: Record<string, unknown> = { ...dnsRecordInput };
    delete values.zoneName;
    const result = validateDnsRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.zoneName).toBeDefined();
  });

  it('rejects unknown recordType', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, recordType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects recordValue longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, recordValue: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.recordValue).toContain('Record Value');
  });

  it('requires recordValue', () => {
    const values: Record<string, unknown> = { ...dnsRecordInput };
    delete values.recordValue;
    const result = validateDnsRecord(values);
    expect(result.ok).toBe(false);
    expect(result.errors.recordValue).toBeDefined();
  });

  it('rejects negative ttl', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, ttl: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.ttl).toContain('negative');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateDnsRecord({ ...dnsRecordInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
