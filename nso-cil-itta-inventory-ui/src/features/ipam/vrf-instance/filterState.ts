import type { LifecycleStatus } from '@/shared/types';
import type { VrfInstanceFilter, VrfInstanceLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toVrfInstanceQuery(filter: VrfInstanceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vrfName) query.vrfName = filter.vrfName;
  if (filter.routeDistinguisher) query.routeDistinguisher = filter.routeDistinguisher;
  if (filter.importTargets) query.importTargets = filter.importTargets;
  if (filter.exportTargets) query.exportTargets = filter.exportTargets;
  if (filter.deviceId) query.deviceId = filter.deviceId;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseVrfInstanceFilter(params: URLSearchParams): VrfInstanceFilter {
  const filter: VrfInstanceFilter = {};
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
  const vrfName = params.get('vrfName');
  if (vrfName) filter.vrfName = vrfName;
  const routeDistinguisher = params.get('routeDistinguisher');
  if (routeDistinguisher) filter.routeDistinguisher = routeDistinguisher;
  const importTargets = params.get('importTargets');
  if (importTargets) filter.importTargets = importTargets;
  const exportTargets = params.get('exportTargets');
  if (exportTargets) filter.exportTargets = exportTargets;
  const deviceId = params.get('deviceId');
  if (deviceId) filter.deviceId = deviceId;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as VrfInstanceLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeVrfInstanceFilter(filter: VrfInstanceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vrfName) params.set('vrfName', filter.vrfName);
  if (filter.routeDistinguisher) params.set('routeDistinguisher', filter.routeDistinguisher);
  if (filter.importTargets) params.set('importTargets', filter.importTargets);
  if (filter.exportTargets) params.set('exportTargets', filter.exportTargets);
  if (filter.deviceId) params.set('deviceId', filter.deviceId);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countVrfInstanceActiveFilters(filter: VrfInstanceFilter): number {
  return Array.from(serializeVrfInstanceFilter(filter).keys()).length;
}
