import type { LifecycleStatus, SortState } from '@/shared/types';

export const K8S_CLUSTER_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type K8sClusterLifecycleStage = (typeof K8S_CLUSTER_LIFECYCLE_STAGE_VALUES)[number];
export const K8S_CLUSTER_LIFECYCLE_STAGE_LABELS: Record<K8sClusterLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const K8S_CLUSTER_LIFECYCLE_STAGE_OPTIONS = K8S_CLUSTER_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: K8S_CLUSTER_LIFECYCLE_STAGE_LABELS[value] }));

export interface K8sCluster {
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
  clusterName: string;
  clusterVersion: string;
  nodeCount: number;
  cloudAccountId?: string;
  region?: string;
  apiEndpoint?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: K8sClusterLifecycleStage;
  sourceSystem?: string;
  owner?: string;
}

export interface K8sClusterInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  clusterName: string;
  clusterVersion: string;
  nodeCount: number;
  cloudAccountId?: string;
  region?: string;
  apiEndpoint?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: K8sClusterLifecycleStage;
  sourceSystem?: string;
  owner?: string;
}

export type K8sClusterFormValues = Partial<K8sClusterInput>;

export interface K8sClusterUpdateInput extends Partial<K8sClusterInput> {
  expectedVersion?: number;
}

export interface K8sClusterFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  clusterName?: string;
  clusterVersion?: string;
  nodeCountMin?: number;
  nodeCountMax?: number;
  cloudAccountId?: string;
  region?: string;
  apiEndpoint?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: K8sClusterLifecycleStage[];
  sourceSystem?: string;
  owner?: string;
}

export type K8sClusterSort = SortState<keyof K8sCluster & string>;

export interface K8sClusterSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const K8SCLUSTER_PATH = '/api/v1/k8s-clusters';
export const K8SCLUSTER_TITLE = 'K8s Cluster';
export const K8SCLUSTER_TITLE_PLURAL = 'K8s Clusters';

export const K8SCLUSTER_DEFAULTS: K8sClusterFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  clusterName: '',
  clusterVersion: '',
};
