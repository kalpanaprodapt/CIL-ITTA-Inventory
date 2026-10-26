import type { LifecycleStatus, SortState } from '@/shared/types';

export const TOPOLOGY_NODE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type TopologyNodeCriticality = (typeof TOPOLOGY_NODE_CRITICALITY_VALUES)[number];
export const TOPOLOGY_NODE_CRITICALITY_LABELS: Record<TopologyNodeCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const TOPOLOGY_NODE_CRITICALITY_OPTIONS = TOPOLOGY_NODE_CRITICALITY_VALUES.map((value) => ({ value, label: TOPOLOGY_NODE_CRITICALITY_LABELS[value] }));

export interface TopologyNode {
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
  entityRef: string;
  nodeType?: string;
  xPos?: number;
  yPos?: number;
  layer?: number;
  criticality?: TopologyNodeCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface TopologyNodeInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  viewId: string;
  entityRef: string;
  nodeType?: string;
  xPos?: number;
  yPos?: number;
  layer?: number;
  criticality?: TopologyNodeCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type TopologyNodeFormValues = Partial<TopologyNodeInput>;

export interface TopologyNodeUpdateInput extends Partial<TopologyNodeInput> {
  expectedVersion?: number;
}

export interface TopologyNodeFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  viewId?: string;
  entityRef?: string;
  nodeType?: string;
  xPosMin?: number;
  xPosMax?: number;
  yPosMin?: number;
  yPosMax?: number;
  layerMin?: number;
  layerMax?: number;
  criticality?: TopologyNodeCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type TopologyNodeSort = SortState<keyof TopologyNode & string>;

export interface TopologyNodeSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const TOPOLOGYNODE_PATH = '/api/v1/topology-nodes';
export const TOPOLOGYNODE_TITLE = 'Topology Node';
export const TOPOLOGYNODE_TITLE_PLURAL = 'Topology Nodees';

export const TOPOLOGYNODE_DEFAULTS: TopologyNodeFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  entityRef: '',
};
