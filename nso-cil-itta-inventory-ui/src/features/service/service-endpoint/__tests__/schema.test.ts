import { describe, expect, it } from 'vitest';
import { cleanServiceEndpointValues, validateServiceEndpoint } from '../schema';
import { serviceEndpointInput } from './fixtures';

describe('serviceEndpoint schema', () => {
  it('accepts the fixture', () => {
    expect(validateServiceEndpoint(serviceEndpointInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanServiceEndpointValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...serviceEndpointInput };
    delete values.code;
    const result = validateServiceEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...serviceEndpointInput };
    delete values.name;
    const result = validateServiceEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...serviceEndpointInput };
    delete values.status;
    const result = validateServiceEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires serviceId', () => {
    const values: Record<string, unknown> = { ...serviceEndpointInput };
    delete values.serviceId;
    const result = validateServiceEndpoint(values);
    expect(result.ok).toBe(false);
    expect(result.errors.serviceId).toBeDefined();
  });

  it('rejects malformed serviceId', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, serviceId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.serviceId).toContain('UUID');
  });

  it('rejects malformed siteId', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, siteId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.siteId).toContain('UUID');
  });

  it('rejects malformed portId', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, portId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.portId).toContain('UUID');
  });

  it('rejects unknown endpointRole', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, endpointRole: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects handoff longer than 255', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, handoff: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.handoff).toContain('Handoff');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateServiceEndpoint({ ...serviceEndpointInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
