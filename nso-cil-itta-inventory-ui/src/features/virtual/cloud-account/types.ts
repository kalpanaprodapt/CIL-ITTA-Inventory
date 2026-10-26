import type { LifecycleStatus, SortState } from '@/shared/types';

export const CLOUD_ACCOUNT_PROVIDER_VALUES = ['AZURE', 'AWS', 'GCP', 'ORACLE'] as const;
export type CloudAccountProvider = (typeof CLOUD_ACCOUNT_PROVIDER_VALUES)[number];
export const CLOUD_ACCOUNT_PROVIDER_LABELS: Record<CloudAccountProvider, string> = {
  AZURE: 'Azure',
  AWS: 'Aws',
  GCP: 'Gcp',
  ORACLE: 'Oracle',
};
export const CLOUD_ACCOUNT_PROVIDER_OPTIONS = CLOUD_ACCOUNT_PROVIDER_VALUES.map((value) => ({ value, label: CLOUD_ACCOUNT_PROVIDER_LABELS[value] }));

export const CLOUD_ACCOUNT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type CloudAccountLifecycleStage = (typeof CLOUD_ACCOUNT_LIFECYCLE_STAGE_VALUES)[number];
export const CLOUD_ACCOUNT_LIFECYCLE_STAGE_LABELS: Record<CloudAccountLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const CLOUD_ACCOUNT_LIFECYCLE_STAGE_OPTIONS = CLOUD_ACCOUNT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: CLOUD_ACCOUNT_LIFECYCLE_STAGE_LABELS[value] }));

export interface CloudAccount {
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
  provider?: CloudAccountProvider;
  accountRef: string;
  subscriptionName?: string;
  monthlyBudget?: number;
  environment?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CloudAccountLifecycleStage;
}

export interface CloudAccountInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  provider?: CloudAccountProvider;
  accountRef: string;
  subscriptionName?: string;
  monthlyBudget?: number;
  environment?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: CloudAccountLifecycleStage;
}

export type CloudAccountFormValues = Partial<CloudAccountInput>;

export interface CloudAccountUpdateInput extends Partial<CloudAccountInput> {
  expectedVersion?: number;
}

export interface CloudAccountFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  provider?: CloudAccountProvider[];
  accountRef?: string;
  subscriptionName?: string;
  monthlyBudgetMin?: number;
  monthlyBudgetMax?: number;
  environment?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: CloudAccountLifecycleStage[];
}

export type CloudAccountSort = SortState<keyof CloudAccount & string>;

export interface CloudAccountSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CLOUDACCOUNT_PATH = '/api/v1/cloud-accounts';
export const CLOUDACCOUNT_TITLE = 'Cloud Account';
export const CLOUDACCOUNT_TITLE_PLURAL = 'Cloud Accounts';

export const CLOUDACCOUNT_DEFAULTS: CloudAccountFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  accountRef: '',
};
