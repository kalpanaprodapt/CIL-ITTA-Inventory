import type { LifecycleStatus } from '@/shared/types';
import type { LinkFilter, LinkLinkType, LinkCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toLinkQuery(filter: LinkFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.aInterfaceId) query.aInterfaceId = filter.aInterfaceId;
  if (filter.zInterfaceId) query.zInterfaceId = filter.zInterfaceId;
  if (filter.linkType?.length) query.linkType = filter.linkType.join(',');
  if (filter.speedMbpsMin !== undefined) query.speedMbpsMin = filter.speedMbpsMin;
  if (filter.speedMbpsMax !== undefined) query.speedMbpsMax = filter.speedMbpsMax;
  if (filter.discoveredBy) query.discoveredBy = filter.discoveredBy;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseLinkFilter(params: URLSearchParams): LinkFilter {
  const filter: LinkFilter = {};
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
  const aInterfaceId = params.get('aInterfaceId');
  if (aInterfaceId) filter.aInterfaceId = aInterfaceId;
  const zInterfaceId = params.get('zInterfaceId');
  if (zInterfaceId) filter.zInterfaceId = zInterfaceId;
  const linkType = params.get('linkType');
  if (linkType) filter.linkType = linkType.split(',') as LinkLinkType[];
  const speedMbpsMin = params.get('speedMbpsMin');
  if (speedMbpsMin !== null && speedMbpsMin !== '') filter.speedMbpsMin = Number(speedMbpsMin);
  const speedMbpsMax = params.get('speedMbpsMax');
  if (speedMbpsMax !== null && speedMbpsMax !== '') filter.speedMbpsMax = Number(speedMbpsMax);
  const discoveredBy = params.get('discoveredBy');
  if (discoveredBy) filter.discoveredBy = discoveredBy;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as LinkCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeLinkFilter(filter: LinkFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.aInterfaceId) params.set('aInterfaceId', filter.aInterfaceId);
  if (filter.zInterfaceId) params.set('zInterfaceId', filter.zInterfaceId);
  if (filter.linkType?.length) params.set('linkType', filter.linkType.join(','));
  if (filter.speedMbpsMin !== undefined) params.set('speedMbpsMin', String(filter.speedMbpsMin));
  if (filter.speedMbpsMax !== undefined) params.set('speedMbpsMax', String(filter.speedMbpsMax));
  if (filter.discoveredBy) params.set('discoveredBy', filter.discoveredBy);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countLinkActiveFilters(filter: LinkFilter): number {
  return Array.from(serializeLinkFilter(filter).keys()).length;
}
