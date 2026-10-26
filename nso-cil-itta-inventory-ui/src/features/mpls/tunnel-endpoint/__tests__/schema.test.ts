import { describe, expect, it } from 'vitest';
import { cleanTunnelEndpointValues, validateTunnelEndpoint } from '../schema';
import { tunnelEndpointInput } from './fixtures';

describe('tunnelEndpoint schema', () => {
  it('accepts the fixture', () => {
    expect(validateTunnelEndpoint(tunnelEndpointInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanTunnelEndpointValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...tunnelEndpointInput };
    delete values.code;
    const result = validateTunnelEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...tunnelEndpointInput };
    delete values.name;
    const result = validateTunnelEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...tunnelEndpointInput };
    delete values.status;
    const result = validateTunnelEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires deviceId', () => {
    const values: Record<string, unknown> = { ...tunnelEndpointInput };
    delete values.deviceId;
    const result = validateTunnelEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toBeDefined();
  });

  it('rejects malformed deviceId', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, deviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.deviceId).toContain('UUID');
  });

  it('rejects tunnelName longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, tunnelName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.tunnelName).toContain('Tunnel Name');
  });

  it('requires tunnelName', () => {
    const values: Record<string, unknown> = { ...tunnelEndpointInput };
    delete values.tunnelName;
    const result = validateTunnelEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.tunnelName).toBeDefined();
  });

  it('rejects unknown tunnelType', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, tunnelType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceIp longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, sourceIp: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceIp).toContain('Source Ip');
  });

  it('rejects destinationIp longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, destinationIp: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.destinationIp).toContain('Destination Ip');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateTunnelEndpoint({ ...tunnelEndpointInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
