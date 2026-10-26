import type { LifecycleStatus } from '@/shared/types';
import type { BgpSessionFilter, BgpSessionSessionState, BgpSessionCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toBgpSessionQuery(filter: BgpSessionFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.localDeviceId) query.localDeviceId = filter.localDeviceId;
  if (filter.peerAddress) query.peerAddress = filter.peerAddress;
  if (filter.localAsnMin !== undefined) query.localAsnMin = filter.localAsnMin;
  if (filter.localAsnMax !== undefined) query.localAsnMax = filter.localAsnMax;
  if (filter.remoteAsnMin !== undefined) query.remoteAsnMin = filter.remoteAsnMin;
  if (filter.remoteAsnMax !== undefined) query.remoteAsnMax = filter.remoteAsnMax;
  if (filter.sessionState?.length) query.sessionState = filter.sessionState.join(',');
  if (filter.prefixLimitMin !== undefined) query.prefixLimitMin = filter.prefixLimitMin;
  if (filter.prefixLimitMax !== undefined) query.prefixLimitMax = filter.prefixLimitMax;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseBgpSessionFilter(params: URLSearchParams): BgpSessionFilter {
  const filter: BgpSessionFilter = {};
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
  const localDeviceId = params.get('localDeviceId');
  if (localDeviceId) filter.localDeviceId = localDeviceId;
  const peerAddress = params.get('peerAddress');
  if (peerAddress) filter.peerAddress = peerAddress;
  const localAsnMin = params.get('localAsnMin');
  if (localAsnMin !== null && localAsnMin !== '') filter.localAsnMin = Number(localAsnMin);
  const localAsnMax = params.get('localAsnMax');
  if (localAsnMax !== null && localAsnMax !== '') filter.localAsnMax = Number(localAsnMax);
  const remoteAsnMin = params.get('remoteAsnMin');
  if (remoteAsnMin !== null && remoteAsnMin !== '') filter.remoteAsnMin = Number(remoteAsnMin);
  const remoteAsnMax = params.get('remoteAsnMax');
  if (remoteAsnMax !== null && remoteAsnMax !== '') filter.remoteAsnMax = Number(remoteAsnMax);
  const sessionState = params.get('sessionState');
  if (sessionState) filter.sessionState = sessionState.split(',') as BgpSessionSessionState[];
  const prefixLimitMin = params.get('prefixLimitMin');
  if (prefixLimitMin !== null && prefixLimitMin !== '') filter.prefixLimitMin = Number(prefixLimitMin);
  const prefixLimitMax = params.get('prefixLimitMax');
  if (prefixLimitMax !== null && prefixLimitMax !== '') filter.prefixLimitMax = Number(prefixLimitMax);
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as BgpSessionCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeBgpSessionFilter(filter: BgpSessionFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.localDeviceId) params.set('localDeviceId', filter.localDeviceId);
  if (filter.peerAddress) params.set('peerAddress', filter.peerAddress);
  if (filter.localAsnMin !== undefined) params.set('localAsnMin', String(filter.localAsnMin));
  if (filter.localAsnMax !== undefined) params.set('localAsnMax', String(filter.localAsnMax));
  if (filter.remoteAsnMin !== undefined) params.set('remoteAsnMin', String(filter.remoteAsnMin));
  if (filter.remoteAsnMax !== undefined) params.set('remoteAsnMax', String(filter.remoteAsnMax));
  if (filter.sessionState?.length) params.set('sessionState', filter.sessionState.join(','));
  if (filter.prefixLimitMin !== undefined) params.set('prefixLimitMin', String(filter.prefixLimitMin));
  if (filter.prefixLimitMax !== undefined) params.set('prefixLimitMax', String(filter.prefixLimitMax));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countBgpSessionActiveFilters(filter: BgpSessionFilter): number {
  return Array.from(serializeBgpSessionFilter(filter).keys()).length;
}
