import { describe, expect, it } from 'vitest';
import { cleanTopologyViewValues, validateTopologyView } from '../schema';
import { topologyViewInput } from './fixtures';

describe('topologyView schema', () => {
  it('accepts the fixture', () => {
    expect(validateTopologyView(topologyViewInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanTopologyViewValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...topologyViewInput };
    delete values.code;
    const result = validateTopologyView(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...topologyViewInput };
    delete values.name;
    const result = validateTopologyView(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateTopologyView({ ...topologyViewInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...topologyViewInput };
    delete values.status;
    const result = validateTopologyView(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateTopologyView({ ...topologyViewInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects viewName longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, viewName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.viewName).toContain('View Name');
  });

  it('requires viewName', () => {
    const values: Record<string, unknown> = { ...topologyViewInput };
    delete values.viewName;
    const result = validateTopologyView(values);
    expect(result.ok).toBe(false);
    expect(result.errors.viewName).toBeDefined();
  });

  it('rejects unknown viewType', () => {
    const result = validateTopologyView({ ...topologyViewInput, viewType: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects scopeFilter longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, scopeFilter: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.scopeFilter).toContain('Scope Filter');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateTopologyView({ ...topologyViewInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateTopologyView({ ...topologyViewInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects region longer than 255', () => {
    const result = validateTopologyView({ ...topologyViewInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });
});
