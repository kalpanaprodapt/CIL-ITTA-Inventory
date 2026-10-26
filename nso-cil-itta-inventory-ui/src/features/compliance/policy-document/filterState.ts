import type { LifecycleStatus } from '@/shared/types';
import type { PolicyDocumentFilter, PolicyDocumentLifecycleStage, PolicyDocumentCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPolicyDocumentQuery(filter: PolicyDocumentFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.title) query.title = filter.title;
  if (filter.docVersion) query.docVersion = filter.docVersion;
  if (filter.effectiveDateFrom) query.effectiveDateFrom = filter.effectiveDateFrom;
  if (filter.effectiveDateTo) query.effectiveDateTo = filter.effectiveDateTo;
  if (filter.owner) query.owner = filter.owner;
  if (filter.documentUrl) query.documentUrl = filter.documentUrl;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parsePolicyDocumentFilter(params: URLSearchParams): PolicyDocumentFilter {
  const filter: PolicyDocumentFilter = {};
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
  const title = params.get('title');
  if (title) filter.title = title;
  const docVersion = params.get('docVersion');
  if (docVersion) filter.docVersion = docVersion;
  const effectiveDateFrom = params.get('effectiveDateFrom');
  if (effectiveDateFrom) filter.effectiveDateFrom = effectiveDateFrom;
  const effectiveDateTo = params.get('effectiveDateTo');
  if (effectiveDateTo) filter.effectiveDateTo = effectiveDateTo;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const documentUrl = params.get('documentUrl');
  if (documentUrl) filter.documentUrl = documentUrl;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as PolicyDocumentLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as PolicyDocumentCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializePolicyDocumentFilter(filter: PolicyDocumentFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.title) params.set('title', filter.title);
  if (filter.docVersion) params.set('docVersion', filter.docVersion);
  if (filter.effectiveDateFrom) params.set('effectiveDateFrom', filter.effectiveDateFrom);
  if (filter.effectiveDateTo) params.set('effectiveDateTo', filter.effectiveDateTo);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.documentUrl) params.set('documentUrl', filter.documentUrl);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countPolicyDocumentActiveFilters(filter: PolicyDocumentFilter): number {
  return Array.from(serializePolicyDocumentFilter(filter).keys()).length;
}
