import { describe, expect, it } from 'vitest';
import { cleanServiceOrderValues, validateServiceOrder } from '../schema';
import { serviceOrderInput } from './fixtures';

describe('serviceOrder schema', () => {
  it('accepts the fixture', () => {
    expect(validateServiceOrder(serviceOrderInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanServiceOrderValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...serviceOrderInput };
    delete values.code;
    const result = validateServiceOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...serviceOrderInput };
    delete values.name;
    const result = validateServiceOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...serviceOrderInput };
    delete values.status;
    const result = validateServiceOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires customerId', () => {
    const values: Record<string, unknown> = { ...serviceOrderInput };
    delete values.customerId;
    const result = validateServiceOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toBeDefined();
  });

  it('rejects malformed customerId', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, customerId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.customerId).toContain('UUID');
  });

  it('rejects unknown orderType', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, orderType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires requestedOn', () => {
    const values: Record<string, unknown> = { ...serviceOrderInput };
    delete values.requestedOn;
    const result = validateServiceOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.requestedOn).toBeDefined();
  });

  it('rejects malformed date in requestedOn', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, requestedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in targetDate', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, targetDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects negative priority', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, priority: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.priority).toContain('negative');
  });

  it('rejects region longer than 255', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateServiceOrder({ ...serviceOrderInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });
});
