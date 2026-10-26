import type { LifecycleStatus } from '@/shared/types';
import type { ServiceCatalogItemFilter, ServiceCatalogItemFamily, ServiceCatalogItemLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toServiceCatalogItemQuery(filter: ServiceCatalogItemFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.itemName) query.itemName = filter.itemName;
  if (filter.family?.length) query.family = filter.family.join(',');
  if (filter.basePriceMin !== undefined) query.basePriceMin = filter.basePriceMin;
  if (filter.basePriceMax !== undefined) query.basePriceMax = filter.basePriceMax;
  if (filter.billingUnit) query.billingUnit = filter.billingUnit;
  if (filter.orderable !== undefined) query.orderable = filter.orderable;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseServiceCatalogItemFilter(params: URLSearchParams): ServiceCatalogItemFilter {
  const filter: ServiceCatalogItemFilter = {};
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
  const itemName = params.get('itemName');
  if (itemName) filter.itemName = itemName;
  const family = params.get('family');
  if (family) filter.family = family.split(',') as ServiceCatalogItemFamily[];
  const basePriceMin = params.get('basePriceMin');
  if (basePriceMin !== null && basePriceMin !== '') filter.basePriceMin = Number(basePriceMin);
  const basePriceMax = params.get('basePriceMax');
  if (basePriceMax !== null && basePriceMax !== '') filter.basePriceMax = Number(basePriceMax);
  const billingUnit = params.get('billingUnit');
  if (billingUnit) filter.billingUnit = billingUnit;
  const orderable = params.get('orderable');
  if (orderable !== null) filter.orderable = orderable === 'true';
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ServiceCatalogItemLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeServiceCatalogItemFilter(filter: ServiceCatalogItemFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.itemName) params.set('itemName', filter.itemName);
  if (filter.family?.length) params.set('family', filter.family.join(','));
  if (filter.basePriceMin !== undefined) params.set('basePriceMin', String(filter.basePriceMin));
  if (filter.basePriceMax !== undefined) params.set('basePriceMax', String(filter.basePriceMax));
  if (filter.billingUnit) params.set('billingUnit', filter.billingUnit);
  if (filter.orderable !== undefined) params.set('orderable', String(filter.orderable));
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countServiceCatalogItemActiveFilters(filter: ServiceCatalogItemFilter): number {
  return Array.from(serializeServiceCatalogItemFilter(filter).keys()).length;
}
