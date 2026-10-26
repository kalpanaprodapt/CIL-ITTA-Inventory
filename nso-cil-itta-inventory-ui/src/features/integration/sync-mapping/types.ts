import type { LifecycleStatus, SortState } from '@/shared/types';

export interface SyncMapping {
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
  jobId: string;
  sourceField: string;
  targetField: string;
  transform?: string;
  required?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface SyncMappingInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  jobId: string;
  sourceField: string;
  targetField: string;
  transform?: string;
  required?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type SyncMappingFormValues = Partial<SyncMappingInput>;

export interface SyncMappingUpdateInput extends Partial<SyncMappingInput> {
  expectedVersion?: number;
}

export interface SyncMappingFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  jobId?: string;
  sourceField?: string;
  targetField?: string;
  transform?: string;
  required?: boolean;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type SyncMappingSort = SortState<keyof SyncMapping & string>;

export interface SyncMappingSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SYNCMAPPING_PATH = '/api/v1/sync-mappings';
export const SYNCMAPPING_TITLE = 'Sync Mapping';
export const SYNCMAPPING_TITLE_PLURAL = 'Sync Mappings';

export const SYNCMAPPING_DEFAULTS: SyncMappingFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  sourceField: '',
  targetField: '',
  required: false,
};
