import type { LifecycleStatus } from '@/shared/types';
import type { PurchaseOrderFilter, PurchaseOrderCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPurchaseOrderQuery(filter: PurchaseOrderFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.poNumber) query.poNumber = filter.poNumber;
  if (filter.vendorId) query.vendorId = filter.vendorId;
  if (filter.orderedOnFrom) query.orderedOnFrom = filter.orderedOnFrom;
  if (filter.orderedOnTo) query.orderedOnTo = filter.orderedOnTo;
  if (filter.totalAmountMin !== undefined) query.totalAmountMin = filter.totalAmountMin;
  if (filter.totalAmountMax !== undefined) query.totalAmountMax = filter.totalAmountMax;
  if (filter.currency) query.currency = filter.currency;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parsePurchaseOrderFilter(params: URLSearchParams): PurchaseOrderFilter {
  const filter: PurchaseOrderFilter = {};
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
  const poNumber = params.get('poNumber');
  if (poNumber) filter.poNumber = poNumber;
  const vendorId = params.get('vendorId');
  if (vendorId) filter.vendorId = vendorId;
  const orderedOnFrom = params.get('orderedOnFrom');
  if (orderedOnFrom) filter.orderedOnFrom = orderedOnFrom;
  const orderedOnTo = params.get('orderedOnTo');
  if (orderedOnTo) filter.orderedOnTo = orderedOnTo;
  const totalAmountMin = params.get('totalAmountMin');
  if (totalAmountMin !== null && totalAmountMin !== '') filter.totalAmountMin = Number(totalAmountMin);
  const totalAmountMax = params.get('totalAmountMax');
  if (totalAmountMax !== null && totalAmountMax !== '') filter.totalAmountMax = Number(totalAmountMax);
  const currency = params.get('currency');
  if (currency) filter.currency = currency;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as PurchaseOrderCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializePurchaseOrderFilter(filter: PurchaseOrderFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.poNumber) params.set('poNumber', filter.poNumber);
  if (filter.vendorId) params.set('vendorId', filter.vendorId);
  if (filter.orderedOnFrom) params.set('orderedOnFrom', filter.orderedOnFrom);
  if (filter.orderedOnTo) params.set('orderedOnTo', filter.orderedOnTo);
  if (filter.totalAmountMin !== undefined) params.set('totalAmountMin', String(filter.totalAmountMin));
  if (filter.totalAmountMax !== undefined) params.set('totalAmountMax', String(filter.totalAmountMax));
  if (filter.currency) params.set('currency', filter.currency);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countPurchaseOrderActiveFilters(filter: PurchaseOrderFilter): number {
  return Array.from(serializePurchaseOrderFilter(filter).keys()).length;
}
