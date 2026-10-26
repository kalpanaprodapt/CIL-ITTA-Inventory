import type { LifecycleStatus } from '@/shared/types';
import type { TopologyNodeFilter, TopologyNodeCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toTopologyNodeQuery(filter: TopologyNodeFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.viewId) query.viewId = filter.viewId;
  if (filter.entityRef) query.entityRef = filter.entityRef;
  if (filter.nodeType) query.nodeType = filter.nodeType;
  if (filter.xPosMin !== undefined) query.xPosMin = filter.xPosMin;
  if (filter.xPosMax !== undefined) query.xPosMax = filter.xPosMax;
  if (filter.yPosMin !== undefined) query.yPosMin = filter.yPosMin;
  if (filter.yPosMax !== undefined) query.yPosMax = filter.yPosMax;
  if (filter.layerMin !== undefined) query.layerMin = filter.layerMin;
  if (filter.layerMax !== undefined) query.layerMax = filter.layerMax;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseTopologyNodeFilter(params: URLSearchParams): TopologyNodeFilter {
  const filter: TopologyNodeFilter = {};
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
  const entityRef = params.get('entityRef');
  if (entityRef) filter.entityRef = entityRef;
  const nodeType = params.get('nodeType');
  if (nodeType) filter.nodeType = nodeType;
  const xPosMin = params.get('xPosMin');
  if (xPosMin !== null && xPosMin !== '') filter.xPosMin = Number(xPosMin);
  const xPosMax = params.get('xPosMax');
  if (xPosMax !== null && xPosMax !== '') filter.xPosMax = Number(xPosMax);
  const yPosMin = params.get('yPosMin');
  if (yPosMin !== null && yPosMin !== '') filter.yPosMin = Number(yPosMin);
  const yPosMax = params.get('yPosMax');
  if (yPosMax !== null && yPosMax !== '') filter.yPosMax = Number(yPosMax);
  const layerMin = params.get('layerMin');
  if (layerMin !== null && layerMin !== '') filter.layerMin = Number(layerMin);
  const layerMax = params.get('layerMax');
  if (layerMax !== null && layerMax !== '') filter.layerMax = Number(layerMax);
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as TopologyNodeCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  return filter;
}

export function serializeTopologyNodeFilter(filter: TopologyNodeFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.viewId) params.set('viewId', filter.viewId);
  if (filter.entityRef) params.set('entityRef', filter.entityRef);
  if (filter.nodeType) params.set('nodeType', filter.nodeType);
  if (filter.xPosMin !== undefined) params.set('xPosMin', String(filter.xPosMin));
  if (filter.xPosMax !== undefined) params.set('xPosMax', String(filter.xPosMax));
  if (filter.yPosMin !== undefined) params.set('yPosMin', String(filter.yPosMin));
  if (filter.yPosMax !== undefined) params.set('yPosMax', String(filter.yPosMax));
  if (filter.layerMin !== undefined) params.set('layerMin', String(filter.layerMin));
  if (filter.layerMax !== undefined) params.set('layerMax', String(filter.layerMax));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countTopologyNodeActiveFilters(filter: TopologyNodeFilter): number {
  return Array.from(serializeTopologyNodeFilter(filter).keys()).length;
}
