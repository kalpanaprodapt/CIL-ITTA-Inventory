import type { LifecycleStatus } from '@/shared/types';
import type { ReconciliationRuleFilter, ReconciliationRuleAction, ReconciliationRuleLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toReconciliationRuleQuery(filter: ReconciliationRuleFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.ruleName) query.ruleName = filter.ruleName;
  if (filter.matchKey) query.matchKey = filter.matchKey;
  if (filter.action?.length) query.action = filter.action.join(',');
  if (filter.priorityMin !== undefined) query.priorityMin = filter.priorityMin;
  if (filter.priorityMax !== undefined) query.priorityMax = filter.priorityMax;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseReconciliationRuleFilter(params: URLSearchParams): ReconciliationRuleFilter {
  const filter: ReconciliationRuleFilter = {};
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
  const ruleName = params.get('ruleName');
  if (ruleName) filter.ruleName = ruleName;
  const matchKey = params.get('matchKey');
  if (matchKey) filter.matchKey = matchKey;
  const action = params.get('action');
  if (action) filter.action = action.split(',') as ReconciliationRuleAction[];
  const priorityMin = params.get('priorityMin');
  if (priorityMin !== null && priorityMin !== '') filter.priorityMin = Number(priorityMin);
  const priorityMax = params.get('priorityMax');
  if (priorityMax !== null && priorityMax !== '') filter.priorityMax = Number(priorityMax);
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ReconciliationRuleLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeReconciliationRuleFilter(filter: ReconciliationRuleFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.ruleName) params.set('ruleName', filter.ruleName);
  if (filter.matchKey) params.set('matchKey', filter.matchKey);
  if (filter.action?.length) params.set('action', filter.action.join(','));
  if (filter.priorityMin !== undefined) params.set('priorityMin', String(filter.priorityMin));
  if (filter.priorityMax !== undefined) params.set('priorityMax', String(filter.priorityMax));
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countReconciliationRuleActiveFilters(filter: ReconciliationRuleFilter): number {
  return Array.from(serializeReconciliationRuleFilter(filter).keys()).length;
}
