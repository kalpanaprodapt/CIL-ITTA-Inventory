import type { LifecycleStatus } from '@/shared/types';
import type { VendorFilter, VendorTier, VendorLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toVendorQuery(filter: VendorFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.legalName) query.legalName = filter.legalName;
  if (filter.supportEmail) query.supportEmail = filter.supportEmail;
  if (filter.supportPhone) query.supportPhone = filter.supportPhone;
  if (filter.website) query.website = filter.website;
  if (filter.tier?.length) query.tier = filter.tier.join(',');
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseVendorFilter(params: URLSearchParams): VendorFilter {
  const filter: VendorFilter = {};
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
  const legalName = params.get('legalName');
  if (legalName) filter.legalName = legalName;
  const supportEmail = params.get('supportEmail');
  if (supportEmail) filter.supportEmail = supportEmail;
  const supportPhone = params.get('supportPhone');
  if (supportPhone) filter.supportPhone = supportPhone;
  const website = params.get('website');
  if (website) filter.website = website;
  const tier = params.get('tier');
  if (tier) filter.tier = tier.split(',') as VendorTier[];
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as VendorLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeVendorFilter(filter: VendorFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.legalName) params.set('legalName', filter.legalName);
  if (filter.supportEmail) params.set('supportEmail', filter.supportEmail);
  if (filter.supportPhone) params.set('supportPhone', filter.supportPhone);
  if (filter.website) params.set('website', filter.website);
  if (filter.tier?.length) params.set('tier', filter.tier.join(','));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countVendorActiveFilters(filter: VendorFilter): number {
  return Array.from(serializeVendorFilter(filter).keys()).length;
}
