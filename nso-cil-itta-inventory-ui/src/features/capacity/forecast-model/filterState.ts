import type { LifecycleStatus } from '@/shared/types';
import type { ForecastModelFilter, ForecastModelAlgorithm, ForecastModelCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toForecastModelQuery(filter: ForecastModelFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.modelName) query.modelName = filter.modelName;
  if (filter.algorithm?.length) query.algorithm = filter.algorithm.join(',');
  if (filter.trainedOnFrom) query.trainedOnFrom = filter.trainedOnFrom;
  if (filter.trainedOnTo) query.trainedOnTo = filter.trainedOnTo;
  if (filter.mapeMin !== undefined) query.mapeMin = filter.mapeMin;
  if (filter.mapeMax !== undefined) query.mapeMax = filter.mapeMax;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseForecastModelFilter(params: URLSearchParams): ForecastModelFilter {
  const filter: ForecastModelFilter = {};
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
  const modelName = params.get('modelName');
  if (modelName) filter.modelName = modelName;
  const algorithm = params.get('algorithm');
  if (algorithm) filter.algorithm = algorithm.split(',') as ForecastModelAlgorithm[];
  const trainedOnFrom = params.get('trainedOnFrom');
  if (trainedOnFrom) filter.trainedOnFrom = trainedOnFrom;
  const trainedOnTo = params.get('trainedOnTo');
  if (trainedOnTo) filter.trainedOnTo = trainedOnTo;
  const mapeMin = params.get('mapeMin');
  if (mapeMin !== null && mapeMin !== '') filter.mapeMin = Number(mapeMin);
  const mapeMax = params.get('mapeMax');
  if (mapeMax !== null && mapeMax !== '') filter.mapeMax = Number(mapeMax);
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ForecastModelCriticality[];
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

export function serializeForecastModelFilter(filter: ForecastModelFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.modelName) params.set('modelName', filter.modelName);
  if (filter.algorithm?.length) params.set('algorithm', filter.algorithm.join(','));
  if (filter.trainedOnFrom) params.set('trainedOnFrom', filter.trainedOnFrom);
  if (filter.trainedOnTo) params.set('trainedOnTo', filter.trainedOnTo);
  if (filter.mapeMin !== undefined) params.set('mapeMin', String(filter.mapeMin));
  if (filter.mapeMax !== undefined) params.set('mapeMax', String(filter.mapeMax));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countForecastModelActiveFilters(filter: ForecastModelFilter): number {
  return Array.from(serializeForecastModelFilter(filter).keys()).length;
}
