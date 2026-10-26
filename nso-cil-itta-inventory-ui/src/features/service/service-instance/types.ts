import type { LifecycleStatus, SortState } from '@/shared/types';

export const SERVICE_INSTANCE_SERVICE_STATE_VALUES = ['PLANNED', 'PROVISIONING', 'ACTIVE', 'SUSPENDED', 'TERMINATED'] as const;
export type ServiceInstanceServiceState = (typeof SERVICE_INSTANCE_SERVICE_STATE_VALUES)[number];
export const SERVICE_INSTANCE_SERVICE_STATE_LABELS: Record<ServiceInstanceServiceState, string> = {
  PLANNED: 'Planned',
  PROVISIONING: 'Provisioning',
  ACTIVE: 'Active',
  SUSPENDED: 'Suspended',
  TERMINATED: 'Terminated',
};
export const SERVICE_INSTANCE_SERVICE_STATE_OPTIONS = SERVICE_INSTANCE_SERVICE_STATE_VALUES.map((value) => ({ value, label: SERVICE_INSTANCE_SERVICE_STATE_LABELS[value] }));

export const SERVICE_INSTANCE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ServiceInstanceLifecycleStage = (typeof SERVICE_INSTANCE_LIFECYCLE_STAGE_VALUES)[number];
export const SERVICE_INSTANCE_LIFECYCLE_STAGE_LABELS: Record<ServiceInstanceLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const SERVICE_INSTANCE_LIFECYCLE_STAGE_OPTIONS = SERVICE_INSTANCE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: SERVICE_INSTANCE_LIFECYCLE_STAGE_LABELS[value] }));

export const SERVICE_INSTANCE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ServiceInstanceCriticality = (typeof SERVICE_INSTANCE_CRITICALITY_VALUES)[number];
export const SERVICE_INSTANCE_CRITICALITY_LABELS: Record<ServiceInstanceCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SERVICE_INSTANCE_CRITICALITY_OPTIONS = SERVICE_INSTANCE_CRITICALITY_VALUES.map((value) => ({ value, label: SERVICE_INSTANCE_CRITICALITY_LABELS[value] }));

export interface ServiceInstance {
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
  catalogItemId: string;
  customerId: string;
  activatedOn?: string;
  bandwidthMbps?: number;
  serviceState?: ServiceInstanceServiceState;
  lifecycleStage?: ServiceInstanceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ServiceInstanceCriticality;
}

export interface ServiceInstanceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  catalogItemId: string;
  customerId: string;
  activatedOn?: string;
  bandwidthMbps?: number;
  serviceState?: ServiceInstanceServiceState;
  lifecycleStage?: ServiceInstanceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ServiceInstanceCriticality;
}

export type ServiceInstanceFormValues = Partial<ServiceInstanceInput>;

export interface ServiceInstanceUpdateInput extends Partial<ServiceInstanceInput> {
  expectedVersion?: number;
}

export interface ServiceInstanceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  catalogItemId?: string;
  customerId?: string;
  activatedOnFrom?: string;
  activatedOnTo?: string;
  bandwidthMbpsMin?: number;
  bandwidthMbpsMax?: number;
  serviceState?: ServiceInstanceServiceState[];
  lifecycleStage?: ServiceInstanceLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: ServiceInstanceCriticality[];
}

export type ServiceInstanceSort = SortState<keyof ServiceInstance & string>;

export interface ServiceInstanceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SERVICEINSTANCE_PATH = '/api/v1/service-instances';
export const SERVICEINSTANCE_TITLE = 'Service Instance';
export const SERVICEINSTANCE_TITLE_PLURAL = 'Service Instancees';

export const SERVICEINSTANCE_DEFAULTS: ServiceInstanceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
