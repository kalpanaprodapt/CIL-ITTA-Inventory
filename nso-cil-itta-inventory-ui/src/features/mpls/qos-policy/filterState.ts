import type { LifecycleStatus } from '@/shared/types';
import type { QosPolicyFilter, QosPolicyLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toQosPolicyQuery(filter: QosPolicyFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.policyName) query.policyName = filter.policyName;
  if (filter.classCountMin !== undefined) query.classCountMin = filter.classCountMin;
  if (filter.classCountMax !== undefined) query.classCountMax = filter.classCountMax;
  if (filter.cirMbpsMin !== undefined) query.cirMbpsMin = filter.cirMbpsMin;
  if (filter.cirMbpsMax !== undefined) query.cirMbpsMax = filter.cirMbpsMax;
  if (filter.pirMbpsMin !== undefined) query.pirMbpsMin = filter.pirMbpsMin;
  if (filter.pirMbpsMax !== undefined) query.pirMbpsMax = filter.pirMbpsMax;
  if (filter.markingScheme) query.markingScheme = filter.markingScheme;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseQosPolicyFilter(params: URLSearchParams): QosPolicyFilter {
  const filter: QosPolicyFilter = {};
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
  const policyName = params.get('policyName');
  if (policyName) filter.policyName = policyName;
  const classCountMin = params.get('classCountMin');
  if (classCountMin !== null && classCountMin !== '') filter.classCountMin = Number(classCountMin);
  const classCountMax = params.get('classCountMax');
  if (classCountMax !== null && classCountMax !== '') filter.classCountMax = Number(classCountMax);
  const cirMbpsMin = params.get('cirMbpsMin');
  if (cirMbpsMin !== null && cirMbpsMin !== '') filter.cirMbpsMin = Number(cirMbpsMin);
  const cirMbpsMax = params.get('cirMbpsMax');
  if (cirMbpsMax !== null && cirMbpsMax !== '') filter.cirMbpsMax = Number(cirMbpsMax);
  const pirMbpsMin = params.get('pirMbpsMin');
  if (pirMbpsMin !== null && pirMbpsMin !== '') filter.pirMbpsMin = Number(pirMbpsMin);
  const pirMbpsMax = params.get('pirMbpsMax');
  if (pirMbpsMax !== null && pirMbpsMax !== '') filter.pirMbpsMax = Number(pirMbpsMax);
  const markingScheme = params.get('markingScheme');
  if (markingScheme) filter.markingScheme = markingScheme;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as QosPolicyLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeQosPolicyFilter(filter: QosPolicyFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.policyName) params.set('policyName', filter.policyName);
  if (filter.classCountMin !== undefined) params.set('classCountMin', String(filter.classCountMin));
  if (filter.classCountMax !== undefined) params.set('classCountMax', String(filter.classCountMax));
  if (filter.cirMbpsMin !== undefined) params.set('cirMbpsMin', String(filter.cirMbpsMin));
  if (filter.cirMbpsMax !== undefined) params.set('cirMbpsMax', String(filter.cirMbpsMax));
  if (filter.pirMbpsMin !== undefined) params.set('pirMbpsMin', String(filter.pirMbpsMin));
  if (filter.pirMbpsMax !== undefined) params.set('pirMbpsMax', String(filter.pirMbpsMax));
  if (filter.markingScheme) params.set('markingScheme', filter.markingScheme);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countQosPolicyActiveFilters(filter: QosPolicyFilter): number {
  return Array.from(serializeQosPolicyFilter(filter).keys()).length;
}
