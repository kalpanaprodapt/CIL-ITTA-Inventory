import type { LifecycleStatus } from '@/shared/types';
import type { TopologyViewFilter, TopologyViewViewType, TopologyViewLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toTopologyViewQuery(filter: TopologyViewFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.viewName) query.viewName = filter.viewName;
  if (filter.viewType?.length) query.viewType = filter.viewType.join(',');
  if (filter.scopeFilter) query.scopeFilter = filter.scopeFilter;
  if (filter.refreshedAtFrom) query.refreshedAtFrom = filter.refreshedAtFrom;
  if (filter.refreshedAtTo) query.refreshedAtTo = filter.refreshedAtTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseTopologyViewFilter(params: URLSearchParams): TopologyViewFilter {
  const filter: TopologyViewFilter = {};
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
  const viewName = params.get('viewName');
  if (viewName) filter.viewName = viewName;
  const viewType = params.get('viewType');
  if (viewType) filter.viewType = viewType.split(',') as TopologyViewViewType[];
  const scopeFilter = params.get('scopeFilter');
  if (scopeFilter) filter.scopeFilter = scopeFilter;
  const refreshedAtFrom = params.get('refreshedAtFrom');
  if (refreshedAtFrom) filter.refreshedAtFrom = refreshedAtFrom;
  const refreshedAtTo = params.get('refreshedAtTo');
  if (refreshedAtTo) filter.refreshedAtTo = refreshedAtTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as TopologyViewLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeTopologyViewFilter(filter: TopologyViewFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.viewName) params.set('viewName', filter.viewName);
  if (filter.viewType?.length) params.set('viewType', filter.viewType.join(','));
  if (filter.scopeFilter) params.set('scopeFilter', filter.scopeFilter);
  if (filter.refreshedAtFrom) params.set('refreshedAtFrom', filter.refreshedAtFrom);
  if (filter.refreshedAtTo) params.set('refreshedAtTo', filter.refreshedAtTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countTopologyViewActiveFilters(filter: TopologyViewFilter): number {
  return Array.from(serializeTopologyViewFilter(filter).keys()).length;
}
