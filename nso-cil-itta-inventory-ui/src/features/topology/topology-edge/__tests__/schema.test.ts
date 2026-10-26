import { describe, expect, it } from 'vitest';
import { cleanTopologyEdgeValues, validateTopologyEdge } from '../schema';
import { topologyEdgeInput } from './fixtures';

describe('topologyEdge schema', () => {
  it('accepts the fixture', () => {
    expect(validateTopologyEdge(topologyEdgeInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanTopologyEdgeValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...topologyEdgeInput };
    delete values.code;
    const result = validateTopologyEdge(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...topologyEdgeInput };
    delete values.name;
    const result = validateTopologyEdge(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...topologyEdgeInput };
    delete values.status;
    const result = validateTopologyEdge(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('requires viewId', () => {
    const values: Record<string, unknown> = { ...topologyEdgeInput };
    delete values.viewId;
    const result = validateTopologyEdge(values);
    expect(result.ok).toBe(false);
    expect(result.errors.viewId).toBeDefined();
  });

  it('rejects malformed viewId', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, viewId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.viewId).toContain('UUID');
  });

  it('requires sourceNodeId', () => {
    const values: Record<string, unknown> = { ...topologyEdgeInput };
    delete values.sourceNodeId;
    const result = validateTopologyEdge(values);
    expect(result.ok).toBe(false);
    expect(result.errors.sourceNodeId).toBeDefined();
  });

  it('rejects malformed sourceNodeId', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, sourceNodeId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceNodeId).toContain('UUID');
  });

  it('requires targetNodeId', () => {
    const values: Record<string, unknown> = { ...topologyEdgeInput };
    delete values.targetNodeId;
    const result = validateTopologyEdge(values);
    expect(result.ok).toBe(false);
    expect(result.errors.targetNodeId).toBeDefined();
  });

  it('rejects malformed targetNodeId', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, targetNodeId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.targetNodeId).toContain('UUID');
  });

  it('rejects negative weight', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, weight: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.weight).toContain('negative');
  });

  it('rejects notes longer than 4000', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, notes: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.notes).toContain('Notes');
  });

  it('rejects malformed date in effectiveFrom', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, effectiveFrom: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateTopologyEdge({ ...topologyEdgeInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });
});
