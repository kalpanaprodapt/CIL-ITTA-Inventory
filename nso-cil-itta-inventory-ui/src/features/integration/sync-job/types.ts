import type { LifecycleStatus, SortState } from '@/shared/types';

export const SYNC_JOB_DIRECTION_VALUES = ['INBOUND', 'OUTBOUND', 'BIDIRECTIONAL'] as const;
export type SyncJobDirection = (typeof SYNC_JOB_DIRECTION_VALUES)[number];
export const SYNC_JOB_DIRECTION_LABELS: Record<SyncJobDirection, string> = {
  INBOUND: 'Inbound',
  OUTBOUND: 'Outbound',
  BIDIRECTIONAL: 'Bidirectional',
};
export const SYNC_JOB_DIRECTION_OPTIONS = SYNC_JOB_DIRECTION_VALUES.map((value) => ({ value, label: SYNC_JOB_DIRECTION_LABELS[value] }));

export const SYNC_JOB_LAST_STATUS_VALUES = ['OK', 'PARTIAL', 'FAILED'] as const;
export type SyncJobLastStatus = (typeof SYNC_JOB_LAST_STATUS_VALUES)[number];
export const SYNC_JOB_LAST_STATUS_LABELS: Record<SyncJobLastStatus, string> = {
  OK: 'Ok',
  PARTIAL: 'Partial',
  FAILED: 'Failed',
};
export const SYNC_JOB_LAST_STATUS_OPTIONS = SYNC_JOB_LAST_STATUS_VALUES.map((value) => ({ value, label: SYNC_JOB_LAST_STATUS_LABELS[value] }));

export const SYNC_JOB_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SyncJobCriticality = (typeof SYNC_JOB_CRITICALITY_VALUES)[number];
export const SYNC_JOB_CRITICALITY_LABELS: Record<SyncJobCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SYNC_JOB_CRITICALITY_OPTIONS = SYNC_JOB_CRITICALITY_VALUES.map((value) => ({ value, label: SYNC_JOB_CRITICALITY_LABELS[value] }));

export interface SyncJob {
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
  endpointId: string;
  direction?: SyncJobDirection;
  scheduleCron?: string;
  lastRunAt?: string;
  lastStatus?: SyncJobLastStatus;
  owner?: string;
  criticality?: SyncJobCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface SyncJobInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  endpointId: string;
  direction?: SyncJobDirection;
  scheduleCron?: string;
  lastRunAt?: string;
  lastStatus?: SyncJobLastStatus;
  owner?: string;
  criticality?: SyncJobCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type SyncJobFormValues = Partial<SyncJobInput>;

export interface SyncJobUpdateInput extends Partial<SyncJobInput> {
  expectedVersion?: number;
}

export interface SyncJobFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  endpointId?: string;
  direction?: SyncJobDirection[];
  scheduleCron?: string;
  lastRunAtFrom?: string;
  lastRunAtTo?: string;
  lastStatus?: SyncJobLastStatus[];
  owner?: string;
  criticality?: SyncJobCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type SyncJobSort = SortState<keyof SyncJob & string>;

export interface SyncJobSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SYNCJOB_PATH = '/api/v1/sync-jobs';
export const SYNCJOB_TITLE = 'Sync Job';
export const SYNCJOB_TITLE_PLURAL = 'Sync Jobs';

export const SYNCJOB_DEFAULTS: SyncJobFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
