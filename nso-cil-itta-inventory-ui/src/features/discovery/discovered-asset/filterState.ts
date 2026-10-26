import type { LifecycleStatus } from '@/shared/types';
import type { DiscoveredAssetFilter, DiscoveredAssetLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toDiscoveredAssetQuery(filter: DiscoveredAssetFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.jobId) query.jobId = filter.jobId;
  if (filter.assetType) query.assetType = filter.assetType;
  if (filter.fingerprint) query.fingerprint = filter.fingerprint;
  if (filter.matchedAssetId) query.matchedAssetId = filter.matchedAssetId;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseDiscoveredAssetFilter(params: URLSearchParams): DiscoveredAssetFilter {
  const filter: DiscoveredAssetFilter = {};
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
  const jobId = params.get('jobId');
  if (jobId) filter.jobId = jobId;
  const assetType = params.get('assetType');
  if (assetType) filter.assetType = assetType;
  const fingerprint = params.get('fingerprint');
  if (fingerprint) filter.fingerprint = fingerprint;
  const matchedAssetId = params.get('matchedAssetId');
  if (matchedAssetId) filter.matchedAssetId = matchedAssetId;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as DiscoveredAssetLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeDiscoveredAssetFilter(filter: DiscoveredAssetFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.jobId) params.set('jobId', filter.jobId);
  if (filter.assetType) params.set('assetType', filter.assetType);
  if (filter.fingerprint) params.set('fingerprint', filter.fingerprint);
  if (filter.matchedAssetId) params.set('matchedAssetId', filter.matchedAssetId);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countDiscoveredAssetActiveFilters(filter: DiscoveredAssetFilter): number {
  return Array.from(serializeDiscoveredAssetFilter(filter).keys()).length;
}
