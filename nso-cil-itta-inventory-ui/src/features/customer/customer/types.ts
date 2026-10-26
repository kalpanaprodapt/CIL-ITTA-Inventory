import type { LifecycleStatus, SortState } from '@/shared/types';

export const CUSTOMER_SEGMENT_VALUES = ['ENTERPRISE', 'WHOLESALE', 'GOVERNMENT', 'CARRIER', 'SMB'] as const;
export type CustomerSegment = (typeof CUSTOMER_SEGMENT_VALUES)[number];
export const CUSTOMER_SEGMENT_LABELS: Record<CustomerSegment, string> = {
  ENTERPRISE: 'Enterprise',
  WHOLESALE: 'Wholesale',
  GOVERNMENT: 'Government',
  CARRIER: 'Carrier',
  SMB: 'Smb',
};
export const CUSTOMER_SEGMENT_OPTIONS = CUSTOMER_SEGMENT_VALUES.map((value) => ({ value, label: CUSTOMER_SEGMENT_LABELS[value] }));

export interface Customer {
  id: string;
  tenantId: string;
  version: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  legalName: string;
  segment?: CustomerSegment;
  taxId?: string;
  industry?: string;
  accountManager?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface CustomerInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  legalName: string;
  segment?: CustomerSegment;
  taxId?: string;
  industry?: string;
  accountManager?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type CustomerFormValues = Partial<CustomerInput>;

export interface CustomerUpdateInput extends Partial<CustomerInput> {
  expectedVersion?: number;
}

export interface CustomerFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  legalName?: string;
  segment?: CustomerSegment[];
  taxId?: string;
  industry?: string;
  accountManager?: string;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type CustomerSort = SortState<keyof Customer & string>;

export interface CustomerSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CUSTOMER_PATH = '/api/v1/customers';
export const CUSTOMER_TITLE = 'Customer';
export const CUSTOMER_TITLE_PLURAL = 'Customers';

export const CUSTOMER_DEFAULTS: CustomerFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  legalName: '',
};
