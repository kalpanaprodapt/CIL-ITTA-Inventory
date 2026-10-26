import type { LifecycleStatus } from '@/shared/types';
import type { AssetFilter, AssetLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toAssetQuery(filter: AssetFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.assetTag) query.assetTag = filter.assetTag;
  if (filter.assetClass) query.assetClass = filter.assetClass;
  if (filter.acquiredOnFrom) query.acquiredOnFrom = filter.acquiredOnFrom;
  if (filter.acquiredOnTo) query.acquiredOnTo = filter.acquiredOnTo;
  if (filter.purchasePriceMin !== undefined) query.purchasePriceMin = filter.purchasePriceMin;
  if (filter.purchasePriceMax !== undefined) query.purchasePriceMax = filter.purchasePriceMax;
  if (filter.currentValueMin !== undefined) query.currentValueMin = filter.currentValueMin;
  if (filter.currentValueMax !== undefined) query.currentValueMax = filter.currentValueMax;
  if (filter.custodian) query.custodian = filter.custodian;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseAssetFilter(params: URLSearchParams): AssetFilter {
  const filter: AssetFilter = {};
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
  const assetTag = params.get('assetTag');
  if (assetTag) filter.assetTag = assetTag;
  const assetClass = params.get('assetClass');
  if (assetClass) filter.assetClass = assetClass;
  const acquiredOnFrom = params.get('acquiredOnFrom');
  if (acquiredOnFrom) filter.acquiredOnFrom = acquiredOnFrom;
  const acquiredOnTo = params.get('acquiredOnTo');
  if (acquiredOnTo) filter.acquiredOnTo = acquiredOnTo;
  const purchasePriceMin = params.get('purchasePriceMin');
  if (purchasePriceMin !== null && purchasePriceMin !== '') filter.purchasePriceMin = Number(purchasePriceMin);
  const purchasePriceMax = params.get('purchasePriceMax');
  if (purchasePriceMax !== null && purchasePriceMax !== '') filter.purchasePriceMax = Number(purchasePriceMax);
  const currentValueMin = params.get('currentValueMin');
  if (currentValueMin !== null && currentValueMin !== '') filter.currentValueMin = Number(currentValueMin);
  const currentValueMax = params.get('currentValueMax');
  if (currentValueMax !== null && currentValueMax !== '') filter.currentValueMax = Number(currentValueMax);
  const custodian = params.get('custodian');
  if (custodian) filter.custodian = custodian;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as AssetLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeAssetFilter(filter: AssetFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.assetTag) params.set('assetTag', filter.assetTag);
  if (filter.assetClass) params.set('assetClass', filter.assetClass);
  if (filter.acquiredOnFrom) params.set('acquiredOnFrom', filter.acquiredOnFrom);
  if (filter.acquiredOnTo) params.set('acquiredOnTo', filter.acquiredOnTo);
  if (filter.purchasePriceMin !== undefined) params.set('purchasePriceMin', String(filter.purchasePriceMin));
  if (filter.purchasePriceMax !== undefined) params.set('purchasePriceMax', String(filter.purchasePriceMax));
  if (filter.currentValueMin !== undefined) params.set('currentValueMin', String(filter.currentValueMin));
  if (filter.currentValueMax !== undefined) params.set('currentValueMax', String(filter.currentValueMax));
  if (filter.custodian) params.set('custodian', filter.custodian);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countAssetActiveFilters(filter: AssetFilter): number {
  return Array.from(serializeAssetFilter(filter).keys()).length;
}
