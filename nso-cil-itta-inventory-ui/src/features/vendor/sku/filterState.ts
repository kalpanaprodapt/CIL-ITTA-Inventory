import type { LifecycleStatus } from '@/shared/types';
import type { SkuFilter, SkuCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSkuQuery(filter: SkuFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.modelId) query.modelId = filter.modelId;
  if (filter.skuCode) query.skuCode = filter.skuCode;
  if (filter.listPriceMin !== undefined) query.listPriceMin = filter.listPriceMin;
  if (filter.listPriceMax !== undefined) query.listPriceMax = filter.listPriceMax;
  if (filter.currency) query.currency = filter.currency;
  if (filter.leadTimeDaysMin !== undefined) query.leadTimeDaysMin = filter.leadTimeDaysMin;
  if (filter.leadTimeDaysMax !== undefined) query.leadTimeDaysMax = filter.leadTimeDaysMax;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseSkuFilter(params: URLSearchParams): SkuFilter {
  const filter: SkuFilter = {};
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
  const modelId = params.get('modelId');
  if (modelId) filter.modelId = modelId;
  const skuCode = params.get('skuCode');
  if (skuCode) filter.skuCode = skuCode;
  const listPriceMin = params.get('listPriceMin');
  if (listPriceMin !== null && listPriceMin !== '') filter.listPriceMin = Number(listPriceMin);
  const listPriceMax = params.get('listPriceMax');
  if (listPriceMax !== null && listPriceMax !== '') filter.listPriceMax = Number(listPriceMax);
  const currency = params.get('currency');
  if (currency) filter.currency = currency;
  const leadTimeDaysMin = params.get('leadTimeDaysMin');
  if (leadTimeDaysMin !== null && leadTimeDaysMin !== '') filter.leadTimeDaysMin = Number(leadTimeDaysMin);
  const leadTimeDaysMax = params.get('leadTimeDaysMax');
  if (leadTimeDaysMax !== null && leadTimeDaysMax !== '') filter.leadTimeDaysMax = Number(leadTimeDaysMax);
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SkuCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeSkuFilter(filter: SkuFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.modelId) params.set('modelId', filter.modelId);
  if (filter.skuCode) params.set('skuCode', filter.skuCode);
  if (filter.listPriceMin !== undefined) params.set('listPriceMin', String(filter.listPriceMin));
  if (filter.listPriceMax !== undefined) params.set('listPriceMax', String(filter.listPriceMax));
  if (filter.currency) params.set('currency', filter.currency);
  if (filter.leadTimeDaysMin !== undefined) params.set('leadTimeDaysMin', String(filter.leadTimeDaysMin));
  if (filter.leadTimeDaysMax !== undefined) params.set('leadTimeDaysMax', String(filter.leadTimeDaysMax));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countSkuActiveFilters(filter: SkuFilter): number {
  return Array.from(serializeSkuFilter(filter).keys()).length;
}
