import type { LifecycleStatus } from '@/shared/types';
import type { AmplifierFilter, AmplifierAmpType, AmplifierLifecycleStage, AmplifierCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toAmplifierQuery(filter: AmplifierFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.systemId) query.systemId = filter.systemId;
  if (filter.ampType?.length) query.ampType = filter.ampType.join(',');
  if (filter.gainDbMin !== undefined) query.gainDbMin = filter.gainDbMin;
  if (filter.gainDbMax !== undefined) query.gainDbMax = filter.gainDbMax;
  if (filter.outputPowerDbmMin !== undefined) query.outputPowerDbmMin = filter.outputPowerDbmMin;
  if (filter.outputPowerDbmMax !== undefined) query.outputPowerDbmMax = filter.outputPowerDbmMax;
  if (filter.noiseFigureDbMin !== undefined) query.noiseFigureDbMin = filter.noiseFigureDbMin;
  if (filter.noiseFigureDbMax !== undefined) query.noiseFigureDbMax = filter.noiseFigureDbMax;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseAmplifierFilter(params: URLSearchParams): AmplifierFilter {
  const filter: AmplifierFilter = {};
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
  const systemId = params.get('systemId');
  if (systemId) filter.systemId = systemId;
  const ampType = params.get('ampType');
  if (ampType) filter.ampType = ampType.split(',') as AmplifierAmpType[];
  const gainDbMin = params.get('gainDbMin');
  if (gainDbMin !== null && gainDbMin !== '') filter.gainDbMin = Number(gainDbMin);
  const gainDbMax = params.get('gainDbMax');
  if (gainDbMax !== null && gainDbMax !== '') filter.gainDbMax = Number(gainDbMax);
  const outputPowerDbmMin = params.get('outputPowerDbmMin');
  if (outputPowerDbmMin !== null && outputPowerDbmMin !== '') filter.outputPowerDbmMin = Number(outputPowerDbmMin);
  const outputPowerDbmMax = params.get('outputPowerDbmMax');
  if (outputPowerDbmMax !== null && outputPowerDbmMax !== '') filter.outputPowerDbmMax = Number(outputPowerDbmMax);
  const noiseFigureDbMin = params.get('noiseFigureDbMin');
  if (noiseFigureDbMin !== null && noiseFigureDbMin !== '') filter.noiseFigureDbMin = Number(noiseFigureDbMin);
  const noiseFigureDbMax = params.get('noiseFigureDbMax');
  if (noiseFigureDbMax !== null && noiseFigureDbMax !== '') filter.noiseFigureDbMax = Number(noiseFigureDbMax);
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as AmplifierLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as AmplifierCriticality[];
  return filter;
}

export function serializeAmplifierFilter(filter: AmplifierFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.systemId) params.set('systemId', filter.systemId);
  if (filter.ampType?.length) params.set('ampType', filter.ampType.join(','));
  if (filter.gainDbMin !== undefined) params.set('gainDbMin', String(filter.gainDbMin));
  if (filter.gainDbMax !== undefined) params.set('gainDbMax', String(filter.gainDbMax));
  if (filter.outputPowerDbmMin !== undefined) params.set('outputPowerDbmMin', String(filter.outputPowerDbmMin));
  if (filter.outputPowerDbmMax !== undefined) params.set('outputPowerDbmMax', String(filter.outputPowerDbmMax));
  if (filter.noiseFigureDbMin !== undefined) params.set('noiseFigureDbMin', String(filter.noiseFigureDbMin));
  if (filter.noiseFigureDbMax !== undefined) params.set('noiseFigureDbMax', String(filter.noiseFigureDbMax));
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countAmplifierActiveFilters(filter: AmplifierFilter): number {
  return Array.from(serializeAmplifierFilter(filter).keys()).length;
}
