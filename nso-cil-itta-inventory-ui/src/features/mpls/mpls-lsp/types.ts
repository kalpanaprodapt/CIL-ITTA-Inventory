import type { LifecycleStatus, SortState } from '@/shared/types';

export const MPLS_LSP_PATH_TYPE_VALUES = ['PRIMARY', 'SECONDARY', 'STANDBY'] as const;
export type MplsLspPathType = (typeof MPLS_LSP_PATH_TYPE_VALUES)[number];
export const MPLS_LSP_PATH_TYPE_LABELS: Record<MplsLspPathType, string> = {
  PRIMARY: 'Primary',
  SECONDARY: 'Secondary',
  STANDBY: 'Standby',
};
export const MPLS_LSP_PATH_TYPE_OPTIONS = MPLS_LSP_PATH_TYPE_VALUES.map((value) => ({ value, label: MPLS_LSP_PATH_TYPE_LABELS[value] }));

export const MPLS_LSP_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type MplsLspCriticality = (typeof MPLS_LSP_CRITICALITY_VALUES)[number];
export const MPLS_LSP_CRITICALITY_LABELS: Record<MplsLspCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const MPLS_LSP_CRITICALITY_OPTIONS = MPLS_LSP_CRITICALITY_VALUES.map((value) => ({ value, label: MPLS_LSP_CRITICALITY_LABELS[value] }));

export interface MplsLsp {
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
  lspName: string;
  headEndId: string;
  tailEndId: string;
  bandwidthMbps?: number;
  setupPriority?: number;
  pathType?: MplsLspPathType;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: MplsLspCriticality;
  costCenter?: string;
}

export interface MplsLspInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  lspName: string;
  headEndId: string;
  tailEndId: string;
  bandwidthMbps?: number;
  setupPriority?: number;
  pathType?: MplsLspPathType;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: MplsLspCriticality;
  costCenter?: string;
}

export type MplsLspFormValues = Partial<MplsLspInput>;

export interface MplsLspUpdateInput extends Partial<MplsLspInput> {
  expectedVersion?: number;
}

export interface MplsLspFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  lspName?: string;
  headEndId?: string;
  tailEndId?: string;
  bandwidthMbpsMin?: number;
  bandwidthMbpsMax?: number;
  setupPriorityMin?: number;
  setupPriorityMax?: number;
  pathType?: MplsLspPathType[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: MplsLspCriticality[];
  costCenter?: string;
}

export type MplsLspSort = SortState<keyof MplsLsp & string>;

export interface MplsLspSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const MPLSLSP_PATH = '/api/v1/mpls-lsps';
export const MPLSLSP_TITLE = 'Mpls Lsp';
export const MPLSLSP_TITLE_PLURAL = 'Mpls Lsps';

export const MPLSLSP_DEFAULTS: MplsLspFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  lspName: '',
};
