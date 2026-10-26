import type { LifecycleStatus } from '@/shared/types';
import type { ImportBatchFilter, ImportBatchBatchState, ImportBatchLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toImportBatchQuery(filter: ImportBatchFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.fileName) query.fileName = filter.fileName;
  if (filter.entityType) query.entityType = filter.entityType;
  if (filter.totalRowsMin !== undefined) query.totalRowsMin = filter.totalRowsMin;
  if (filter.totalRowsMax !== undefined) query.totalRowsMax = filter.totalRowsMax;
  if (filter.processedRowsMin !== undefined) query.processedRowsMin = filter.processedRowsMin;
  if (filter.processedRowsMax !== undefined) query.processedRowsMax = filter.processedRowsMax;
  if (filter.failedRowsMin !== undefined) query.failedRowsMin = filter.failedRowsMin;
  if (filter.failedRowsMax !== undefined) query.failedRowsMax = filter.failedRowsMax;
  if (filter.batchState?.length) query.batchState = filter.batchState.join(',');
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseImportBatchFilter(params: URLSearchParams): ImportBatchFilter {
  const filter: ImportBatchFilter = {};
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
  const fileName = params.get('fileName');
  if (fileName) filter.fileName = fileName;
  const entityType = params.get('entityType');
  if (entityType) filter.entityType = entityType;
  const totalRowsMin = params.get('totalRowsMin');
  if (totalRowsMin !== null && totalRowsMin !== '') filter.totalRowsMin = Number(totalRowsMin);
  const totalRowsMax = params.get('totalRowsMax');
  if (totalRowsMax !== null && totalRowsMax !== '') filter.totalRowsMax = Number(totalRowsMax);
  const processedRowsMin = params.get('processedRowsMin');
  if (processedRowsMin !== null && processedRowsMin !== '') filter.processedRowsMin = Number(processedRowsMin);
  const processedRowsMax = params.get('processedRowsMax');
  if (processedRowsMax !== null && processedRowsMax !== '') filter.processedRowsMax = Number(processedRowsMax);
  const failedRowsMin = params.get('failedRowsMin');
  if (failedRowsMin !== null && failedRowsMin !== '') filter.failedRowsMin = Number(failedRowsMin);
  const failedRowsMax = params.get('failedRowsMax');
  if (failedRowsMax !== null && failedRowsMax !== '') filter.failedRowsMax = Number(failedRowsMax);
  const batchState = params.get('batchState');
  if (batchState) filter.batchState = batchState.split(',') as ImportBatchBatchState[];
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ImportBatchLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeImportBatchFilter(filter: ImportBatchFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.fileName) params.set('fileName', filter.fileName);
  if (filter.entityType) params.set('entityType', filter.entityType);
  if (filter.totalRowsMin !== undefined) params.set('totalRowsMin', String(filter.totalRowsMin));
  if (filter.totalRowsMax !== undefined) params.set('totalRowsMax', String(filter.totalRowsMax));
  if (filter.processedRowsMin !== undefined) params.set('processedRowsMin', String(filter.processedRowsMin));
  if (filter.processedRowsMax !== undefined) params.set('processedRowsMax', String(filter.processedRowsMax));
  if (filter.failedRowsMin !== undefined) params.set('failedRowsMin', String(filter.failedRowsMin));
  if (filter.failedRowsMax !== undefined) params.set('failedRowsMax', String(filter.failedRowsMax));
  if (filter.batchState?.length) params.set('batchState', filter.batchState.join(','));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countImportBatchActiveFilters(filter: ImportBatchFilter): number {
  return Array.from(serializeImportBatchFilter(filter).keys()).length;
}
