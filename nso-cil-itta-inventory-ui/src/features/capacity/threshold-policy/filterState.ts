import type { LifecycleStatus } from '@/shared/types';
import type { ThresholdPolicyFilter, ThresholdPolicyCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toThresholdPolicyQuery(filter: ThresholdPolicyFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.policyName) query.policyName = filter.policyName;
  if (filter.metric) query.metric = filter.metric;
  if (filter.warnPctMin !== undefined) query.warnPctMin = filter.warnPctMin;
  if (filter.warnPctMax !== undefined) query.warnPctMax = filter.warnPctMax;
  if (filter.critPctMin !== undefined) query.critPctMin = filter.critPctMin;
  if (filter.critPctMax !== undefined) query.critPctMax = filter.critPctMax;
  if (filter.evaluationWindowMinutesMin !== undefined) query.evaluationWindowMinutesMin = filter.evaluationWindowMinutesMin;
  if (filter.evaluationWindowMinutesMax !== undefined) query.evaluationWindowMinutesMax = filter.evaluationWindowMinutesMax;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseThresholdPolicyFilter(params: URLSearchParams): ThresholdPolicyFilter {
  const filter: ThresholdPolicyFilter = {};
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
  const policyName = params.get('policyName');
  if (policyName) filter.policyName = policyName;
  const metric = params.get('metric');
  if (metric) filter.metric = metric;
  const warnPctMin = params.get('warnPctMin');
  if (warnPctMin !== null && warnPctMin !== '') filter.warnPctMin = Number(warnPctMin);
  const warnPctMax = params.get('warnPctMax');
  if (warnPctMax !== null && warnPctMax !== '') filter.warnPctMax = Number(warnPctMax);
  const critPctMin = params.get('critPctMin');
  if (critPctMin !== null && critPctMin !== '') filter.critPctMin = Number(critPctMin);
  const critPctMax = params.get('critPctMax');
  if (critPctMax !== null && critPctMax !== '') filter.critPctMax = Number(critPctMax);
  const evaluationWindowMinutesMin = params.get('evaluationWindowMinutesMin');
  if (evaluationWindowMinutesMin !== null && evaluationWindowMinutesMin !== '') filter.evaluationWindowMinutesMin = Number(evaluationWindowMinutesMin);
  const evaluationWindowMinutesMax = params.get('evaluationWindowMinutesMax');
  if (evaluationWindowMinutesMax !== null && evaluationWindowMinutesMax !== '') filter.evaluationWindowMinutesMax = Number(evaluationWindowMinutesMax);
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ThresholdPolicyCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeThresholdPolicyFilter(filter: ThresholdPolicyFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.policyName) params.set('policyName', filter.policyName);
  if (filter.metric) params.set('metric', filter.metric);
  if (filter.warnPctMin !== undefined) params.set('warnPctMin', String(filter.warnPctMin));
  if (filter.warnPctMax !== undefined) params.set('warnPctMax', String(filter.warnPctMax));
  if (filter.critPctMin !== undefined) params.set('critPctMin', String(filter.critPctMin));
  if (filter.critPctMax !== undefined) params.set('critPctMax', String(filter.critPctMax));
  if (filter.evaluationWindowMinutesMin !== undefined) params.set('evaluationWindowMinutesMin', String(filter.evaluationWindowMinutesMin));
  if (filter.evaluationWindowMinutesMax !== undefined) params.set('evaluationWindowMinutesMax', String(filter.evaluationWindowMinutesMax));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countThresholdPolicyActiveFilters(filter: ThresholdPolicyFilter): number {
  return Array.from(serializeThresholdPolicyFilter(filter).keys()).length;
}
