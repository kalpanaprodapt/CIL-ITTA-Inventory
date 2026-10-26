import type { LifecycleStatus } from '@/shared/types';
import type { PatchPanelFilter, PatchPanelPanelType, PatchPanelLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPatchPanelQuery(filter: PatchPanelFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.roomId) query.roomId = filter.roomId;
  if (filter.rackId) query.rackId = filter.rackId;
  if (filter.panelType?.length) query.panelType = filter.panelType.join(',');
  if (filter.portCountMin !== undefined) query.portCountMin = filter.portCountMin;
  if (filter.portCountMax !== undefined) query.portCountMax = filter.portCountMax;
  if (filter.label) query.label = filter.label;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parsePatchPanelFilter(params: URLSearchParams): PatchPanelFilter {
  const filter: PatchPanelFilter = {};
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
  const roomId = params.get('roomId');
  if (roomId) filter.roomId = roomId;
  const rackId = params.get('rackId');
  if (rackId) filter.rackId = rackId;
  const panelType = params.get('panelType');
  if (panelType) filter.panelType = panelType.split(',') as PatchPanelPanelType[];
  const portCountMin = params.get('portCountMin');
  if (portCountMin !== null && portCountMin !== '') filter.portCountMin = Number(portCountMin);
  const portCountMax = params.get('portCountMax');
  if (portCountMax !== null && portCountMax !== '') filter.portCountMax = Number(portCountMax);
  const label = params.get('label');
  if (label) filter.label = label;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as PatchPanelLifecycleStage[];
  return filter;
}

export function serializePatchPanelFilter(filter: PatchPanelFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.roomId) params.set('roomId', filter.roomId);
  if (filter.rackId) params.set('rackId', filter.rackId);
  if (filter.panelType?.length) params.set('panelType', filter.panelType.join(','));
  if (filter.portCountMin !== undefined) params.set('portCountMin', String(filter.portCountMin));
  if (filter.portCountMax !== undefined) params.set('portCountMax', String(filter.portCountMax));
  if (filter.label) params.set('label', filter.label);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countPatchPanelActiveFilters(filter: PatchPanelFilter): number {
  return Array.from(serializePatchPanelFilter(filter).keys()).length;
}
