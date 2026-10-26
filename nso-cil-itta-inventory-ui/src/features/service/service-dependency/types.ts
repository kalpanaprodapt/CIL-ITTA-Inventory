import type { LifecycleStatus, SortState } from '@/shared/types';

export const SERVICE_DEPENDENCY_DEPENDENCY_TYPE_VALUES = ['HARD', 'SOFT', 'OPTIONAL'] as const;
export type ServiceDependencyDependencyType = (typeof SERVICE_DEPENDENCY_DEPENDENCY_TYPE_VALUES)[number];
export const SERVICE_DEPENDENCY_DEPENDENCY_TYPE_LABELS: Record<ServiceDependencyDependencyType, string> = {
  HARD: 'Hard',
  SOFT: 'Soft',
  OPTIONAL: 'Optional',
};
export const SERVICE_DEPENDENCY_DEPENDENCY_TYPE_OPTIONS = SERVICE_DEPENDENCY_DEPENDENCY_TYPE_VALUES.map((value) => ({ value, label: SERVICE_DEPENDENCY_DEPENDENCY_TYPE_LABELS[value] }));

export const SERVICE_DEPENDENCY_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ServiceDependencyCriticality = (typeof SERVICE_DEPENDENCY_CRITICALITY_VALUES)[number];
export const SERVICE_DEPENDENCY_CRITICALITY_LABELS: Record<ServiceDependencyCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SERVICE_DEPENDENCY_CRITICALITY_OPTIONS = SERVICE_DEPENDENCY_CRITICALITY_VALUES.map((value) => ({ value, label: SERVICE_DEPENDENCY_CRITICALITY_LABELS[value] }));

export interface ServiceDependency {
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
  serviceId: string;
  dependsOnId: string;
  dependencyType?: ServiceDependencyDependencyType;
  impactScore?: number;
  criticality?: ServiceDependencyCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface ServiceDependencyInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  serviceId: string;
  dependsOnId: string;
  dependencyType?: ServiceDependencyDependencyType;
  impactScore?: number;
  criticality?: ServiceDependencyCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type ServiceDependencyFormValues = Partial<ServiceDependencyInput>;

export interface ServiceDependencyUpdateInput extends Partial<ServiceDependencyInput> {
  expectedVersion?: number;
}

export interface ServiceDependencyFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  serviceId?: string;
  dependsOnId?: string;
  dependencyType?: ServiceDependencyDependencyType[];
  impactScoreMin?: number;
  impactScoreMax?: number;
  criticality?: ServiceDependencyCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type ServiceDependencySort = SortState<keyof ServiceDependency & string>;

export interface ServiceDependencySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SERVICEDEPENDENCY_PATH = '/api/v1/service-dependencies';
export const SERVICEDEPENDENCY_TITLE = 'Service Dependency';
export const SERVICEDEPENDENCY_TITLE_PLURAL = 'Service Dependencyes';

export const SERVICEDEPENDENCY_DEFAULTS: ServiceDependencyFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
