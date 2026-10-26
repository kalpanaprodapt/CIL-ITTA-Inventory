import type { LifecycleStatus } from '@/shared/types';
import type { EventSourceFilter, EventSourceProtocol, EventSourceLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toEventSourceQuery(filter: EventSourceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.sourceName) query.sourceName = filter.sourceName;
  if (filter.protocol?.length) query.protocol = filter.protocol.join(',');
  if (filter.endpoint) query.endpoint = filter.endpoint;
  if (filter.heartbeatSecondsMin !== undefined) query.heartbeatSecondsMin = filter.heartbeatSecondsMin;
  if (filter.heartbeatSecondsMax !== undefined) query.heartbeatSecondsMax = filter.heartbeatSecondsMax;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseEventSourceFilter(params: URLSearchParams): EventSourceFilter {
  const filter: EventSourceFilter = {};
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
  const sourceName = params.get('sourceName');
  if (sourceName) filter.sourceName = sourceName;
  const protocol = params.get('protocol');
  if (protocol) filter.protocol = protocol.split(',') as EventSourceProtocol[];
  const endpoint = params.get('endpoint');
  if (endpoint) filter.endpoint = endpoint;
  const heartbeatSecondsMin = params.get('heartbeatSecondsMin');
  if (heartbeatSecondsMin !== null && heartbeatSecondsMin !== '') filter.heartbeatSecondsMin = Number(heartbeatSecondsMin);
  const heartbeatSecondsMax = params.get('heartbeatSecondsMax');
  if (heartbeatSecondsMax !== null && heartbeatSecondsMax !== '') filter.heartbeatSecondsMax = Number(heartbeatSecondsMax);
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as EventSourceLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeEventSourceFilter(filter: EventSourceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.sourceName) params.set('sourceName', filter.sourceName);
  if (filter.protocol?.length) params.set('protocol', filter.protocol.join(','));
  if (filter.endpoint) params.set('endpoint', filter.endpoint);
  if (filter.heartbeatSecondsMin !== undefined) params.set('heartbeatSecondsMin', String(filter.heartbeatSecondsMin));
  if (filter.heartbeatSecondsMax !== undefined) params.set('heartbeatSecondsMax', String(filter.heartbeatSecondsMax));
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countEventSourceActiveFilters(filter: EventSourceFilter): number {
  return Array.from(serializeEventSourceFilter(filter).keys()).length;
}
