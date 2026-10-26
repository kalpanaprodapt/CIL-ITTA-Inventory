import { describe, expect, it } from 'vitest';
import { cleanK8sClusterValues, validateK8sCluster } from '../schema';
import { k8sClusterInput } from './fixtures';

describe('k8sCluster schema', () => {
  it('accepts the fixture', () => {
    expect(validateK8sCluster(k8sClusterInput)).toEqual({ ok: true, errors: {} });
  });

  it('drops empty strings and NaN when cleaning', () => {
    expect(cleanK8sClusterValues({ a: '', b: Number.NaN, c: 1, d: null })).toEqual({ c: 1 });
  });

  it('rejects code longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, code: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.code).toContain('Code');
  });

  it('requires code', () => {
    const values: Record<string, unknown> = { ...k8sClusterInput };
    delete values.code;
    const result = validateK8sCluster(values);
    expect(result.ok).toBe(false);
    expect(result.errors.code).toBeDefined();
  });

  it('rejects name longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, name: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.name).toContain('Name');
  });

  it('requires name', () => {
    const values: Record<string, unknown> = { ...k8sClusterInput };
    delete values.name;
    const result = validateK8sCluster(values);
    expect(result.ok).toBe(false);
    expect(result.errors.name).toBeDefined();
  });

  it('rejects description longer than 4000', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, description: 'x'.repeat(4001) });
    expect(result.ok).toBe(false);
    expect(result.errors.description).toContain('Description');
  });

  it('requires status', () => {
    const values: Record<string, unknown> = { ...k8sClusterInput };
    delete values.status;
    const result = validateK8sCluster(values);
    expect(result.ok).toBe(false);
    expect(result.errors.status).toBeDefined();
  });

  it('rejects unknown status', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, status: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects externalRef longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, externalRef: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.externalRef).toContain('External Ref');
  });

  it('rejects clusterName longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, clusterName: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.clusterName).toContain('Cluster Name');
  });

  it('requires clusterName', () => {
    const values: Record<string, unknown> = { ...k8sClusterInput };
    delete values.clusterName;
    const result = validateK8sCluster(values);
    expect(result.ok).toBe(false);
    expect(result.errors.clusterName).toBeDefined();
  });

  it('rejects clusterVersion longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, clusterVersion: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.clusterVersion).toContain('Cluster Version');
  });

  it('requires clusterVersion', () => {
    const values: Record<string, unknown> = { ...k8sClusterInput };
    delete values.clusterVersion;
    const result = validateK8sCluster(values);
    expect(result.ok).toBe(false);
    expect(result.errors.clusterVersion).toBeDefined();
  });

  it('requires nodeCount', () => {
    const values: Record<string, unknown> = { ...k8sClusterInput };
    delete values.nodeCount;
    const result = validateK8sCluster(values);
    expect(result.ok).toBe(false);
    expect(result.errors.nodeCount).toBeDefined();
  });

  it('rejects negative nodeCount', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, nodeCount: -1 });
    expect(result.ok).toBe(false);
    expect(result.errors.nodeCount).toContain('negative');
  });

  it('rejects malformed cloudAccountId', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, cloudAccountId: 'not-a-uuid' });
    expect(result.ok).toBe(false);
    expect(result.errors.cloudAccountId).toContain('UUID');
  });

  it('rejects region longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, region: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.region).toContain('Region');
  });

  it('rejects apiEndpoint longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, apiEndpoint: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.apiEndpoint).toContain('Api Endpoint');
  });

  it('rejects malformed date in effectiveTo', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, effectiveTo: '31/12/2026' });
    expect(result.ok).toBe(false);
  });

  it('rejects labels longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, labels: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.labels).toContain('Labels');
  });

  it('rejects unknown lifecycleStage', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, lifecycleStage: 'NOT_A_VALUE' });
    expect(result.ok).toBe(false);
  });

  it('rejects sourceSystem longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, sourceSystem: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.sourceSystem).toContain('Source System');
  });

  it('rejects owner longer than 255', () => {
    const result = validateK8sCluster({ ...k8sClusterInput, owner: 'x'.repeat(256) });
    expect(result.ok).toBe(false);
    expect(result.errors.owner).toContain('Owner');
  });
});
