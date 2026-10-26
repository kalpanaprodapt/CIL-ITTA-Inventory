import { describe, expect, it } from 'vitest';
import { cleanCircuitValues, validateCircuit } from '../schema';
import { circuitInput } from './fixtures';

describe('circuit schema', () => {
  it('accepts the fixture', () => {
    expect(validateCircuit(circuitInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCircuitValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...circuitInput };
    delete values.code;
    const result = validateCircuit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...circuitInput };
    delete values.name;
    const result = validateCircuit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCircuit({ ...circuitInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...circuitInput };
    delete values.status;
    const result = validateCircuit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCircuit({ ...circuitInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects circuitId longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, circuitId: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.circuitId).toContain('Circuit Id');
  });

  it('requires circuitId', () => {
    const values: Record<string, unknown> = { ...circuitInput };
    delete values.circuitId;
    const result = validateCircuit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.circuitId).toBeDefined();
  });

  it('rejects unknown circuitType', () => {
    const result = validateCircuit({ ...circuitInput, circuitType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires bandwidthMbps', () => {
    const values: Record<string, unknown> = { ...circuitInput };
    delete values.bandwidthMbps;
    const result = validateCircuit(values);
    expect(result.ok).toBe(false);
    expect(result.errors.bandwidthMbps).toBeDefined();
  });

  it('rejects negative bandwidthMbps', () => {
    const result = validateCircuit({ ...circuitInput, bandwidthMbps: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.bandwidthMbps).toContain('negative');
  });

  it('rejects malformed providerId', () => {
    const result = validateCircuit({ ...circuitInput, providerId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.providerId).toContain('UUID');
  });

  it('rejects malformed aSiteId', () => {
    const result = validateCircuit({ ...circuitInput, aSiteId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.aSiteId).toContain('UUID');
  });

  it('rejects malformed zSiteId', () => {
    const result = validateCircuit({ ...circuitInput, zSiteId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.zSiteId).toContain('UUID');
  });

  it('rejects labels longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateCircuit({ ...circuitInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects owner longer than 255', () => {
    const result = validateCircuit({ ...circuitInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
