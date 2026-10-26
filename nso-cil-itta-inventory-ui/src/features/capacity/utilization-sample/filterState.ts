import type { LifecycleStatus } from '@/shared/types';
import type { UtilizationSampleFilter, UtilizationSampleLifecycleStage, UtilizationSampleCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toUtilizationSampleQuery(filter: UtilizationSampleFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.targetRef) query.targetRef = filter.targetRef;
  if (filter.metric) query.metric = filter.metric;
  if (filter.sampledAtFrom) query.sampledAtFrom = filter.sampledAtFrom;
  if (filter.sampledAtTo) query.sampledAtTo = filter.sampledAtTo;
  if (filter.valuePctMin !== undefined) query.valuePctMin = filter.valuePctMin;
  if (filter.valuePctMax !== undefined) query.valuePctMax = filter.valuePctMax;
  if (filter.peak !== undefined) query.peak = filter.peak;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseUtilizationSampleFilter(params: URLSearchParams): UtilizationSampleFilter {
  const filter: UtilizationSampleFilter = {};
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
  const targetRef = params.get('targetRef');
  if (targetRef) filter.targetRef = targetRef;
  const metric = params.get('metric');
  if (metric) filter.metric = metric;
  const sampledAtFrom = params.get('sampledAtFrom');
  if (sampledAtFrom) filter.sampledAtFrom = sampledAtFrom;
  const sampledAtTo = params.get('sampledAtTo');
  if (sampledAtTo) filter.sampledAtTo = sampledAtTo;
  const valuePctMin = params.get('valuePctMin');
  if (valuePctMin !== null && valuePctMin !== '') filter.valuePctMin = Number(valuePctMin);
  const valuePctMax = params.get('valuePctMax');
  if (valuePctMax !== null && valuePctMax !== '') filter.valuePctMax = Number(valuePctMax);
  const peak = params.get('peak');
  if (peak !== null) filter.peak = peak === 'true';
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as UtilizationSampleLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as UtilizationSampleCriticality[];
  return filter;
}

export function serializeUtilizationSampleFilter(filter: UtilizationSampleFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.targetRef) params.set('targetRef', filter.targetRef);
  if (filter.metric) params.set('metric', filter.metric);
  if (filter.sampledAtFrom) params.set('sampledAtFrom', filter.sampledAtFrom);
  if (filter.sampledAtTo) params.set('sampledAtTo', filter.sampledAtTo);
  if (filter.valuePctMin !== undefined) params.set('valuePctMin', String(filter.valuePctMin));
  if (filter.valuePctMax !== undefined) params.set('valuePctMax', String(filter.valuePctMax));
  if (filter.peak !== undefined) params.set('peak', String(filter.peak));
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countUtilizationSampleActiveFilters(filter: UtilizationSampleFilter): number {
  return Array.from(serializeUtilizationSampleFilter(filter).keys()).length;
}
