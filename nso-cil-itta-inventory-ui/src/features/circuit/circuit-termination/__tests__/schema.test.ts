import { describe, expect, it } from 'vitest';
import { cleanCircuitTerminationValues, validateCircuitTermination } from '../schema';
import { circuitTerminationInput } from './fixtures';

describe('circuitTermination schema', () => {
  it('accepts the fixture', () => {
    expect(validateCircuitTermination(circuitTerminationInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCircuitTerminationValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...circuitTerminationInput };
    delete values.code;
    const result = validateCircuitTermination(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...circuitTerminationInput };
    delete values.name;
    const result = validateCircuitTermination(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...circuitTerminationInput };
    delete values.status;
    const result = validateCircuitTermination(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires circuitId', () => {
    const values: Record<string, unknown> = { ...circuitTerminationInput };
    delete values.circuitId;
    const result = validateCircuitTermination(values);
    expect(result.ok).toBe(false);
    expect(result.errors.circuitId).toBeDefined();
  });

  it('rejects malformed circuitId', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, circuitId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.circuitId).toContain('UUID');
  });

  it('rejects unknown side', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, side: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('requires portId', () => {
    const values: Record<string, unknown> = { ...circuitTerminationInput };
    delete values.portId;
    const result = validateCircuitTermination(values);
    expect(result.ok).toBe(false);
    expect(result.errors.portId).toBeDefined();
  });

  it('rejects malformed portId', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, portId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.portId).toContain('UUID');
  });

  it('rejects xconnectId longer than 255', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, xconnectId: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.xconnectId).toContain('Xconnect Id');
  });

  it('rejects patchInfo longer than 255', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, patchInfo: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.patchInfo).toContain('Patch Info');
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCircuitTermination({ ...circuitTerminationInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });
});
