import type { LifecycleStatus } from '@/shared/types';
import type { ContractFilter, ContractCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toContractQuery(filter: ContractFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.customerId) query.customerId = filter.customerId;
  if (filter.contractNumber) query.contractNumber = filter.contractNumber;
  if (filter.startDateFrom) query.startDateFrom = filter.startDateFrom;
  if (filter.startDateTo) query.startDateTo = filter.startDateTo;
  if (filter.endDateFrom) query.endDateFrom = filter.endDateFrom;
  if (filter.endDateTo) query.endDateTo = filter.endDateTo;
  if (filter.termMonthsMin !== undefined) query.termMonthsMin = filter.termMonthsMin;
  if (filter.termMonthsMax !== undefined) query.termMonthsMax = filter.termMonthsMax;
  if (filter.autoRenew !== undefined) query.autoRenew = filter.autoRenew;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseContractFilter(params: URLSearchParams): ContractFilter {
  const filter: ContractFilter = {};
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
  const customerId = params.get('customerId');
  if (customerId) filter.customerId = customerId;
  const contractNumber = params.get('contractNumber');
  if (contractNumber) filter.contractNumber = contractNumber;
  const startDateFrom = params.get('startDateFrom');
  if (startDateFrom) filter.startDateFrom = startDateFrom;
  const startDateTo = params.get('startDateTo');
  if (startDateTo) filter.startDateTo = startDateTo;
  const endDateFrom = params.get('endDateFrom');
  if (endDateFrom) filter.endDateFrom = endDateFrom;
  const endDateTo = params.get('endDateTo');
  if (endDateTo) filter.endDateTo = endDateTo;
  const termMonthsMin = params.get('termMonthsMin');
  if (termMonthsMin !== null && termMonthsMin !== '') filter.termMonthsMin = Number(termMonthsMin);
  const termMonthsMax = params.get('termMonthsMax');
  if (termMonthsMax !== null && termMonthsMax !== '') filter.termMonthsMax = Number(termMonthsMax);
  const autoRenew = params.get('autoRenew');
  if (autoRenew !== null) filter.autoRenew = autoRenew === 'true';
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ContractCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeContractFilter(filter: ContractFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.customerId) params.set('customerId', filter.customerId);
  if (filter.contractNumber) params.set('contractNumber', filter.contractNumber);
  if (filter.startDateFrom) params.set('startDateFrom', filter.startDateFrom);
  if (filter.startDateTo) params.set('startDateTo', filter.startDateTo);
  if (filter.endDateFrom) params.set('endDateFrom', filter.endDateFrom);
  if (filter.endDateTo) params.set('endDateTo', filter.endDateTo);
  if (filter.termMonthsMin !== undefined) params.set('termMonthsMin', String(filter.termMonthsMin));
  if (filter.termMonthsMax !== undefined) params.set('termMonthsMax', String(filter.termMonthsMax));
  if (filter.autoRenew !== undefined) params.set('autoRenew', String(filter.autoRenew));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countContractActiveFilters(filter: ContractFilter): number {
  return Array.from(serializeContractFilter(filter).keys()).length;
}
