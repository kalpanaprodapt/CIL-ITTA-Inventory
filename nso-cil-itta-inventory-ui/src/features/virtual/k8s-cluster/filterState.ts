import type { LifecycleStatus } from '@/shared/types';
import type { K8sClusterFilter, K8sClusterLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toK8sClusterQuery(filter: K8sClusterFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.clusterName) query.clusterName = filter.clusterName;
  if (filter.clusterVersion) query.clusterVersion = filter.clusterVersion;
  if (filter.nodeCountMin !== undefined) query.nodeCountMin = filter.nodeCountMin;
  if (filter.nodeCountMax !== undefined) query.nodeCountMax = filter.nodeCountMax;
  if (filter.cloudAccountId) query.cloudAccountId = filter.cloudAccountId;
  if (filter.region) query.region = filter.region;
  if (filter.apiEndpoint) query.apiEndpoint = filter.apiEndpoint;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseK8sClusterFilter(params: URLSearchParams): K8sClusterFilter {
  const filter: K8sClusterFilter = {};
  const q = params.get('q');
  if (q) filter.q = q;
  const status = params.get('status');
  if (status) filter.status = status.split(',') as LifecycleStatus[];
  const code = params.get('code');
  if (code) filter.code = code;
  const name = params.get('name');
  if (name) filter.name = name;
  const externalRef = params.get('externalRef');
  if (externalRef) filter.externalRef = externalRef;
  const clusterName = params.get('clusterName');
  if (clusterName) filter.clusterName = clusterName;
  const clusterVersion = params.get('clusterVersion');
  if (clusterVersion) filter.clusterVersion = clusterVersion;
  const nodeCountMin = params.get('nodeCountMin');
  if (nodeCountMin !== null && nodeCountMin !== '') filter.nodeCountMin = Number(nodeCountMin);
  const nodeCountMax = params.get('nodeCountMax');
  if (nodeCountMax !== null && nodeCountMax !== '') filter.nodeCountMax = Number(nodeCountMax);
  const cloudAccountId = params.get('cloudAccountId');
  if (cloudAccountId) filter.cloudAccountId = cloudAccountId;
  const region = params.get('region');
  if (region) filter.region = region;
  const apiEndpoint = params.get('apiEndpoint');
  if (apiEndpoint) filter.apiEndpoint = apiEndpoint;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as K8sClusterLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeK8sClusterFilter(filter: K8sClusterFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.clusterName) params.set('clusterName', filter.clusterName);
  if (filter.clusterVersion) params.set('clusterVersion', filter.clusterVersion);
  if (filter.nodeCountMin !== undefined) params.set('nodeCountMin', String(filter.nodeCountMin));
  if (filter.nodeCountMax !== undefined) params.set('nodeCountMax', String(filter.nodeCountMax));
  if (filter.cloudAccountId) params.set('cloudAccountId', filter.cloudAccountId);
  if (filter.region) params.set('region', filter.region);
  if (filter.apiEndpoint) params.set('apiEndpoint', filter.apiEndpoint);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countK8sClusterActiveFilters(filter: K8sClusterFilter): number {
  return Array.from(serializeK8sClusterFilter(filter).keys()).length;
}
