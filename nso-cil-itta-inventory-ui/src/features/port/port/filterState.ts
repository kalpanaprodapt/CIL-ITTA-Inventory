import type { LifecycleStatus } from '@/shared/types';
import type { PortFilter, PortMediaType, PortAdminState, PortCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPortQuery(filter: PortFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.deviceId) query.deviceId = filter.deviceId;
  if (filter.cardId) query.cardId = filter.cardId;
  if (filter.portNumber) query.portNumber = filter.portNumber;
  if (filter.speedMbpsMin !== undefined) query.speedMbpsMin = filter.speedMbpsMin;
  if (filter.speedMbpsMax !== undefined) query.speedMbpsMax = filter.speedMbpsMax;
  if (filter.mediaType?.length) query.mediaType = filter.mediaType.join(',');
  if (filter.adminState?.length) query.adminState = filter.adminState.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parsePortFilter(params: URLSearchParams): PortFilter {
  const filter: PortFilter = {};
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
  const cardId = params.get('cardId');
  if (cardId) filter.cardId = cardId;
  const portNumber = params.get('portNumber');
  if (portNumber) filter.portNumber = portNumber;
  const speedMbpsMin = params.get('speedMbpsMin');
  if (speedMbpsMin !== null && speedMbpsMin !== '') filter.speedMbpsMin = Number(speedMbpsMin);
  const speedMbpsMax = params.get('speedMbpsMax');
  if (speedMbpsMax !== null && speedMbpsMax !== '') filter.speedMbpsMax = Number(speedMbpsMax);
  const mediaType = params.get('mediaType');
  if (mediaType) filter.mediaType = mediaType.split(',') as PortMediaType[];
  const adminState = params.get('adminState');
  if (adminState) filter.adminState = adminState.split(',') as PortAdminState[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as PortCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializePortFilter(filter: PortFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.deviceId) params.set('deviceId', filter.deviceId);
  if (filter.cardId) params.set('cardId', filter.cardId);
  if (filter.portNumber) params.set('portNumber', filter.portNumber);
  if (filter.speedMbpsMin !== undefined) params.set('speedMbpsMin', String(filter.speedMbpsMin));
  if (filter.speedMbpsMax !== undefined) params.set('speedMbpsMax', String(filter.speedMbpsMax));
  if (filter.mediaType?.length) params.set('mediaType', filter.mediaType.join(','));
  if (filter.adminState?.length) params.set('adminState', filter.adminState.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countPortActiveFilters(filter: PortFilter): number {
  return Array.from(serializePortFilter(filter).keys()).length;
}
