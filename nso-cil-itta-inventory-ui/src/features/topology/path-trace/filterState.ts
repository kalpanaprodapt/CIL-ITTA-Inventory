import type { LifecycleStatus } from '@/shared/types';
import type { PathTraceFilter, PathTraceLifecycleStage, PathTraceCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPathTraceQuery(filter: PathTraceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.sourceRef) query.sourceRef = filter.sourceRef;
  if (filter.targetRef) query.targetRef = filter.targetRef;
  if (filter.hopCountMin !== undefined) query.hopCountMin = filter.hopCountMin;
  if (filter.hopCountMax !== undefined) query.hopCountMax = filter.hopCountMax;
  if (filter.totalLatencyMsMin !== undefined) query.totalLatencyMsMin = filter.totalLatencyMsMin;
  if (filter.totalLatencyMsMax !== undefined) query.totalLatencyMsMax = filter.totalLatencyMsMax;
  if (filter.traceMethod) query.traceMethod = filter.traceMethod;
  if (filter.tracedAtFrom) query.tracedAtFrom = filter.tracedAtFrom;
  if (filter.tracedAtTo) query.tracedAtTo = filter.tracedAtTo;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parsePathTraceFilter(params: URLSearchParams): PathTraceFilter {
  const filter: PathTraceFilter = {};
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
  const sourceRef = params.get('sourceRef');
  if (sourceRef) filter.sourceRef = sourceRef;
  const targetRef = params.get('targetRef');
  if (targetRef) filter.targetRef = targetRef;
  const hopCountMin = params.get('hopCountMin');
  if (hopCountMin !== null && hopCountMin !== '') filter.hopCountMin = Number(hopCountMin);
  const hopCountMax = params.get('hopCountMax');
  if (hopCountMax !== null && hopCountMax !== '') filter.hopCountMax = Number(hopCountMax);
  const totalLatencyMsMin = params.get('totalLatencyMsMin');
  if (totalLatencyMsMin !== null && totalLatencyMsMin !== '') filter.totalLatencyMsMin = Number(totalLatencyMsMin);
  const totalLatencyMsMax = params.get('totalLatencyMsMax');
  if (totalLatencyMsMax !== null && totalLatencyMsMax !== '') filter.totalLatencyMsMax = Number(totalLatencyMsMax);
  const traceMethod = params.get('traceMethod');
  if (traceMethod) filter.traceMethod = traceMethod;
  const tracedAtFrom = params.get('tracedAtFrom');
  if (tracedAtFrom) filter.tracedAtFrom = tracedAtFrom;
  const tracedAtTo = params.get('tracedAtTo');
  if (tracedAtTo) filter.tracedAtTo = tracedAtTo;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as PathTraceLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as PathTraceCriticality[];
  return filter;
}

export function serializePathTraceFilter(filter: PathTraceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.sourceRef) params.set('sourceRef', filter.sourceRef);
  if (filter.targetRef) params.set('targetRef', filter.targetRef);
  if (filter.hopCountMin !== undefined) params.set('hopCountMin', String(filter.hopCountMin));
  if (filter.hopCountMax !== undefined) params.set('hopCountMax', String(filter.hopCountMax));
  if (filter.totalLatencyMsMin !== undefined) params.set('totalLatencyMsMin', String(filter.totalLatencyMsMin));
  if (filter.totalLatencyMsMax !== undefined) params.set('totalLatencyMsMax', String(filter.totalLatencyMsMax));
  if (filter.traceMethod) params.set('traceMethod', filter.traceMethod);
  if (filter.tracedAtFrom) params.set('tracedAtFrom', filter.tracedAtFrom);
  if (filter.tracedAtTo) params.set('tracedAtTo', filter.tracedAtTo);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countPathTraceActiveFilters(filter: PathTraceFilter): number {
  return Array.from(serializePathTraceFilter(filter).keys()).length;
}
