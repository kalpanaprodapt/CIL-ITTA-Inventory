import type { LifecycleStatus, SortState } from '@/shared/types';

export const QOS_POLICY_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type QosPolicyLifecycleStage = (typeof QOS_POLICY_LIFECYCLE_STAGE_VALUES)[number];
export const QOS_POLICY_LIFECYCLE_STAGE_LABELS: Record<QosPolicyLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const QOS_POLICY_LIFECYCLE_STAGE_OPTIONS = QOS_POLICY_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: QOS_POLICY_LIFECYCLE_STAGE_LABELS[value] }));

export interface QosPolicy {
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
  classCount: number;
  cirMbps?: number;
  pirMbps?: number;
  markingScheme?: string;
  labels?: string;
  lifecycleStage?: QosPolicyLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface QosPolicyInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  policyName: string;
  classCount: number;
  cirMbps?: number;
  pirMbps?: number;
  markingScheme?: string;
  labels?: string;
  lifecycleStage?: QosPolicyLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type QosPolicyFormValues = Partial<QosPolicyInput>;

export interface QosPolicyUpdateInput extends Partial<QosPolicyInput> {
  expectedVersion?: number;
}

export interface QosPolicyFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  policyName?: string;
  classCountMin?: number;
  classCountMax?: number;
  cirMbpsMin?: number;
  cirMbpsMax?: number;
  pirMbpsMin?: number;
  pirMbpsMax?: number;
  markingScheme?: string;
  labels?: string;
  lifecycleStage?: QosPolicyLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type QosPolicySort = SortState<keyof QosPolicy & string>;

export interface QosPolicySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const QOSPOLICY_PATH = '/api/v1/qos-policies';
export const QOSPOLICY_TITLE = 'Qos Policy';
export const QOSPOLICY_TITLE_PLURAL = 'Qos Policyes';

export const QOSPOLICY_DEFAULTS: QosPolicyFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  policyName: '',
};
