import type { LifecycleStatus } from '@/shared/types';
import type { SoftwareImageFilter, SoftwareImageLifecycleStage, SoftwareImageCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSoftwareImageQuery(filter: SoftwareImageFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vendorId) query.vendorId = filter.vendorId;
  if (filter.imageName) query.imageName = filter.imageName;
  if (filter.sizeMbMin !== undefined) query.sizeMbMin = filter.sizeMbMin;
  if (filter.sizeMbMax !== undefined) query.sizeMbMax = filter.sizeMbMax;
  if (filter.checksum) query.checksum = filter.checksum;
  if (filter.platform) query.platform = filter.platform;
  if (filter.storagePath) query.storagePath = filter.storagePath;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseSoftwareImageFilter(params: URLSearchParams): SoftwareImageFilter {
  const filter: SoftwareImageFilter = {};
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
  const imageName = params.get('imageName');
  if (imageName) filter.imageName = imageName;
  const sizeMbMin = params.get('sizeMbMin');
  if (sizeMbMin !== null && sizeMbMin !== '') filter.sizeMbMin = Number(sizeMbMin);
  const sizeMbMax = params.get('sizeMbMax');
  if (sizeMbMax !== null && sizeMbMax !== '') filter.sizeMbMax = Number(sizeMbMax);
  const checksum = params.get('checksum');
  if (checksum) filter.checksum = checksum;
  const platform = params.get('platform');
  if (platform) filter.platform = platform;
  const storagePath = params.get('storagePath');
  if (storagePath) filter.storagePath = storagePath;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as SoftwareImageLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SoftwareImageCriticality[];
  return filter;
}

export function serializeSoftwareImageFilter(filter: SoftwareImageFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vendorId) params.set('vendorId', filter.vendorId);
  if (filter.imageName) params.set('imageName', filter.imageName);
  if (filter.sizeMbMin !== undefined) params.set('sizeMbMin', String(filter.sizeMbMin));
  if (filter.sizeMbMax !== undefined) params.set('sizeMbMax', String(filter.sizeMbMax));
  if (filter.checksum) params.set('checksum', filter.checksum);
  if (filter.platform) params.set('platform', filter.platform);
  if (filter.storagePath) params.set('storagePath', filter.storagePath);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countSoftwareImageActiveFilters(filter: SoftwareImageFilter): number {
  return Array.from(serializeSoftwareImageFilter(filter).keys()).length;
}
