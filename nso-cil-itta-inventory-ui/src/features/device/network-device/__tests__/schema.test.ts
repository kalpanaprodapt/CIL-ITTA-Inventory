import { describe, expect, it } from 'vitest';
import { cleanNetworkDeviceValues, validateNetworkDevice } from '../schema';
import { networkDeviceInput } from './fixtures';

describe('networkDevice schema', () => {
  it('accepts the fixture', () => {
    expect(validateNetworkDevice(networkDeviceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanNetworkDeviceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...networkDeviceInput };
    delete values.code;
    const result = validateNetworkDevice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...networkDeviceInput };
    delete values.name;
    const result = validateNetworkDevice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...networkDeviceInput };
    delete values.status;
    const result = validateNetworkDevice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects hostname longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, hostname: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.hostname).toContain('Hostname');
  });

  it('requires hostname', () => {
    const values: Record<string, unknown> = { ...networkDeviceInput };
    delete values.hostname;
    const result = validateNetworkDevice(values);
    expect(result.ok).toBe(false);
    expect(result.errors.hostname).toBeDefined();
  });

  it('rejects unknown deviceRole', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, deviceRole: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects mgmtIp longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, mgmtIp: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.mgmtIp).toContain('Mgmt Ip');
  });

  it('rejects serialNumber longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, serialNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.serialNumber).toContain('Serial Number');
  });

  it('rejects malformed modelId', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, modelId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.modelId).toContain('UUID');
  });

  it('rejects malformed rackUnitId', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, rackUnitId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.rackUnitId).toContain('UUID');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateNetworkDevice({ ...networkDeviceInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
