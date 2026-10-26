import type { LifecycleStatus } from '@/shared/types';
import type { TopologyEdgeFilter, TopologyEdgeLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toTopologyEdgeQuery(filter: TopologyEdgeFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.viewId) query.viewId = filter.viewId;
  if (filter.sourceNodeId) query.sourceNodeId = filter.sourceNodeId;
  if (filter.targetNodeId) query.targetNodeId = filter.targetNodeId;
  if (filter.weightMin !== undefined) query.weightMin = filter.weightMin;
  if (filter.weightMax !== undefined) query.weightMax = filter.weightMax;
  if (filter.directed !== undefined) query.directed = filter.directed;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseTopologyEdgeFilter(params: URLSearchParams): TopologyEdgeFilter {
  const filter: TopologyEdgeFilter = {};
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
  const viewId = params.get('viewId');
  if (viewId) filter.viewId = viewId;
  const sourceNodeId = params.get('sourceNodeId');
  if (sourceNodeId) filter.sourceNodeId = sourceNodeId;
  const targetNodeId = params.get('targetNodeId');
  if (targetNodeId) filter.targetNodeId = targetNodeId;
  const weightMin = params.get('weightMin');
  if (weightMin !== null && weightMin !== '') filter.weightMin = Number(weightMin);
  const weightMax = params.get('weightMax');
  if (weightMax !== null && weightMax !== '') filter.weightMax = Number(weightMax);
  const directed = params.get('directed');
  if (directed !== null) filter.directed = directed === 'true';
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as TopologyEdgeLifecycleStage[];
  return filter;
}

export function serializeTopologyEdgeFilter(filter: TopologyEdgeFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.viewId) params.set('viewId', filter.viewId);
  if (filter.sourceNodeId) params.set('sourceNodeId', filter.sourceNodeId);
  if (filter.targetNodeId) params.set('targetNodeId', filter.targetNodeId);
  if (filter.weightMin !== undefined) params.set('weightMin', String(filter.weightMin));
  if (filter.weightMax !== undefined) params.set('weightMax', String(filter.weightMax));
  if (filter.directed !== undefined) params.set('directed', String(filter.directed));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countTopologyEdgeActiveFilters(filter: TopologyEdgeFilter): number {
  return Array.from(serializeTopologyEdgeFilter(filter).keys()).length;
}
