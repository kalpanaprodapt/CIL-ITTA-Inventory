import type { LifecycleStatus, SortState } from '@/shared/types';

export const THRESHOLD_POLICY_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ThresholdPolicyCriticality = (typeof THRESHOLD_POLICY_CRITICALITY_VALUES)[number];
export const THRESHOLD_POLICY_CRITICALITY_LABELS: Record<ThresholdPolicyCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const THRESHOLD_POLICY_CRITICALITY_OPTIONS = THRESHOLD_POLICY_CRITICALITY_VALUES.map((value) => ({ value, label: THRESHOLD_POLICY_CRITICALITY_LABELS[value] }));

export interface ThresholdPolicy {
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
  policyName: string;
  metric: string;
  warnPct: number;
  critPct: number;
  evaluationWindowMinutes?: number;
  region?: string;
  owner?: string;
  criticality?: ThresholdPolicyCriticality;
  costCenter?: string;
  notes?: string;
}

export interface ThresholdPolicyInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  policyName: string;
  metric: string;
  warnPct: number;
  critPct: number;
  evaluationWindowMinutes?: number;
  region?: string;
  owner?: string;
  criticality?: ThresholdPolicyCriticality;
  costCenter?: string;
  notes?: string;
}

export type ThresholdPolicyFormValues = Partial<ThresholdPolicyInput>;

export interface ThresholdPolicyUpdateInput extends Partial<ThresholdPolicyInput> {
  expectedVersion?: number;
}

export interface ThresholdPolicyFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  policyName?: string;
  metric?: string;
  warnPctMin?: number;
  warnPctMax?: number;
  critPctMin?: number;
  critPctMax?: number;
  evaluationWindowMinutesMin?: number;
  evaluationWindowMinutesMax?: number;
  region?: string;
  owner?: string;
  criticality?: ThresholdPolicyCriticality[];
  costCenter?: string;
}

export type ThresholdPolicySort = SortState<keyof ThresholdPolicy & string>;

export interface ThresholdPolicySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const THRESHOLDPOLICY_PATH = '/api/v1/threshold-policies';
export const THRESHOLDPOLICY_TITLE = 'Threshold Policy';
export const THRESHOLDPOLICY_TITLE_PLURAL = 'Threshold Policyes';

export const THRESHOLDPOLICY_DEFAULTS: ThresholdPolicyFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  policyName: '',
  metric: '',
};
