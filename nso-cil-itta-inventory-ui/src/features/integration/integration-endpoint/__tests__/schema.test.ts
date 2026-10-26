import { describe, expect, it } from 'vitest';
import { cleanIntegrationEndpointValues, validateIntegrationEndpoint } from '../schema';
import { integrationEndpointInput } from './fixtures';

describe('integrationEndpoint schema', () => {
  it('accepts the fixture', () => {
    expect(validateIntegrationEndpoint(integrationEndpointInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanIntegrationEndpointValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...integrationEndpointInput };
    delete values.code;
    const result = validateIntegrationEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...integrationEndpointInput };
    delete values.name;
    const result = validateIntegrationEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...integrationEndpointInput };
    delete values.status;
    const result = validateIntegrationEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects endpointName longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, endpointName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.endpointName).toContain('Endpoint Name');
  });

  it('requires endpointName', () => {
    const values: Record<string, unknown> = { ...integrationEndpointInput };
    delete values.endpointName;
    const result = validateIntegrationEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.endpointName).toBeDefined();
  });

  it('rejects targetSystem longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, targetSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.targetSystem).toContain('Target System');
  });

  it('requires targetSystem', () => {
    const values: Record<string, unknown> = { ...integrationEndpointInput };
    delete values.targetSystem;
    const result = validateIntegrationEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.targetSystem).toBeDefined();
  });

  it('rejects baseUrl longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, baseUrl: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.baseUrl).toContain('Base Url');
  });

  it('requires baseUrl', () => {
    const values: Record<string, unknown> = { ...integrationEndpointInput };
    delete values.baseUrl;
    const result = validateIntegrationEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.baseUrl).toBeDefined();
  });

  it('rejects unknown authType', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, authType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative timeoutMs', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, timeoutMs: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.timeoutMs).toContain('negative');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateIntegrationEndpoint({ ...integrationEndpointInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
