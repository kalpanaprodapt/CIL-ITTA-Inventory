import { describe, expect, it } from 'vitest';
import { cleanPathTraceValues, validatePathTrace } from '../schema';
import { pathTraceInput } from './fixtures';

describe('pathTrace schema', () => {
  it('accepts the fixture', () => {
    expect(validatePathTrace(pathTraceInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanPathTraceValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...pathTraceInput };
    delete values.code;
    const result = validatePathTrace(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...pathTraceInput };
    delete values.name;
    const result = validatePathTrace(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validatePathTrace({ ...pathTraceInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...pathTraceInput };
    delete values.status;
    const result = validatePathTrace(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validatePathTrace({ ...pathTraceInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects sourceRef longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, sourceRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceRef).toContain('Source Ref');
  });

  it('requires sourceRef', () => {
    const values: Record<string, unknown> = { ...pathTraceInput };
    delete values.sourceRef;
    const result = validatePathTrace(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sourceRef).toBeDefined();
  });

  it('rejects targetRef longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, targetRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toContain('Target Ref');
  });

  it('requires targetRef', () => {
    const values: Record<string, unknown> = { ...pathTraceInput };
    delete values.targetRef;
    const result = validatePathTrace(values);
    expect(result.ok).toBe(false);
    expect(result.errors.targetRef).toBeDefined();
  });

  it('rejects negative hopCount', () => {
    const result = validatePathTrace({ ...pathTraceInput, hopCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.hopCount).toContain('negative');
  });

  it('rejects negative totalLatencyMs', () => {
    const result = validatePathTrace({ ...pathTraceInput, totalLatencyMs: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.totalLatencyMs).toContain('negative');
  });

  it('rejects traceMethod longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, traceMethod: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.traceMethod).toContain('Trace Method');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validatePathTrace({ ...pathTraceInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validatePathTrace({ ...pathTraceInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validatePathTrace({ ...pathTraceInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
