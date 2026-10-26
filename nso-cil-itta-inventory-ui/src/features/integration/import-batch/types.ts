import type { LifecycleStatus, SortState } from '@/shared/types';

export const IMPORT_BATCH_BATCH_STATE_VALUES = ['UPLOADED', 'VALIDATING', 'LOADING', 'DONE', 'FAILED'] as const;
export type ImportBatchBatchState = (typeof IMPORT_BATCH_BATCH_STATE_VALUES)[number];
export const IMPORT_BATCH_BATCH_STATE_LABELS: Record<ImportBatchBatchState, string> = {
  UPLOADED: 'Uploaded',
  VALIDATING: 'Validating',
  LOADING: 'Loading',
  DONE: 'Done',
  FAILED: 'Failed',
};
export const IMPORT_BATCH_BATCH_STATE_OPTIONS = IMPORT_BATCH_BATCH_STATE_VALUES.map((value) => ({ value, label: IMPORT_BATCH_BATCH_STATE_LABELS[value] }));

export const IMPORT_BATCH_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ImportBatchLifecycleStage = (typeof IMPORT_BATCH_LIFECYCLE_STAGE_VALUES)[number];
export const IMPORT_BATCH_LIFECYCLE_STAGE_LABELS: Record<ImportBatchLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const IMPORT_BATCH_LIFECYCLE_STAGE_OPTIONS = IMPORT_BATCH_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: IMPORT_BATCH_LIFECYCLE_STAGE_LABELS[value] }));

export interface ImportBatch {
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
  fileName: string;
  entityType: string;
  totalRows?: number;
  processedRows?: number;
  failedRows?: number;
  batchState?: ImportBatchBatchState;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ImportBatchLifecycleStage;
  sourceSystem?: string;
}

export interface ImportBatchInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  fileName: string;
  entityType: string;
  totalRows?: number;
  processedRows?: number;
  failedRows?: number;
  batchState?: ImportBatchBatchState;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ImportBatchLifecycleStage;
  sourceSystem?: string;
}

export type ImportBatchFormValues = Partial<ImportBatchInput>;

export interface ImportBatchUpdateInput extends Partial<ImportBatchInput> {
  expectedVersion?: number;
}

export interface ImportBatchFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  fileName?: string;
  entityType?: string;
  totalRowsMin?: number;
  totalRowsMax?: number;
  processedRowsMin?: number;
  processedRowsMax?: number;
  failedRowsMin?: number;
  failedRowsMax?: number;
  batchState?: ImportBatchBatchState[];
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: ImportBatchLifecycleStage[];
  sourceSystem?: string;
}

export type ImportBatchSort = SortState<keyof ImportBatch & string>;

export interface ImportBatchSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const IMPORTBATCH_PATH = '/api/v1/import-batchs';
export const IMPORTBATCH_TITLE = 'Import Batch';
export const IMPORTBATCH_TITLE_PLURAL = 'Import Batchs';

export const IMPORTBATCH_DEFAULTS: ImportBatchFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  fileName: '',
  entityType: '',
};
