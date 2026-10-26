import type { LifecycleStatus } from '@/shared/types';
import type { TunnelEndpointFilter, TunnelEndpointTunnelType, TunnelEndpointLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toTunnelEndpointQuery(filter: TunnelEndpointFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.deviceId) query.deviceId = filter.deviceId;
  if (filter.tunnelName) query.tunnelName = filter.tunnelName;
  if (filter.tunnelType?.length) query.tunnelType = filter.tunnelType.join(',');
  if (filter.sourceIp) query.sourceIp = filter.sourceIp;
  if (filter.destinationIp) query.destinationIp = filter.destinationIp;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseTunnelEndpointFilter(params: URLSearchParams): TunnelEndpointFilter {
  const filter: TunnelEndpointFilter = {};
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
  const deviceId = params.get('deviceId');
  if (deviceId) filter.deviceId = deviceId;
  const tunnelName = params.get('tunnelName');
  if (tunnelName) filter.tunnelName = tunnelName;
  const tunnelType = params.get('tunnelType');
  if (tunnelType) filter.tunnelType = tunnelType.split(',') as TunnelEndpointTunnelType[];
  const sourceIp = params.get('sourceIp');
  if (sourceIp) filter.sourceIp = sourceIp;
  const destinationIp = params.get('destinationIp');
  if (destinationIp) filter.destinationIp = destinationIp;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as TunnelEndpointLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeTunnelEndpointFilter(filter: TunnelEndpointFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.deviceId) params.set('deviceId', filter.deviceId);
  if (filter.tunnelName) params.set('tunnelName', filter.tunnelName);
  if (filter.tunnelType?.length) params.set('tunnelType', filter.tunnelType.join(','));
  if (filter.sourceIp) params.set('sourceIp', filter.sourceIp);
  if (filter.destinationIp) params.set('destinationIp', filter.destinationIp);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countTunnelEndpointActiveFilters(filter: TunnelEndpointFilter): number {
  return Array.from(serializeTunnelEndpointFilter(filter).keys()).length;
}
