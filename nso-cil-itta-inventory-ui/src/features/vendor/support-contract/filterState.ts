import type { LifecycleStatus } from '@/shared/types';
import type { SupportContractFilter, SupportContractCoverageLevel, SupportContractCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSupportContractQuery(filter: SupportContractFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vendorId) query.vendorId = filter.vendorId;
  if (filter.contractNumber) query.contractNumber = filter.contractNumber;
  if (filter.startDateFrom) query.startDateFrom = filter.startDateFrom;
  if (filter.startDateTo) query.startDateTo = filter.startDateTo;
  if (filter.endDateFrom) query.endDateFrom = filter.endDateFrom;
  if (filter.endDateTo) query.endDateTo = filter.endDateTo;
  if (filter.coverageLevel?.length) query.coverageLevel = filter.coverageLevel.join(',');
  if (filter.annualCostMin !== undefined) query.annualCostMin = filter.annualCostMin;
  if (filter.annualCostMax !== undefined) query.annualCostMax = filter.annualCostMax;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseSupportContractFilter(params: URLSearchParams): SupportContractFilter {
  const filter: SupportContractFilter = {};
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
  const vendorId = params.get('vendorId');
  if (vendorId) filter.vendorId = vendorId;
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
  const coverageLevel = params.get('coverageLevel');
  if (coverageLevel) filter.coverageLevel = coverageLevel.split(',') as SupportContractCoverageLevel[];
  const annualCostMin = params.get('annualCostMin');
  if (annualCostMin !== null && annualCostMin !== '') filter.annualCostMin = Number(annualCostMin);
  const annualCostMax = params.get('annualCostMax');
  if (annualCostMax !== null && annualCostMax !== '') filter.annualCostMax = Number(annualCostMax);
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SupportContractCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeSupportContractFilter(filter: SupportContractFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vendorId) params.set('vendorId', filter.vendorId);
  if (filter.contractNumber) params.set('contractNumber', filter.contractNumber);
  if (filter.startDateFrom) params.set('startDateFrom', filter.startDateFrom);
  if (filter.startDateTo) params.set('startDateTo', filter.startDateTo);
  if (filter.endDateFrom) params.set('endDateFrom', filter.endDateFrom);
  if (filter.endDateTo) params.set('endDateTo', filter.endDateTo);
  if (filter.coverageLevel?.length) params.set('coverageLevel', filter.coverageLevel.join(','));
  if (filter.annualCostMin !== undefined) params.set('annualCostMin', String(filter.annualCostMin));
  if (filter.annualCostMax !== undefined) params.set('annualCostMax', String(filter.annualCostMax));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countSupportContractActiveFilters(filter: SupportContractFilter): number {
  return Array.from(serializeSupportContractFilter(filter).keys()).length;
}
