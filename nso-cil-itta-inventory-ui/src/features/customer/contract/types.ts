import type { LifecycleStatus, SortState } from '@/shared/types';

export const CONTRACT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ContractCriticality = (typeof CONTRACT_CRITICALITY_VALUES)[number];
export const CONTRACT_CRITICALITY_LABELS: Record<ContractCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const CONTRACT_CRITICALITY_OPTIONS = CONTRACT_CRITICALITY_VALUES.map((value) => ({ value, label: CONTRACT_CRITICALITY_LABELS[value] }));

export interface Contract {
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
  contractNumber: string;
  startDate: string;
  endDate?: string;
  termMonths?: number;
  autoRenew?: boolean;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ContractCriticality;
  costCenter?: string;
}

export interface ContractInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  customerId: string;
  contractNumber: string;
  startDate: string;
  endDate?: string;
  termMonths?: number;
  autoRenew?: boolean;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ContractCriticality;
  costCenter?: string;
}

export type ContractFormValues = Partial<ContractInput>;

export interface ContractUpdateInput extends Partial<ContractInput> {
  expectedVersion?: number;
}

export interface ContractFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  customerId?: string;
  contractNumber?: string;
  startDateFrom?: string;
  startDateTo?: string;
  endDateFrom?: string;
  endDateTo?: string;
  termMonthsMin?: number;
  termMonthsMax?: number;
  autoRenew?: boolean;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ContractCriticality[];
  costCenter?: string;
}

export type ContractSort = SortState<keyof Contract & string>;

export interface ContractSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CONTRACT_PATH = '/api/v1/contracts';
export const CONTRACT_TITLE = 'Contract';
export const CONTRACT_TITLE_PLURAL = 'Contracts';

export const CONTRACT_DEFAULTS: ContractFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  contractNumber: '',
  autoRenew: false,
};
