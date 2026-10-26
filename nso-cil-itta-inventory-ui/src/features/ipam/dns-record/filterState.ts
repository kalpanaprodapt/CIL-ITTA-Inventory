import type { LifecycleStatus } from '@/shared/types';
import type { DnsRecordFilter, DnsRecordRecordType, DnsRecordCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toDnsRecordQuery(filter: DnsRecordFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.zoneName) query.zoneName = filter.zoneName;
  if (filter.recordType?.length) query.recordType = filter.recordType.join(',');
  if (filter.recordValue) query.recordValue = filter.recordValue;
  if (filter.ttlMin !== undefined) query.ttlMin = filter.ttlMin;
  if (filter.ttlMax !== undefined) query.ttlMax = filter.ttlMax;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseDnsRecordFilter(params: URLSearchParams): DnsRecordFilter {
  const filter: DnsRecordFilter = {};
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
  const zoneName = params.get('zoneName');
  if (zoneName) filter.zoneName = zoneName;
  const recordType = params.get('recordType');
  if (recordType) filter.recordType = recordType.split(',') as DnsRecordRecordType[];
  const recordValue = params.get('recordValue');
  if (recordValue) filter.recordValue = recordValue;
  const ttlMin = params.get('ttlMin');
  if (ttlMin !== null && ttlMin !== '') filter.ttlMin = Number(ttlMin);
  const ttlMax = params.get('ttlMax');
  if (ttlMax !== null && ttlMax !== '') filter.ttlMax = Number(ttlMax);
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as DnsRecordCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeDnsRecordFilter(filter: DnsRecordFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.zoneName) params.set('zoneName', filter.zoneName);
  if (filter.recordType?.length) params.set('recordType', filter.recordType.join(','));
  if (filter.recordValue) params.set('recordValue', filter.recordValue);
  if (filter.ttlMin !== undefined) params.set('ttlMin', String(filter.ttlMin));
  if (filter.ttlMax !== undefined) params.set('ttlMax', String(filter.ttlMax));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countDnsRecordActiveFilters(filter: DnsRecordFilter): number {
  return Array.from(serializeDnsRecordFilter(filter).keys()).length;
}
