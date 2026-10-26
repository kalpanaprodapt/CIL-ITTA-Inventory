import type { LifecycleStatus, SortState } from '@/shared/types';

export const TOPOLOGY_VIEW_VIEW_TYPE_VALUES = ['PHYSICAL', 'L2', 'L3', 'SERVICE', 'OPTICAL'] as const;
export type TopologyViewViewType = (typeof TOPOLOGY_VIEW_VIEW_TYPE_VALUES)[number];
export const TOPOLOGY_VIEW_VIEW_TYPE_LABELS: Record<TopologyViewViewType, string> = {
  PHYSICAL: 'Physical',
  L2: 'L2',
  L3: 'L3',
  SERVICE: 'Service',
  OPTICAL: 'Optical',
};
export const TOPOLOGY_VIEW_VIEW_TYPE_OPTIONS = TOPOLOGY_VIEW_VIEW_TYPE_VALUES.map((value) => ({ value, label: TOPOLOGY_VIEW_VIEW_TYPE_LABELS[value] }));

export const TOPOLOGY_VIEW_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type TopologyViewLifecycleStage = (typeof TOPOLOGY_VIEW_LIFECYCLE_STAGE_VALUES)[number];
export const TOPOLOGY_VIEW_LIFECYCLE_STAGE_LABELS: Record<TopologyViewLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const TOPOLOGY_VIEW_LIFECYCLE_STAGE_OPTIONS = TOPOLOGY_VIEW_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: TOPOLOGY_VIEW_LIFECYCLE_STAGE_LABELS[value] }));

export interface TopologyView {
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
  viewName: string;
  viewType?: TopologyViewViewType;
  scopeFilter?: string;
  refreshedAt?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: TopologyViewLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface TopologyViewInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  viewName: string;
  viewType?: TopologyViewViewType;
  scopeFilter?: string;
  refreshedAt?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: TopologyViewLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type TopologyViewFormValues = Partial<TopologyViewInput>;

export interface TopologyViewUpdateInput extends Partial<TopologyViewInput> {
  expectedVersion?: number;
}

export interface TopologyViewFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  viewName?: string;
  viewType?: TopologyViewViewType[];
  scopeFilter?: string;
  refreshedAtFrom?: string;
  refreshedAtTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: TopologyViewLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type TopologyViewSort = SortState<keyof TopologyView & string>;

export interface TopologyViewSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const TOPOLOGYVIEW_PATH = '/api/v1/topology-views';
export const TOPOLOGYVIEW_TITLE = 'Topology View';
export const TOPOLOGYVIEW_TITLE_PLURAL = 'Topology Views';

export const TOPOLOGYVIEW_DEFAULTS: TopologyViewFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  viewName: '',
};
