import type { LifecycleStatus } from '@/shared/types';
import type { LineCardFilter, LineCardCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toLineCardQuery(filter: LineCardFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.chassisId) query.chassisId = filter.chassisId;
  if (filter.slotNumberMin !== undefined) query.slotNumberMin = filter.slotNumberMin;
  if (filter.slotNumberMax !== undefined) query.slotNumberMax = filter.slotNumberMax;
  if (filter.cardType) query.cardType = filter.cardType;
  if (filter.portCountMin !== undefined) query.portCountMin = filter.portCountMin;
  if (filter.portCountMax !== undefined) query.portCountMax = filter.portCountMax;
  if (filter.serialNumber) query.serialNumber = filter.serialNumber;
  if (filter.firmware) query.firmware = filter.firmware;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseLineCardFilter(params: URLSearchParams): LineCardFilter {
  const filter: LineCardFilter = {};
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
  const chassisId = params.get('chassisId');
  if (chassisId) filter.chassisId = chassisId;
  const slotNumberMin = params.get('slotNumberMin');
  if (slotNumberMin !== null && slotNumberMin !== '') filter.slotNumberMin = Number(slotNumberMin);
  const slotNumberMax = params.get('slotNumberMax');
  if (slotNumberMax !== null && slotNumberMax !== '') filter.slotNumberMax = Number(slotNumberMax);
  const cardType = params.get('cardType');
  if (cardType) filter.cardType = cardType;
  const portCountMin = params.get('portCountMin');
  if (portCountMin !== null && portCountMin !== '') filter.portCountMin = Number(portCountMin);
  const portCountMax = params.get('portCountMax');
  if (portCountMax !== null && portCountMax !== '') filter.portCountMax = Number(portCountMax);
  const serialNumber = params.get('serialNumber');
  if (serialNumber) filter.serialNumber = serialNumber;
  const firmware = params.get('firmware');
  if (firmware) filter.firmware = firmware;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as LineCardCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeLineCardFilter(filter: LineCardFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.chassisId) params.set('chassisId', filter.chassisId);
  if (filter.slotNumberMin !== undefined) params.set('slotNumberMin', String(filter.slotNumberMin));
  if (filter.slotNumberMax !== undefined) params.set('slotNumberMax', String(filter.slotNumberMax));
  if (filter.cardType) params.set('cardType', filter.cardType);
  if (filter.portCountMin !== undefined) params.set('portCountMin', String(filter.portCountMin));
  if (filter.portCountMax !== undefined) params.set('portCountMax', String(filter.portCountMax));
  if (filter.serialNumber) params.set('serialNumber', filter.serialNumber);
  if (filter.firmware) params.set('firmware', filter.firmware);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countLineCardActiveFilters(filter: LineCardFilter): number {
  return Array.from(serializeLineCardFilter(filter).keys()).length;
}
