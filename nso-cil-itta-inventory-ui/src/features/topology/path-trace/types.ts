import type { LifecycleStatus, SortState } from '@/shared/types';

export const PATH_TRACE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type PathTraceLifecycleStage = (typeof PATH_TRACE_LIFECYCLE_STAGE_VALUES)[number];
export const PATH_TRACE_LIFECYCLE_STAGE_LABELS: Record<PathTraceLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const PATH_TRACE_LIFECYCLE_STAGE_OPTIONS = PATH_TRACE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: PATH_TRACE_LIFECYCLE_STAGE_LABELS[value] }));

export const PATH_TRACE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PathTraceCriticality = (typeof PATH_TRACE_CRITICALITY_VALUES)[number];
export const PATH_TRACE_CRITICALITY_LABELS: Record<PathTraceCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const PATH_TRACE_CRITICALITY_OPTIONS = PATH_TRACE_CRITICALITY_VALUES.map((value) => ({ value, label: PATH_TRACE_CRITICALITY_LABELS[value] }));

export interface PathTrace {
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
  sourceRef: string;
  targetRef: string;
  hopCount?: number;
  totalLatencyMs?: number;
  traceMethod?: string;
  tracedAt?: string;
  lifecycleStage?: PathTraceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: PathTraceCriticality;
}

export interface PathTraceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  sourceRef: string;
  targetRef: string;
  hopCount?: number;
  totalLatencyMs?: number;
  traceMethod?: string;
  tracedAt?: string;
  lifecycleStage?: PathTraceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: PathTraceCriticality;
}

export type PathTraceFormValues = Partial<PathTraceInput>;

export interface PathTraceUpdateInput extends Partial<PathTraceInput> {
  expectedVersion?: number;
}

export interface PathTraceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  sourceRef?: string;
  targetRef?: string;
  hopCountMin?: number;
  hopCountMax?: number;
  totalLatencyMsMin?: number;
  totalLatencyMsMax?: number;
  traceMethod?: string;
  tracedAtFrom?: string;
  tracedAtTo?: string;
  lifecycleStage?: PathTraceLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: PathTraceCriticality[];
}

export type PathTraceSort = SortState<keyof PathTrace & string>;

export interface PathTraceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const PATHTRACE_PATH = '/api/v1/path-traces';
export const PATHTRACE_TITLE = 'Path Trace';
export const PATHTRACE_TITLE_PLURAL = 'Path Tracees';

export const PATHTRACE_DEFAULTS: PathTraceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  sourceRef: '',
  targetRef: '',
};
