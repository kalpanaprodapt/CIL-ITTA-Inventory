import type { LifecycleStatus, SortState } from '@/shared/types';

export const CUSTOMER_CONTACT_CONTACT_ROLE_VALUES = ['TECHNICAL', 'BILLING', 'ESCALATION', 'EXECUTIVE'] as const;
export type CustomerContactContactRole = (typeof CUSTOMER_CONTACT_CONTACT_ROLE_VALUES)[number];
export const CUSTOMER_CONTACT_CONTACT_ROLE_LABELS: Record<CustomerContactContactRole, string> = {
  TECHNICAL: 'Technical',
  BILLING: 'Billing',
  ESCALATION: 'Escalation',
  EXECUTIVE: 'Executive',
};
export const CUSTOMER_CONTACT_CONTACT_ROLE_OPTIONS = CUSTOMER_CONTACT_CONTACT_ROLE_VALUES.map((value) => ({ value, label: CUSTOMER_CONTACT_CONTACT_ROLE_LABELS[value] }));

export const CUSTOMER_CONTACT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type CustomerContactLifecycleStage = (typeof CUSTOMER_CONTACT_LIFECYCLE_STAGE_VALUES)[number];
export const CUSTOMER_CONTACT_LIFECYCLE_STAGE_LABELS: Record<CustomerContactLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CUSTOMER_CONTACT_LIFECYCLE_STAGE_OPTIONS = CUSTOMER_CONTACT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CUSTOMER_CONTACT_LIFECYCLE_STAGE_LABELS[value] }));

export interface CustomerContact {
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
  customerId: string;
  fullName: string;
  email?: string;
  phone?: string;
  contactRole?: CustomerContactContactRole;
  labels?: string;
  lifecycleStage?: CustomerContactLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface CustomerContactInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  customerId: string;
  fullName: string;
  email?: string;
  phone?: string;
  contactRole?: CustomerContactContactRole;
  labels?: string;
  lifecycleStage?: CustomerContactLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type CustomerContactFormValues = Partial<CustomerContactInput>;

export interface CustomerContactUpdateInput extends Partial<CustomerContactInput> {
  expectedVersion?: number;
}

export interface CustomerContactFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  customerId?: string;
  fullName?: string;
  email?: string;
  phone?: string;
  contactRole?: CustomerContactContactRole[];
  labels?: string;
  lifecycleStage?: CustomerContactLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type CustomerContactSort = SortState<keyof CustomerContact & string>;

export interface CustomerContactSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CUSTOMERCONTACT_PATH = '/api/v1/customer-contacts';
export const CUSTOMERCONTACT_TITLE = 'Customer Contact';
export const CUSTOMERCONTACT_TITLE_PLURAL = 'Customer Contacts';

export const CUSTOMERCONTACT_DEFAULTS: CustomerContactFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  fullName: '',
};
