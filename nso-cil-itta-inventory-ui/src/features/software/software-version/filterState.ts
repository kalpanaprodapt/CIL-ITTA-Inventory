import type { LifecycleStatus } from '@/shared/types';
import type { SoftwareVersionFilter, SoftwareVersionReleaseTrain, SoftwareVersionCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSoftwareVersionQuery(filter: SoftwareVersionFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.imageId) query.imageId = filter.imageId;
  if (filter.versionString) query.versionString = filter.versionString;
  if (filter.releaseDateFrom) query.releaseDateFrom = filter.releaseDateFrom;
  if (filter.releaseDateTo) query.releaseDateTo = filter.releaseDateTo;
  if (filter.endOfSupportFrom) query.endOfSupportFrom = filter.endOfSupportFrom;
  if (filter.endOfSupportTo) query.endOfSupportTo = filter.endOfSupportTo;
  if (filter.releaseTrain?.length) query.releaseTrain = filter.releaseTrain.join(',');
  if (filter.cveCountMin !== undefined) query.cveCountMin = filter.cveCountMin;
  if (filter.cveCountMax !== undefined) query.cveCountMax = filter.cveCountMax;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseSoftwareVersionFilter(params: URLSearchParams): SoftwareVersionFilter {
  const filter: SoftwareVersionFilter = {};
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
  const imageId = params.get('imageId');
  if (imageId) filter.imageId = imageId;
  const versionString = params.get('versionString');
  if (versionString) filter.versionString = versionString;
  const releaseDateFrom = params.get('releaseDateFrom');
  if (releaseDateFrom) filter.releaseDateFrom = releaseDateFrom;
  const releaseDateTo = params.get('releaseDateTo');
  if (releaseDateTo) filter.releaseDateTo = releaseDateTo;
  const endOfSupportFrom = params.get('endOfSupportFrom');
  if (endOfSupportFrom) filter.endOfSupportFrom = endOfSupportFrom;
  const endOfSupportTo = params.get('endOfSupportTo');
  if (endOfSupportTo) filter.endOfSupportTo = endOfSupportTo;
  const releaseTrain = params.get('releaseTrain');
  if (releaseTrain) filter.releaseTrain = releaseTrain.split(',') as SoftwareVersionReleaseTrain[];
  const cveCountMin = params.get('cveCountMin');
  if (cveCountMin !== null && cveCountMin !== '') filter.cveCountMin = Number(cveCountMin);
  const cveCountMax = params.get('cveCountMax');
  if (cveCountMax !== null && cveCountMax !== '') filter.cveCountMax = Number(cveCountMax);
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SoftwareVersionCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeSoftwareVersionFilter(filter: SoftwareVersionFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.imageId) params.set('imageId', filter.imageId);
  if (filter.versionString) params.set('versionString', filter.versionString);
  if (filter.releaseDateFrom) params.set('releaseDateFrom', filter.releaseDateFrom);
  if (filter.releaseDateTo) params.set('releaseDateTo', filter.releaseDateTo);
  if (filter.endOfSupportFrom) params.set('endOfSupportFrom', filter.endOfSupportFrom);
  if (filter.endOfSupportTo) params.set('endOfSupportTo', filter.endOfSupportTo);
  if (filter.releaseTrain?.length) params.set('releaseTrain', filter.releaseTrain.join(','));
  if (filter.cveCountMin !== undefined) params.set('cveCountMin', String(filter.cveCountMin));
  if (filter.cveCountMax !== undefined) params.set('cveCountMax', String(filter.cveCountMax));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countSoftwareVersionActiveFilters(filter: SoftwareVersionFilter): number {
  return Array.from(serializeSoftwareVersionFilter(filter).keys()).length;
}
