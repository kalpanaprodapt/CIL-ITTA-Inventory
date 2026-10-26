import type { LifecycleStatus, SortState } from '@/shared/types';

export const SPLICE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type SpliceLifecycleStage = (typeof SPLICE_LIFECYCLE_STAGE_VALUES)[number];
export const SPLICE_LIFECYCLE_STAGE_LABELS: Record<SpliceLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const SPLICE_LIFECYCLE_STAGE_OPTIONS = SPLICE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: SPLICE_LIFECYCLE_STAGE_LABELS[value] }));

export const SPLICE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SpliceCriticality = (typeof SPLICE_CRITICALITY_VALUES)[number];
export const SPLICE_CRITICALITY_LABELS: Record<SpliceCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SPLICE_CRITICALITY_OPTIONS = SPLICE_CRITICALITY_VALUES.map((value) => ({ value, label: SPLICE_CRITICALITY_LABELS[value] }));

export interface Splice {
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
  enclosureId: string;
  aStrandId?: string;
  zStrandId?: string;
  lossDb?: number;
  splicedOn?: string;
  lifecycleStage?: SpliceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SpliceCriticality;
}

export interface SpliceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  enclosureId: string;
  aStrandId?: string;
  zStrandId?: string;
  lossDb?: number;
  splicedOn?: string;
  lifecycleStage?: SpliceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SpliceCriticality;
}

export type SpliceFormValues = Partial<SpliceInput>;

export interface SpliceUpdateInput extends Partial<SpliceInput> {
  expectedVersion?: number;
}

export interface SpliceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  enclosureId?: string;
  aStrandId?: string;
  zStrandId?: string;
  lossDbMin?: number;
  lossDbMax?: number;
  splicedOnFrom?: string;
  splicedOnTo?: string;
  lifecycleStage?: SpliceLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SpliceCriticality[];
}

export type SpliceSort = SortState<keyof Splice & string>;

export interface SpliceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SPLICE_PATH = '/api/v1/splices';
export const SPLICE_TITLE = 'Splice';
export const SPLICE_TITLE_PLURAL = 'Splicees';

export const SPLICE_DEFAULTS: SpliceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  enclosureId: '',
};
