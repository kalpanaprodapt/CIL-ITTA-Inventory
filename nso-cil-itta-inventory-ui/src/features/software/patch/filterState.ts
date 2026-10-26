import type { LifecycleStatus } from '@/shared/types';
import type { PatchFilter, PatchSeverity, PatchCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPatchQuery(filter: PatchFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.versionId) query.versionId = filter.versionId;
  if (filter.patchId) query.patchId = filter.patchId;
  if (filter.severity?.length) query.severity = filter.severity.join(',');
  if (filter.releasedOnFrom) query.releasedOnFrom = filter.releasedOnFrom;
  if (filter.releasedOnTo) query.releasedOnTo = filter.releasedOnTo;
  if (filter.rebootRequired !== undefined) query.rebootRequired = filter.rebootRequired;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parsePatchFilter(params: URLSearchParams): PatchFilter {
  const filter: PatchFilter = {};
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
  const versionId = params.get('versionId');
  if (versionId) filter.versionId = versionId;
  const patchId = params.get('patchId');
  if (patchId) filter.patchId = patchId;
  const severity = params.get('severity');
  if (severity) filter.severity = severity.split(',') as PatchSeverity[];
  const releasedOnFrom = params.get('releasedOnFrom');
  if (releasedOnFrom) filter.releasedOnFrom = releasedOnFrom;
  const releasedOnTo = params.get('releasedOnTo');
  if (releasedOnTo) filter.releasedOnTo = releasedOnTo;
  const rebootRequired = params.get('rebootRequired');
  if (rebootRequired !== null) filter.rebootRequired = rebootRequired === 'true';
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as PatchCriticality[];
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
  return filter;
}

export function serializePatchFilter(filter: PatchFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.versionId) params.set('versionId', filter.versionId);
  if (filter.patchId) params.set('patchId', filter.patchId);
  if (filter.severity?.length) params.set('severity', filter.severity.join(','));
  if (filter.releasedOnFrom) params.set('releasedOnFrom', filter.releasedOnFrom);
  if (filter.releasedOnTo) params.set('releasedOnTo', filter.releasedOnTo);
  if (filter.rebootRequired !== undefined) params.set('rebootRequired', String(filter.rebootRequired));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countPatchActiveFilters(filter: PatchFilter): number {
  return Array.from(serializePatchFilter(filter).keys()).length;
}
