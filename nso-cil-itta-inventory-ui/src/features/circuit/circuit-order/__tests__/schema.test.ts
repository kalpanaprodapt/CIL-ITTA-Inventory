import { describe, expect, it } from 'vitest';
import { cleanCircuitOrderValues, validateCircuitOrder } from '../schema';
import { circuitOrderInput } from './fixtures';

describe('circuitOrder schema', () => {
  it('accepts the fixture', () => {
    expect(validateCircuitOrder(circuitOrderInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanCircuitOrderValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...circuitOrderInput };
    delete values.code;
    const result = validateCircuitOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...circuitOrderInput };
    delete values.name;
    const result = validateCircuitOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...circuitOrderInput };
    delete values.status;
    const result = validateCircuitOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects malformed circuitId', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, circuitId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.circuitId).toContain('UUID');
  });

  it('rejects orderNumber longer than 255', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, orderNumber: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.orderNumber).toContain('Order Number');
  });

  it('requires orderNumber', () => {
    const values: Record<string, unknown> = { ...circuitOrderInput };
    delete values.orderNumber;
    const result = validateCircuitOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.orderNumber).toBeDefined();
  });

  it('requires orderedOn', () => {
    const values: Record<string, unknown> = { ...circuitOrderInput };
    delete values.orderedOn;
    const result = validateCircuitOrder(values);
    expect(result.ok).toBe(false);
    expect(result.errors.orderedOn).toBeDefined();
  });

  it('rejects malformed date in orderedOn', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, orderedOn: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in requestedDate', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, requestedDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in committedDate', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, committedDate: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects unknown orderStatus', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, orderStatus: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateCircuitOrder({ ...circuitOrderInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });
});
