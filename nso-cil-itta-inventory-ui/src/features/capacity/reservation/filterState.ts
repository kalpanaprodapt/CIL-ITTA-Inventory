import type { LifecycleStatus } from '@/shared/types';
import type { ReservationFilter, ReservationLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toReservationQuery(filter: ReservationFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.resourceRef) query.resourceRef = filter.resourceRef;
  if (filter.reservedFor) query.reservedFor = filter.reservedFor;
  if (filter.quantityMin !== undefined) query.quantityMin = filter.quantityMin;
  if (filter.quantityMax !== undefined) query.quantityMax = filter.quantityMax;
  if (filter.startDateFrom) query.startDateFrom = filter.startDateFrom;
  if (filter.startDateTo) query.startDateTo = filter.startDateTo;
  if (filter.endDateFrom) query.endDateFrom = filter.endDateFrom;
  if (filter.endDateTo) query.endDateTo = filter.endDateTo;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseReservationFilter(params: URLSearchParams): ReservationFilter {
  const filter: ReservationFilter = {};
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
  const resourceRef = params.get('resourceRef');
  if (resourceRef) filter.resourceRef = resourceRef;
  const reservedFor = params.get('reservedFor');
  if (reservedFor) filter.reservedFor = reservedFor;
  const quantityMin = params.get('quantityMin');
  if (quantityMin !== null && quantityMin !== '') filter.quantityMin = Number(quantityMin);
  const quantityMax = params.get('quantityMax');
  if (quantityMax !== null && quantityMax !== '') filter.quantityMax = Number(quantityMax);
  const startDateFrom = params.get('startDateFrom');
  if (startDateFrom) filter.startDateFrom = startDateFrom;
  const startDateTo = params.get('startDateTo');
  if (startDateTo) filter.startDateTo = startDateTo;
  const endDateFrom = params.get('endDateFrom');
  if (endDateFrom) filter.endDateFrom = endDateFrom;
  const endDateTo = params.get('endDateTo');
  if (endDateTo) filter.endDateTo = endDateTo;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ReservationLifecycleStage[];
  return filter;
}

export function serializeReservationFilter(filter: ReservationFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.resourceRef) params.set('resourceRef', filter.resourceRef);
  if (filter.reservedFor) params.set('reservedFor', filter.reservedFor);
  if (filter.quantityMin !== undefined) params.set('quantityMin', String(filter.quantityMin));
  if (filter.quantityMax !== undefined) params.set('quantityMax', String(filter.quantityMax));
  if (filter.startDateFrom) params.set('startDateFrom', filter.startDateFrom);
  if (filter.startDateTo) params.set('startDateTo', filter.startDateTo);
  if (filter.endDateFrom) params.set('endDateFrom', filter.endDateFrom);
  if (filter.endDateTo) params.set('endDateTo', filter.endDateTo);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countReservationActiveFilters(filter: ReservationFilter): number {
  return Array.from(serializeReservationFilter(filter).keys()).length;
}
