import type { LifecycleStatus, SortState } from '@/shared/types';

export const CUSTOMER_ACCOUNT_BILLING_CYCLE_VALUES = ['MONTHLY', 'QUARTERLY', 'ANNUAL'] as const;
export type CustomerAccountBillingCycle = (typeof CUSTOMER_ACCOUNT_BILLING_CYCLE_VALUES)[number];
export const CUSTOMER_ACCOUNT_BILLING_CYCLE_LABELS: Record<CustomerAccountBillingCycle, string> = {
  MONTHLY: 'Monthly',
  QUARTERLY: 'Quarterly',
  ANNUAL: 'Annual',
};
export const CUSTOMER_ACCOUNT_BILLING_CYCLE_OPTIONS = CUSTOMER_ACCOUNT_BILLING_CYCLE_VALUES.map((value) => ({ value, label: CUSTOMER_ACCOUNT_BILLING_CYCLE_LABELS[value] }));

export const CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type CustomerAccountLifecycleStage = (typeof CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_VALUES)[number];
export const CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_LABELS: Record<CustomerAccountLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_OPTIONS = CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_LABELS[value] }));

export interface CustomerAccount {
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
  accountNumber: string;
  billingCycle?: CustomerAccountBillingCycle;
  currency?: string;
  creditLimit?: number;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CustomerAccountLifecycleStage;
  sourceSystem?: string;
}

export interface CustomerAccountInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  customerId: string;
  accountNumber: string;
  billingCycle?: CustomerAccountBillingCycle;
  currency?: string;
  creditLimit?: number;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CustomerAccountLifecycleStage;
  sourceSystem?: string;
}

export type CustomerAccountFormValues = Partial<CustomerAccountInput>;

export interface CustomerAccountUpdateInput extends Partial<CustomerAccountInput> {
  expectedVersion?: number;
}

export interface CustomerAccountFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  customerId?: string;
  accountNumber?: string;
  billingCycle?: CustomerAccountBillingCycle[];
  currency?: string;
  creditLimitMin?: number;
  creditLimitMax?: number;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: CustomerAccountLifecycleStage[];
  sourceSystem?: string;
}

export type CustomerAccountSort = SortState<keyof CustomerAccount & string>;

export interface CustomerAccountSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CUSTOMERACCOUNT_PATH = '/api/v1/customer-accounts';
export const CUSTOMERACCOUNT_TITLE = 'Customer Account';
export const CUSTOMERACCOUNT_TITLE_PLURAL = 'Customer Accounts';

export const CUSTOMERACCOUNT_DEFAULTS: CustomerAccountFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  accountNumber: '',
};
