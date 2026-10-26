import type { LifecycleStatus, SortState } from '@/shared/types';

export const DISCOVERY_JOB_JOB_STATE_VALUES = ['QUEUED', 'RUNNING', 'SUCCEEDED', 'FAILED', 'CANCELLED'] as const;
export type DiscoveryJobJobState = (typeof DISCOVERY_JOB_JOB_STATE_VALUES)[number];
export const DISCOVERY_JOB_JOB_STATE_LABELS: Record<DiscoveryJobJobState, string> = {
  QUEUED: 'Queued',
  RUNNING: 'Running',
  SUCCEEDED: 'Succeeded',
  FAILED: 'Failed',
  CANCELLED: 'Cancelled',
};
export const DISCOVERY_JOB_JOB_STATE_OPTIONS = DISCOVERY_JOB_JOB_STATE_VALUES.map((value) => ({ value, label: DISCOVERY_JOB_JOB_STATE_LABELS[value] }));

export const DISCOVERY_JOB_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type DiscoveryJobCriticality = (typeof DISCOVERY_JOB_CRITICALITY_VALUES)[number];
export const DISCOVERY_JOB_CRITICALITY_LABELS: Record<DiscoveryJobCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const DISCOVERY_JOB_CRITICALITY_OPTIONS = DISCOVERY_JOB_CRITICALITY_VALUES.map((value) => ({ value, label: DISCOVERY_JOB_CRITICALITY_LABELS[value] }));

export interface DiscoveryJob {
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
  profileId: string;
  startedAt?: string;
  finishedAt?: string;
  jobState?: DiscoveryJobJobState;
  assetsFound?: number;
  owner?: string;
  criticality?: DiscoveryJobCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface DiscoveryJobInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  profileId: string;
  startedAt?: string;
  finishedAt?: string;
  jobState?: DiscoveryJobJobState;
  assetsFound?: number;
  owner?: string;
  criticality?: DiscoveryJobCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type DiscoveryJobFormValues = Partial<DiscoveryJobInput>;

export interface DiscoveryJobUpdateInput extends Partial<DiscoveryJobInput> {
  expectedVersion?: number;
}

export interface DiscoveryJobFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  profileId?: string;
  startedAtFrom?: string;
  startedAtTo?: string;
  finishedAtFrom?: string;
  finishedAtTo?: string;
  jobState?: DiscoveryJobJobState[];
  assetsFoundMin?: number;
  assetsFoundMax?: number;
  owner?: string;
  criticality?: DiscoveryJobCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type DiscoveryJobSort = SortState<keyof DiscoveryJob & string>;

export interface DiscoveryJobSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const DISCOVERYJOB_PATH = '/api/v1/discovery-jobs';
export const DISCOVERYJOB_TITLE = 'Discovery Job';
export const DISCOVERYJOB_TITLE_PLURAL = 'Discovery Jobs';

export const DISCOVERYJOB_DEFAULTS: DiscoveryJobFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
