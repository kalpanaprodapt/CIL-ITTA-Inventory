import type { LifecycleStatus } from '@/shared/types';
import type { LicenseKeyFilter, LicenseKeyLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toLicenseKeyQuery(filter: LicenseKeyFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.poolId) query.poolId = filter.poolId;
  if (filter.keyValue) query.keyValue = filter.keyValue;
  if (filter.feature) query.feature = filter.feature;
  if (filter.expiresOnFrom) query.expiresOnFrom = filter.expiresOnFrom;
  if (filter.expiresOnTo) query.expiresOnTo = filter.expiresOnTo;
  if (filter.assignedDeviceId) query.assignedDeviceId = filter.assignedDeviceId;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseLicenseKeyFilter(params: URLSearchParams): LicenseKeyFilter {
  const filter: LicenseKeyFilter = {};
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
  const poolId = params.get('poolId');
  if (poolId) filter.poolId = poolId;
  const keyValue = params.get('keyValue');
  if (keyValue) filter.keyValue = keyValue;
  const feature = params.get('feature');
  if (feature) filter.feature = feature;
  const expiresOnFrom = params.get('expiresOnFrom');
  if (expiresOnFrom) filter.expiresOnFrom = expiresOnFrom;
  const expiresOnTo = params.get('expiresOnTo');
  if (expiresOnTo) filter.expiresOnTo = expiresOnTo;
  const assignedDeviceId = params.get('assignedDeviceId');
  if (assignedDeviceId) filter.assignedDeviceId = assignedDeviceId;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as LicenseKeyLifecycleStage[];
  return filter;
}

export function serializeLicenseKeyFilter(filter: LicenseKeyFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.poolId) params.set('poolId', filter.poolId);
  if (filter.keyValue) params.set('keyValue', filter.keyValue);
  if (filter.feature) params.set('feature', filter.feature);
  if (filter.expiresOnFrom) params.set('expiresOnFrom', filter.expiresOnFrom);
  if (filter.expiresOnTo) params.set('expiresOnTo', filter.expiresOnTo);
  if (filter.assignedDeviceId) params.set('assignedDeviceId', filter.assignedDeviceId);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countLicenseKeyActiveFilters(filter: LicenseKeyFilter): number {
  return Array.from(serializeLicenseKeyFilter(filter).keys()).length;
}
