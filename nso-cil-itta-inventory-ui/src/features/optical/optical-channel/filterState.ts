import type { LifecycleStatus } from '@/shared/types';
import type { OpticalChannelFilter, OpticalChannelGridType, OpticalChannelCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toOpticalChannelQuery(filter: OpticalChannelFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.wavelengthNmMin !== undefined) query.wavelengthNmMin = filter.wavelengthNmMin;
  if (filter.wavelengthNmMax !== undefined) query.wavelengthNmMax = filter.wavelengthNmMax;
  if (filter.channelNumberMin !== undefined) query.channelNumberMin = filter.channelNumberMin;
  if (filter.channelNumberMax !== undefined) query.channelNumberMax = filter.channelNumberMax;
  if (filter.gridType?.length) query.gridType = filter.gridType.join(',');
  if (filter.bitrateGbpsMin !== undefined) query.bitrateGbpsMin = filter.bitrateGbpsMin;
  if (filter.bitrateGbpsMax !== undefined) query.bitrateGbpsMax = filter.bitrateGbpsMax;
  if (filter.systemId) query.systemId = filter.systemId;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseOpticalChannelFilter(params: URLSearchParams): OpticalChannelFilter {
  const filter: OpticalChannelFilter = {};
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
  const wavelengthNmMin = params.get('wavelengthNmMin');
  if (wavelengthNmMin !== null && wavelengthNmMin !== '') filter.wavelengthNmMin = Number(wavelengthNmMin);
  const wavelengthNmMax = params.get('wavelengthNmMax');
  if (wavelengthNmMax !== null && wavelengthNmMax !== '') filter.wavelengthNmMax = Number(wavelengthNmMax);
  const channelNumberMin = params.get('channelNumberMin');
  if (channelNumberMin !== null && channelNumberMin !== '') filter.channelNumberMin = Number(channelNumberMin);
  const channelNumberMax = params.get('channelNumberMax');
  if (channelNumberMax !== null && channelNumberMax !== '') filter.channelNumberMax = Number(channelNumberMax);
  const gridType = params.get('gridType');
  if (gridType) filter.gridType = gridType.split(',') as OpticalChannelGridType[];
  const bitrateGbpsMin = params.get('bitrateGbpsMin');
  if (bitrateGbpsMin !== null && bitrateGbpsMin !== '') filter.bitrateGbpsMin = Number(bitrateGbpsMin);
  const bitrateGbpsMax = params.get('bitrateGbpsMax');
  if (bitrateGbpsMax !== null && bitrateGbpsMax !== '') filter.bitrateGbpsMax = Number(bitrateGbpsMax);
  const systemId = params.get('systemId');
  if (systemId) filter.systemId = systemId;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as OpticalChannelCriticality[];
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

export function serializeOpticalChannelFilter(filter: OpticalChannelFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.wavelengthNmMin !== undefined) params.set('wavelengthNmMin', String(filter.wavelengthNmMin));
  if (filter.wavelengthNmMax !== undefined) params.set('wavelengthNmMax', String(filter.wavelengthNmMax));
  if (filter.channelNumberMin !== undefined) params.set('channelNumberMin', String(filter.channelNumberMin));
  if (filter.channelNumberMax !== undefined) params.set('channelNumberMax', String(filter.channelNumberMax));
  if (filter.gridType?.length) params.set('gridType', filter.gridType.join(','));
  if (filter.bitrateGbpsMin !== undefined) params.set('bitrateGbpsMin', String(filter.bitrateGbpsMin));
  if (filter.bitrateGbpsMax !== undefined) params.set('bitrateGbpsMax', String(filter.bitrateGbpsMax));
  if (filter.systemId) params.set('systemId', filter.systemId);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countOpticalChannelActiveFilters(filter: OpticalChannelFilter): number {
  return Array.from(serializeOpticalChannelFilter(filter).keys()).length;
}
