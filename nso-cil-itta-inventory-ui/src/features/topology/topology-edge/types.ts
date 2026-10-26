import type { LifecycleStatus, SortState } from '@/shared/types';

export const TOPOLOGY_EDGE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type TopologyEdgeLifecycleStage = (typeof TOPOLOGY_EDGE_LIFECYCLE_STAGE_VALUES)[number];
export const TOPOLOGY_EDGE_LIFECYCLE_STAGE_LABELS: Record<TopologyEdgeLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const TOPOLOGY_EDGE_LIFECYCLE_STAGE_OPTIONS = TOPOLOGY_EDGE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: TOPOLOGY_EDGE_LIFECYCLE_STAGE_LABELS[value] }));

export interface TopologyEdge {
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
  viewId: string;
  sourceNodeId: string;
  targetNodeId: string;
  weight?: number;
  directed?: boolean;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: TopologyEdgeLifecycleStage;
}

export interface TopologyEdgeInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  viewId: string;
  sourceNodeId: string;
  targetNodeId: string;
  weight?: number;
  directed?: boolean;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: TopologyEdgeLifecycleStage;
}

export type TopologyEdgeFormValues = Partial<TopologyEdgeInput>;

export interface TopologyEdgeUpdateInput extends Partial<TopologyEdgeInput> {
  expectedVersion?: number;
}

export interface TopologyEdgeFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  viewId?: string;
  sourceNodeId?: string;
  targetNodeId?: string;
  weightMin?: number;
  weightMax?: number;
  directed?: boolean;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: TopologyEdgeLifecycleStage[];
}

export type TopologyEdgeSort = SortState<keyof TopologyEdge & string>;

export interface TopologyEdgeSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const TOPOLOGYEDGE_PATH = '/api/v1/topology-edges';
export const TOPOLOGYEDGE_TITLE = 'Topology Edge';
export const TOPOLOGYEDGE_TITLE_PLURAL = 'Topology Edgees';

export const TOPOLOGYEDGE_DEFAULTS: TopologyEdgeFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  directed: false,
};
