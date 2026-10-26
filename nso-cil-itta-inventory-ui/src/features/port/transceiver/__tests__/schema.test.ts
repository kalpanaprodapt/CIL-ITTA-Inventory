import { describe, expect, it } from 'vitest';
import { cleanTransceiverValues, validateTransceiver } from '../schema';
import { transceiverInput } from './fixtures';

describe('transceiver schema', () => {
  it('accepts the fixture', () => {
    expect(validateTransceiver(transceiverInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanTransceiverValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...transceiverInput };
    delete values.code;
    const result = validateTransceiver(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...transceiverInput };
    delete values.name;
    const result = validateTransceiver(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateTransceiver({ ...transceiverInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...transceiverInput };
    delete values.status;
    const result = validateTransceiver(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateTransceiver({ ...transceiverInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects malformed portId', () => {
    const result = validateTransceiver({ ...transceiverInput, portId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.portId).toContain('UUID');
  });

  it('rejects unknown formFactor', () => {
    const result = validateTransceiver({ ...transceiverInput, formFactor: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative wavelengthNm', () => {
    const result = validateTransceiver({ ...transceiverInput, wavelengthNm: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.wavelengthNm).toContain('negative');
  });

  it('rejects negative reachKm', () => {
    const result = validateTransceiver({ ...transceiverInput, reachKm: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.reachKm).toContain('negative');
  });

  it('rejects serialNumber longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, serialNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.serialNumber).toContain('Serial Number');
  });

  it('rejects vendorPart longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, vendorPart: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.vendorPart).toContain('Vendor Part');
  });

  it('rejects labels longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateTransceiver({ ...transceiverInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateTransceiver({ ...transceiverInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
