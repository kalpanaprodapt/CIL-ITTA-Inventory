import { describe, expect, it } from 'vitest';
import { cleanRouteTargetValues, validateRouteTarget } from '../schema';
import { routeTargetInput } from './fixtures';

describe('routeTarget schema', () => {
  it('accepts the fixture', () => {
    expect(validateRouteTarget(routeTargetInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanRouteTargetValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateRouteTarget({ ...routeTargetInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...routeTargetInput };
    delete values.code;
    const result = validateRouteTarget(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateRouteTarget({ ...routeTargetInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...routeTargetInput };
    delete values.name;
    const result = validateRouteTarget(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateRouteTarget({ ...routeTargetInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...routeTargetInput };
    delete values.status;
    const result = validateRouteTarget(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateRouteTarget({ ...routeTargetInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateRouteTarget({ ...routeTargetInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires asn', () => {
    const values: Record<string, unknown> = { ...routeTargetInput };
    delete values.asn;
    const result = validateRouteTarget(values);
    expect(result.ok).toBe(false);
    expect(result.errors.asn).toBeDefined();
  });

  it('rejects negative asn', () => {
    const result = validateRouteTarget({ ...routeTargetInput, asn: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.asn).toContain('negative');
  });

  it('requires assignedNumber', () => {
    const values: Record<string, unknown> = { ...routeTargetInput };
    delete values.assignedNumber;
    const result = validateRouteTarget(values);
    expect(result.ok).toBe(false);
    expect(result.errors.assignedNumber).toBeDefined();
  });

  it('rejects negative assignedNumber', () => {
    const result = validateRouteTarget({ ...routeTargetInput, assignedNumber: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.assignedNumber).toContain('negative');
  });

  it('rejects unknown rtType', () => {
    const result = validateRouteTarget({ ...routeTargetInput, rtType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed vrfId', () => {
    const result = validateRouteTarget({ ...routeTargetInput, vrfId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.vrfId).toContain('UUID');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateRouteTarget({ ...routeTargetInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateRouteTarget({ ...routeTargetInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateRouteTarget({ ...routeTargetInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateRouteTarget({ ...routeTargetInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateRouteTarget({ ...routeTargetInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
