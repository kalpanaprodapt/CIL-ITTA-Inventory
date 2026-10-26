import type { LifecycleStatus } from '@/shared/types';
import type { CountryFilter, CountryCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCountryQuery(filter: CountryFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.isoCode) query.isoCode = filter.isoCode;
  if (filter.isoNumericMin !== undefined) query.isoNumericMin = filter.isoNumericMin;
  if (filter.isoNumericMax !== undefined) query.isoNumericMax = filter.isoNumericMax;
  if (filter.dialCode) query.dialCode = filter.dialCode;
  if (filter.currency) query.currency = filter.currency;
  if (filter.regulatoryBody) query.regulatoryBody = filter.regulatoryBody;
  if (filter.timezone) query.timezone = filter.timezone;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseCountryFilter(params: URLSearchParams): CountryFilter {
  const filter: CountryFilter = {};
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
  const isoCode = params.get('isoCode');
  if (isoCode) filter.isoCode = isoCode;
  const isoNumericMin = params.get('isoNumericMin');
  if (isoNumericMin !== null && isoNumericMin !== '') filter.isoNumericMin = Number(isoNumericMin);
  const isoNumericMax = params.get('isoNumericMax');
  if (isoNumericMax !== null && isoNumericMax !== '') filter.isoNumericMax = Number(isoNumericMax);
  const dialCode = params.get('dialCode');
  if (dialCode) filter.dialCode = dialCode;
  const currency = params.get('currency');
  if (currency) filter.currency = currency;
  const regulatoryBody = params.get('regulatoryBody');
  if (regulatoryBody) filter.regulatoryBody = regulatoryBody;
  const timezone = params.get('timezone');
  if (timezone) filter.timezone = timezone;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as CountryCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeCountryFilter(filter: CountryFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.isoCode) params.set('isoCode', filter.isoCode);
  if (filter.isoNumericMin !== undefined) params.set('isoNumericMin', String(filter.isoNumericMin));
  if (filter.isoNumericMax !== undefined) params.set('isoNumericMax', String(filter.isoNumericMax));
  if (filter.dialCode) params.set('dialCode', filter.dialCode);
  if (filter.currency) params.set('currency', filter.currency);
  if (filter.regulatoryBody) params.set('regulatoryBody', filter.regulatoryBody);
  if (filter.timezone) params.set('timezone', filter.timezone);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countCountryActiveFilters(filter: CountryFilter): number {
  return Array.from(serializeCountryFilter(filter).keys()).length;
}
