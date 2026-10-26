import type { LifecycleStatus } from '@/shared/types';
import type { CustomerContactFilter, CustomerContactContactRole, CustomerContactLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCustomerContactQuery(filter: CustomerContactFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.customerId) query.customerId = filter.customerId;
  if (filter.fullName) query.fullName = filter.fullName;
  if (filter.email) query.email = filter.email;
  if (filter.phone) query.phone = filter.phone;
  if (filter.contactRole?.length) query.contactRole = filter.contactRole.join(',');
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseCustomerContactFilter(params: URLSearchParams): CustomerContactFilter {
  const filter: CustomerContactFilter = {};
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
  const customerId = params.get('customerId');
  if (customerId) filter.customerId = customerId;
  const fullName = params.get('fullName');
  if (fullName) filter.fullName = fullName;
  const email = params.get('email');
  if (email) filter.email = email;
  const phone = params.get('phone');
  if (phone) filter.phone = phone;
  const contactRole = params.get('contactRole');
  if (contactRole) filter.contactRole = contactRole.split(',') as CustomerContactContactRole[];
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as CustomerContactLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeCustomerContactFilter(filter: CustomerContactFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.customerId) params.set('customerId', filter.customerId);
  if (filter.fullName) params.set('fullName', filter.fullName);
  if (filter.email) params.set('email', filter.email);
  if (filter.phone) params.set('phone', filter.phone);
  if (filter.contactRole?.length) params.set('contactRole', filter.contactRole.join(','));
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countCustomerContactActiveFilters(filter: CustomerContactFilter): number {
  return Array.from(serializeCustomerContactFilter(filter).keys()).length;
}
