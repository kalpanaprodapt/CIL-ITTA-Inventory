import type { LifecycleStatus } from '@/shared/types';
import type { LicensePoolFilter, LicensePoolLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toLicensePoolQuery(filter: LicensePoolFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vendorId) query.vendorId = filter.vendorId;
  if (filter.poolName) query.poolName = filter.poolName;
  if (filter.totalSeatsMin !== undefined) query.totalSeatsMin = filter.totalSeatsMin;
  if (filter.totalSeatsMax !== undefined) query.totalSeatsMax = filter.totalSeatsMax;
  if (filter.usedSeatsMin !== undefined) query.usedSeatsMin = filter.usedSeatsMin;
  if (filter.usedSeatsMax !== undefined) query.usedSeatsMax = filter.usedSeatsMax;
  if (filter.renewalDateFrom) query.renewalDateFrom = filter.renewalDateFrom;
  if (filter.renewalDateTo) query.renewalDateTo = filter.renewalDateTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseLicensePoolFilter(params: URLSearchParams): LicensePoolFilter {
  const filter: LicensePoolFilter = {};
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
  const vendorId = params.get('vendorId');
  if (vendorId) filter.vendorId = vendorId;
  const poolName = params.get('poolName');
  if (poolName) filter.poolName = poolName;
  const totalSeatsMin = params.get('totalSeatsMin');
  if (totalSeatsMin !== null && totalSeatsMin !== '') filter.totalSeatsMin = Number(totalSeatsMin);
  const totalSeatsMax = params.get('totalSeatsMax');
  if (totalSeatsMax !== null && totalSeatsMax !== '') filter.totalSeatsMax = Number(totalSeatsMax);
  const usedSeatsMin = params.get('usedSeatsMin');
  if (usedSeatsMin !== null && usedSeatsMin !== '') filter.usedSeatsMin = Number(usedSeatsMin);
  const usedSeatsMax = params.get('usedSeatsMax');
  if (usedSeatsMax !== null && usedSeatsMax !== '') filter.usedSeatsMax = Number(usedSeatsMax);
  const renewalDateFrom = params.get('renewalDateFrom');
  if (renewalDateFrom) filter.renewalDateFrom = renewalDateFrom;
  const renewalDateTo = params.get('renewalDateTo');
  if (renewalDateTo) filter.renewalDateTo = renewalDateTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as LicensePoolLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeLicensePoolFilter(filter: LicensePoolFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vendorId) params.set('vendorId', filter.vendorId);
  if (filter.poolName) params.set('poolName', filter.poolName);
  if (filter.totalSeatsMin !== undefined) params.set('totalSeatsMin', String(filter.totalSeatsMin));
  if (filter.totalSeatsMax !== undefined) params.set('totalSeatsMax', String(filter.totalSeatsMax));
  if (filter.usedSeatsMin !== undefined) params.set('usedSeatsMin', String(filter.usedSeatsMin));
  if (filter.usedSeatsMax !== undefined) params.set('usedSeatsMax', String(filter.usedSeatsMax));
  if (filter.renewalDateFrom) params.set('renewalDateFrom', filter.renewalDateFrom);
  if (filter.renewalDateTo) params.set('renewalDateTo', filter.renewalDateTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countLicensePoolActiveFilters(filter: LicensePoolFilter): number {
  return Array.from(serializeLicensePoolFilter(filter).keys()).length;
}
