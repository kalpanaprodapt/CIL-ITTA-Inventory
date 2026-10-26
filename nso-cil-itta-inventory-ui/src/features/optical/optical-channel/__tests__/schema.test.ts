import { describe, expect, it } from 'vitest';
import { cleanOpticalChannelValues, validateOpticalChannel } from '../schema';
import { opticalChannelInput } from './fixtures';

describe('opticalChannel schema', () => {
  it('accepts the fixture', () => {
    expect(validateOpticalChannel(opticalChannelInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanOpticalChannelValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...opticalChannelInput };
    delete values.code;
    const result = validateOpticalChannel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...opticalChannelInput };
    delete values.name;
    const result = validateOpticalChannel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...opticalChannelInput };
    delete values.status;
    const result = validateOpticalChannel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires wavelengthNm', () => {
    const values: Record<string, unknown> = { ...opticalChannelInput };
    delete values.wavelengthNm;
    const result = validateOpticalChannel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.wavelengthNm).toBeDefined();
  });

  it('rejects negative wavelengthNm', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, wavelengthNm: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.wavelengthNm).toContain('negative');
  });

  it('requires channelNumber', () => {
    const values: Record<string, unknown> = { ...opticalChannelInput };
    delete values.channelNumber;
    const result = validateOpticalChannel(values);
    expect(result.ok).toBe(false);
    expect(result.errors.channelNumber).toBeDefined();
  });

  it('rejects negative channelNumber', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, channelNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.channelNumber).toContain('negative');
  });

  it('rejects unknown gridType', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, gridType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative bitrateGbps', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, bitrateGbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.bitrateGbps).toContain('negative');
  });

  it('rejects malformed systemId', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, systemId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.systemId).toContain('UUID');
  });

  it('rejects unknown criticality', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateOpticalChannel({ ...opticalChannelInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
