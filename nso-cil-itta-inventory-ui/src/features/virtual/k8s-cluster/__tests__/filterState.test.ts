import { describe, expect, it } from 'vitest';
import { countK8sClusterActiveFilters, parseK8sClusterFilter, serializeK8sClusterFilter, toK8sClusterQuery } from '../filterState';

describe('k8sCluster filter state', () => {
  it('is empty by default', () => {
    expect(parseK8sClusterFilter(new URLSearchParams())).toEqual({});
    expect(countK8sClusterActiveFilters({})).toBe(0);
  });

  it('round trips free text and status', () => {
    const filter = { q: 'core', status: ['ACTIVE' as const, 'PLANNED' as const] };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).status).toBe('ACTIVE,PLANNED');
  });

  it('round trips code', () => {
    const filter = { code: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).code).toBe('abc');
  });

  it('round trips name', () => {
    const filter = { name: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).name).toBe('abc');
  });

  it('round trips externalRef', () => {
    const filter = { externalRef: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).externalRef).toBe('abc');
  });

  it('round trips clusterName', () => {
    const filter = { clusterName: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).clusterName).toBe('abc');
  });

  it('round trips clusterVersion', () => {
    const filter = { clusterVersion: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).clusterVersion).toBe('abc');
  });

  it('round trips nodeCount range', () => {
    const filter = { nodeCountMin: 1, nodeCountMax: 9 };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).nodeCountMax).toBe(9);
  });

  it('round trips cloudAccountId', () => {
    const filter = { cloudAccountId: '00000000-0000-4000-8000-000000000011' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
  });

  it('round trips region', () => {
    const filter = { region: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).region).toBe('abc');
  });

  it('round trips apiEndpoint', () => {
    const filter = { apiEndpoint: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).apiEndpoint).toBe('abc');
  });

  it('round trips effectiveTo window', () => {
    const filter = { effectiveToFrom: '2026-01-01', effectiveToTo: '2026-02-01' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
  });

  it('round trips labels', () => {
    const filter = { labels: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).labels).toBe('abc');
  });

  it('round trips lifecycleStage selection', () => {
    const filter = { lifecycleStage: ['PLAN'] as never };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).lifecycleStage).toBe('PLAN');
  });

  it('round trips sourceSystem', () => {
    const filter = { sourceSystem: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).sourceSystem).toBe('abc');
  });

  it('round trips owner', () => {
    const filter = { owner: 'abc' };
    expect(parseK8sClusterFilter(serializeK8sClusterFilter(filter))).toEqual(filter);
    expect(toK8sClusterQuery(filter).owner).toBe('abc');
  });
});
