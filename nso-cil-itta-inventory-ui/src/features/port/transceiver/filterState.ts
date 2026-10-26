import type { LifecycleStatus } from '@/shared/types';
import type { TransceiverFilter, TransceiverFormFactor, TransceiverLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toTransceiverQuery(filter: TransceiverFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.portId) query.portId = filter.portId;
  if (filter.formFactor?.length) query.formFactor = filter.formFactor.join(',');
  if (filter.wavelengthNmMin !== undefined) query.wavelengthNmMin = filter.wavelengthNmMin;
  if (filter.wavelengthNmMax !== undefined) query.wavelengthNmMax = filter.wavelengthNmMax;
  if (filter.reachKmMin !== undefined) query.reachKmMin = filter.reachKmMin;
  if (filter.reachKmMax !== undefined) query.reachKmMax = filter.reachKmMax;
  if (filter.serialNumber) query.serialNumber = filter.serialNumber;
  if (filter.vendorPart) query.vendorPart = filter.vendorPart;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseTransceiverFilter(params: URLSearchParams): TransceiverFilter {
  const filter: TransceiverFilter = {};
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
  const portId = params.get('portId');
  if (portId) filter.portId = portId;
  const formFactor = params.get('formFactor');
  if (formFactor) filter.formFactor = formFactor.split(',') as TransceiverFormFactor[];
  const wavelengthNmMin = params.get('wavelengthNmMin');
  if (wavelengthNmMin !== null && wavelengthNmMin !== '') filter.wavelengthNmMin = Number(wavelengthNmMin);
  const wavelengthNmMax = params.get('wavelengthNmMax');
  if (wavelengthNmMax !== null && wavelengthNmMax !== '') filter.wavelengthNmMax = Number(wavelengthNmMax);
  const reachKmMin = params.get('reachKmMin');
  if (reachKmMin !== null && reachKmMin !== '') filter.reachKmMin = Number(reachKmMin);
  const reachKmMax = params.get('reachKmMax');
  if (reachKmMax !== null && reachKmMax !== '') filter.reachKmMax = Number(reachKmMax);
  const serialNumber = params.get('serialNumber');
  if (serialNumber) filter.serialNumber = serialNumber;
  const vendorPart = params.get('vendorPart');
  if (vendorPart) filter.vendorPart = vendorPart;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as TransceiverLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeTransceiverFilter(filter: TransceiverFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.portId) params.set('portId', filter.portId);
  if (filter.formFactor?.length) params.set('formFactor', filter.formFactor.join(','));
  if (filter.wavelengthNmMin !== undefined) params.set('wavelengthNmMin', String(filter.wavelengthNmMin));
  if (filter.wavelengthNmMax !== undefined) params.set('wavelengthNmMax', String(filter.wavelengthNmMax));
  if (filter.reachKmMin !== undefined) params.set('reachKmMin', String(filter.reachKmMin));
  if (filter.reachKmMax !== undefined) params.set('reachKmMax', String(filter.reachKmMax));
  if (filter.serialNumber) params.set('serialNumber', filter.serialNumber);
  if (filter.vendorPart) params.set('vendorPart', filter.vendorPart);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countTransceiverActiveFilters(filter: TransceiverFilter): number {
  return Array.from(serializeTransceiverFilter(filter).keys()).length;
}
