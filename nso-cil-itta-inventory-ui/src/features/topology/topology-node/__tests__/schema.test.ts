import { describe, expect, it } from 'vitest';
import { cleanTopologyNodeValues, validateTopologyNode } from '../schema';
import { topologyNodeInput } from './fixtures';

describe('topologyNode schema', () => {
  it('accepts the fixture', () => {
    expect(validateTopologyNode(topologyNodeInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanTopologyNodeValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...topologyNodeInput };
    delete values.code;
    const result = validateTopologyNode(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...topologyNodeInput };
    delete values.name;
    const result = validateTopologyNode(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...topologyNodeInput };
    delete values.status;
    const result = validateTopologyNode(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires viewId', () => {
    const values: Record<string, unknown> = { ...topologyNodeInput };
    delete values.viewId;
    const result = validateTopologyNode(values);
    expect(result.ok).toBe(false);
    expect(result.errors.viewId).toBeDefined();
  });

  it('rejects malformed viewId', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, viewId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.viewId).toContain('UUID');
  });

  it('rejects entityRef longer than 255', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, entityRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.entityRef).toContain('Entity Ref');
  });

  it('requires entityRef', () => {
    const values: Record<string, unknown> = { ...topologyNodeInput };
    delete values.entityRef;
    const result = validateTopologyNode(values);
    expect(result.ok).toBe(false);
    expect(result.errors.entityRef).toBeDefined();
  });

  it('rejects nodeType longer than 255', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, nodeType: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.nodeType).toContain('Node Type');
  });

  it('rejects negative layer', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, layer: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.layer).toContain('negative');
  });

  it('rejects unknown criticality', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, criticality: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects costCenter longer than 255', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, costCenter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.costCenter).toContain('Cost Center');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateTopologyNode({ ...topologyNodeInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });
});
