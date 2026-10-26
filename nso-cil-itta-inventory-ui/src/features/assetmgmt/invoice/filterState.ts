import type { LifecycleStatus } from '@/shared/types';
import type { InvoiceFilter } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toInvoiceQuery(filter: InvoiceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.invoiceNumber) query.invoiceNumber = filter.invoiceNumber;
  if (filter.poId) query.poId = filter.poId;
  if (filter.amountMin !== undefined) query.amountMin = filter.amountMin;
  if (filter.amountMax !== undefined) query.amountMax = filter.amountMax;
  if (filter.dueDateFrom) query.dueDateFrom = filter.dueDateFrom;
  if (filter.dueDateTo) query.dueDateTo = filter.dueDateTo;
  if (filter.paid !== undefined) query.paid = filter.paid;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseInvoiceFilter(params: URLSearchParams): InvoiceFilter {
  const filter: InvoiceFilter = {};
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
  const invoiceNumber = params.get('invoiceNumber');
  if (invoiceNumber) filter.invoiceNumber = invoiceNumber;
  const poId = params.get('poId');
  if (poId) filter.poId = poId;
  const amountMin = params.get('amountMin');
  if (amountMin !== null && amountMin !== '') filter.amountMin = Number(amountMin);
  const amountMax = params.get('amountMax');
  if (amountMax !== null && amountMax !== '') filter.amountMax = Number(amountMax);
  const dueDateFrom = params.get('dueDateFrom');
  if (dueDateFrom) filter.dueDateFrom = dueDateFrom;
  const dueDateTo = params.get('dueDateTo');
  if (dueDateTo) filter.dueDateTo = dueDateTo;
  const paid = params.get('paid');
  if (paid !== null) filter.paid = paid === 'true';
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
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
  return filter;
}

export function serializeInvoiceFilter(filter: InvoiceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.invoiceNumber) params.set('invoiceNumber', filter.invoiceNumber);
  if (filter.poId) params.set('poId', filter.poId);
  if (filter.amountMin !== undefined) params.set('amountMin', String(filter.amountMin));
  if (filter.amountMax !== undefined) params.set('amountMax', String(filter.amountMax));
  if (filter.dueDateFrom) params.set('dueDateFrom', filter.dueDateFrom);
  if (filter.dueDateTo) params.set('dueDateTo', filter.dueDateTo);
  if (filter.paid !== undefined) params.set('paid', String(filter.paid));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countInvoiceActiveFilters(filter: InvoiceFilter): number {
  return Array.from(serializeInvoiceFilter(filter).keys()).length;
}
