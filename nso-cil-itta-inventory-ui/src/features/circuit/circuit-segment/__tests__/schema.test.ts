import { describe, expect, it } from 'vitest';
import { cleanCircuitSegmentValues, validateCircuitSegment } from '../schema';
import { circuitSegmentInput } from './fixtures';

describe('circuitSegment schema', () => {
  it('accepts the fixture', () => {
    expect(validateCircuitSegment(circuitSegmentInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCircuitSegmentValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...circuitSegmentInput };
    delete values.code;
    const result = validateCircuitSegment(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...circuitSegmentInput };
    delete values.name;
    const result = validateCircuitSegment(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...circuitSegmentInput };
    delete values.status;
    const result = validateCircuitSegment(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires circuitId', () => {
    const values: Record<string, unknown> = { ...circuitSegmentInput };
    delete values.circuitId;
    const result = validateCircuitSegment(values);
    expect(result.ok).toBe(false);
    expect(result.errors.circuitId).toBeDefined();
  });

  it('rejects malformed circuitId', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, circuitId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.circuitId).toContain('UUID');
  });

  it('requires segmentOrder', () => {
    const values: Record<string, unknown> = { ...circuitSegmentInput };
    delete values.segmentOrder;
    const result = validateCircuitSegment(values);
    expect(result.ok).toBe(false);
    expect(result.errors.segmentOrder).toBeDefined();
  });

  it('rejects negative segmentOrder', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, segmentOrder: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.segmentOrder).toContain('negative');
  });

  it('rejects malformed aTerminationId', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, aTerminationId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.aTerminationId).toContain('UUID');
  });

  it('rejects malformed zTerminationId', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, zTerminationId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.zTerminationId).toContain('UUID');
  });

  it('rejects negative latencyMs', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, latencyMs: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.latencyMs).toContain('negative');
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });

  it('rejects unknown criticality', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateCircuitSegment({ ...circuitSegmentInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });
});
