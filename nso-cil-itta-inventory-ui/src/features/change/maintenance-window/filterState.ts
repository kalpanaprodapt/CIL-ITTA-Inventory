import type { LifecycleStatus } from '@/shared/types';
import type { MaintenanceWindowFilter, MaintenanceWindowImpactLevel, MaintenanceWindowLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toMaintenanceWindowQuery(filter: MaintenanceWindowFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.windowName) query.windowName = filter.windowName;
  if (filter.startsAtFrom) query.startsAtFrom = filter.startsAtFrom;
  if (filter.startsAtTo) query.startsAtTo = filter.startsAtTo;
  if (filter.endsAtFrom) query.endsAtFrom = filter.endsAtFrom;
  if (filter.endsAtTo) query.endsAtTo = filter.endsAtTo;
  if (filter.scope) query.scope = filter.scope;
  if (filter.impactLevel?.length) query.impactLevel = filter.impactLevel.join(',');
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseMaintenanceWindowFilter(params: URLSearchParams): MaintenanceWindowFilter {
  const filter: MaintenanceWindowFilter = {};
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
  const windowName = params.get('windowName');
  if (windowName) filter.windowName = windowName;
  const startsAtFrom = params.get('startsAtFrom');
  if (startsAtFrom) filter.startsAtFrom = startsAtFrom;
  const startsAtTo = params.get('startsAtTo');
  if (startsAtTo) filter.startsAtTo = startsAtTo;
  const endsAtFrom = params.get('endsAtFrom');
  if (endsAtFrom) filter.endsAtFrom = endsAtFrom;
  const endsAtTo = params.get('endsAtTo');
  if (endsAtTo) filter.endsAtTo = endsAtTo;
  const scope = params.get('scope');
  if (scope) filter.scope = scope;
  const impactLevel = params.get('impactLevel');
  if (impactLevel) filter.impactLevel = impactLevel.split(',') as MaintenanceWindowImpactLevel[];
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as MaintenanceWindowLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeMaintenanceWindowFilter(filter: MaintenanceWindowFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.windowName) params.set('windowName', filter.windowName);
  if (filter.startsAtFrom) params.set('startsAtFrom', filter.startsAtFrom);
  if (filter.startsAtTo) params.set('startsAtTo', filter.startsAtTo);
  if (filter.endsAtFrom) params.set('endsAtFrom', filter.endsAtFrom);
  if (filter.endsAtTo) params.set('endsAtTo', filter.endsAtTo);
  if (filter.scope) params.set('scope', filter.scope);
  if (filter.impactLevel?.length) params.set('impactLevel', filter.impactLevel.join(','));
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countMaintenanceWindowActiveFilters(filter: MaintenanceWindowFilter): number {
  return Array.from(serializeMaintenanceWindowFilter(filter).keys()).length;
}
