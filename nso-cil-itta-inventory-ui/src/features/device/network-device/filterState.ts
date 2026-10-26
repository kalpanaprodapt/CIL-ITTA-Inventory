import type { LifecycleStatus } from '@/shared/types';
import type { NetworkDeviceFilter, NetworkDeviceDeviceRole, NetworkDeviceLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toNetworkDeviceQuery(filter: NetworkDeviceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.hostname) query.hostname = filter.hostname;
  if (filter.deviceRole?.length) query.deviceRole = filter.deviceRole.join(',');
  if (filter.mgmtIp) query.mgmtIp = filter.mgmtIp;
  if (filter.serialNumber) query.serialNumber = filter.serialNumber;
  if (filter.modelId) query.modelId = filter.modelId;
  if (filter.rackUnitId) query.rackUnitId = filter.rackUnitId;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  return query;
}

export function parseNetworkDeviceFilter(params: URLSearchParams): NetworkDeviceFilter {
  const filter: NetworkDeviceFilter = {};
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
  const hostname = params.get('hostname');
  if (hostname) filter.hostname = hostname;
  const deviceRole = params.get('deviceRole');
  if (deviceRole) filter.deviceRole = deviceRole.split(',') as NetworkDeviceDeviceRole[];
  const mgmtIp = params.get('mgmtIp');
  if (mgmtIp) filter.mgmtIp = mgmtIp;
  const serialNumber = params.get('serialNumber');
  if (serialNumber) filter.serialNumber = serialNumber;
  const modelId = params.get('modelId');
  if (modelId) filter.modelId = modelId;
  const rackUnitId = params.get('rackUnitId');
  if (rackUnitId) filter.rackUnitId = rackUnitId;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as NetworkDeviceLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  return filter;
}

export function serializeNetworkDeviceFilter(filter: NetworkDeviceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.hostname) params.set('hostname', filter.hostname);
  if (filter.deviceRole?.length) params.set('deviceRole', filter.deviceRole.join(','));
  if (filter.mgmtIp) params.set('mgmtIp', filter.mgmtIp);
  if (filter.serialNumber) params.set('serialNumber', filter.serialNumber);
  if (filter.modelId) params.set('modelId', filter.modelId);
  if (filter.rackUnitId) params.set('rackUnitId', filter.rackUnitId);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  return params;
}

export function countNetworkDeviceActiveFilters(filter: NetworkDeviceFilter): number {
  return Array.from(serializeNetworkDeviceFilter(filter).keys()).length;
}
