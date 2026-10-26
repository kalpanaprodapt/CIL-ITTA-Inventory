import type { LifecycleStatus } from '@/shared/types';
import type { AssetTagFilter, AssetTagTagType, AssetTagCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toAssetTagQuery(filter: AssetTagFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.assetId) query.assetId = filter.assetId;
  if (filter.tagValue) query.tagValue = filter.tagValue;
  if (filter.tagType?.length) query.tagType = filter.tagType.join(',');
  if (filter.printedOnFrom) query.printedOnFrom = filter.printedOnFrom;
  if (filter.printedOnTo) query.printedOnTo = filter.printedOnTo;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseAssetTagFilter(params: URLSearchParams): AssetTagFilter {
  const filter: AssetTagFilter = {};
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
  const assetId = params.get('assetId');
  if (assetId) filter.assetId = assetId;
  const tagValue = params.get('tagValue');
  if (tagValue) filter.tagValue = tagValue;
  const tagType = params.get('tagType');
  if (tagType) filter.tagType = tagType.split(',') as AssetTagTagType[];
  const printedOnFrom = params.get('printedOnFrom');
  if (printedOnFrom) filter.printedOnFrom = printedOnFrom;
  const printedOnTo = params.get('printedOnTo');
  if (printedOnTo) filter.printedOnTo = printedOnTo;
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as AssetTagCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeAssetTagFilter(filter: AssetTagFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.assetId) params.set('assetId', filter.assetId);
  if (filter.tagValue) params.set('tagValue', filter.tagValue);
  if (filter.tagType?.length) params.set('tagType', filter.tagType.join(','));
  if (filter.printedOnFrom) params.set('printedOnFrom', filter.printedOnFrom);
  if (filter.printedOnTo) params.set('printedOnTo', filter.printedOnTo);
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countAssetTagActiveFilters(filter: AssetTagFilter): number {
  return Array.from(serializeAssetTagFilter(filter).keys()).length;
}
