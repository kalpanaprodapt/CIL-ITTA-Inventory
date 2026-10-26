import type { LifecycleStatus, SortState } from '@/shared/types';

export const SUPPORT_CONTRACT_COVERAGE_LEVEL_VALUES = ['BRONZE', 'SILVER', 'GOLD', 'PLATINUM'] as const;
export type SupportContractCoverageLevel = (typeof SUPPORT_CONTRACT_COVERAGE_LEVEL_VALUES)[number];
export const SUPPORT_CONTRACT_COVERAGE_LEVEL_LABELS: Record<SupportContractCoverageLevel, string> = {
  BRONZE: 'Bronze',
  SILVER: 'Silver',
  GOLD: 'Gold',
  PLATINUM: 'Platinum',
};
export const SUPPORT_CONTRACT_COVERAGE_LEVEL_OPTIONS = SUPPORT_CONTRACT_COVERAGE_LEVEL_VALUES.map((value) => ({ value, label: SUPPORT_CONTRACT_COVERAGE_LEVEL_LABELS[value] }));

export const SUPPORT_CONTRACT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SupportContractCriticality = (typeof SUPPORT_CONTRACT_CRITICALITY_VALUES)[number];
export const SUPPORT_CONTRACT_CRITICALITY_LABELS: Record<SupportContractCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SUPPORT_CONTRACT_CRITICALITY_OPTIONS = SUPPORT_CONTRACT_CRITICALITY_VALUES.map((value) => ({ value, label: SUPPORT_CONTRACT_CRITICALITY_LABELS[value] }));

export interface SupportContract {
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
  vendorId: string;
  contractNumber: string;
  startDate: string;
  endDate: string;
  coverageLevel?: SupportContractCoverageLevel;
  annualCost?: number;
  owner?: string;
  criticality?: SupportContractCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface SupportContractInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vendorId: string;
  contractNumber: string;
  startDate: string;
  endDate: string;
  coverageLevel?: SupportContractCoverageLevel;
  annualCost?: number;
  owner?: string;
  criticality?: SupportContractCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type SupportContractFormValues = Partial<SupportContractInput>;

export interface SupportContractUpdateInput extends Partial<SupportContractInput> {
  expectedVersion?: number;
}

export interface SupportContractFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vendorId?: string;
  contractNumber?: string;
  startDateFrom?: string;
  startDateTo?: string;
  endDateFrom?: string;
  endDateTo?: string;
  coverageLevel?: SupportContractCoverageLevel[];
  annualCostMin?: number;
  annualCostMax?: number;
  owner?: string;
  criticality?: SupportContractCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type SupportContractSort = SortState<keyof SupportContract & string>;

export interface SupportContractSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SUPPORTCONTRACT_PATH = '/api/v1/support-contracts';
export const SUPPORTCONTRACT_TITLE = 'Support Contract';
export const SUPPORTCONTRACT_TITLE_PLURAL = 'Support Contracts';

export const SUPPORTCONTRACT_DEFAULTS: SupportContractFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  contractNumber: '',
};
