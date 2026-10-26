import type { LifecycleStatus } from '@/shared/types';
import type { TicketLinkFilter, TicketLinkTicketSystem, TicketLinkCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toTicketLinkQuery(filter: TicketLinkFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.alarmId) query.alarmId = filter.alarmId;
  if (filter.ticketSystem?.length) query.ticketSystem = filter.ticketSystem.join(',');
  if (filter.ticketNumber) query.ticketNumber = filter.ticketNumber;
  if (filter.linkedAtFrom) query.linkedAtFrom = filter.linkedAtFrom;
  if (filter.linkedAtTo) query.linkedAtTo = filter.linkedAtTo;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseTicketLinkFilter(params: URLSearchParams): TicketLinkFilter {
  const filter: TicketLinkFilter = {};
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
  const alarmId = params.get('alarmId');
  if (alarmId) filter.alarmId = alarmId;
  const ticketSystem = params.get('ticketSystem');
  if (ticketSystem) filter.ticketSystem = ticketSystem.split(',') as TicketLinkTicketSystem[];
  const ticketNumber = params.get('ticketNumber');
  if (ticketNumber) filter.ticketNumber = ticketNumber;
  const linkedAtFrom = params.get('linkedAtFrom');
  if (linkedAtFrom) filter.linkedAtFrom = linkedAtFrom;
  const linkedAtTo = params.get('linkedAtTo');
  if (linkedAtTo) filter.linkedAtTo = linkedAtTo;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as TicketLinkCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeTicketLinkFilter(filter: TicketLinkFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.alarmId) params.set('alarmId', filter.alarmId);
  if (filter.ticketSystem?.length) params.set('ticketSystem', filter.ticketSystem.join(','));
  if (filter.ticketNumber) params.set('ticketNumber', filter.ticketNumber);
  if (filter.linkedAtFrom) params.set('linkedAtFrom', filter.linkedAtFrom);
  if (filter.linkedAtTo) params.set('linkedAtTo', filter.linkedAtTo);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countTicketLinkActiveFilters(filter: TicketLinkFilter): number {
  return Array.from(serializeTicketLinkFilter(filter).keys()).length;
}
