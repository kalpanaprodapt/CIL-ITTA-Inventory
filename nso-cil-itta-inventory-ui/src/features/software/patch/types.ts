import type { LifecycleStatus, SortState } from '@/shared/types';

export const PATCH_SEVERITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PatchSeverity = (typeof PATCH_SEVERITY_VALUES)[number];
export const PATCH_SEVERITY_LABELS: Record<PatchSeverity, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const PATCH_SEVERITY_OPTIONS = PATCH_SEVERITY_VALUES.map((value) => ({ value, label: PATCH_SEVERITY_LABELS[value] }));

export const PATCH_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PatchCriticality = (typeof PATCH_CRITICALITY_VALUES)[number];
export const PATCH_CRITICALITY_LABELS: Record<PatchCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const PATCH_CRITICALITY_OPTIONS = PATCH_CRITICALITY_VALUES.map((value) => ({ value, label: PATCH_CRITICALITY_LABELS[value] }));

export interface Patch {
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
  versionId: string;
  patchId: string;
  severity?: PatchSeverity;
  releasedOn?: string;
  rebootRequired?: boolean;
  criticality?: PatchCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface PatchInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  versionId: string;
  patchId: string;
  severity?: PatchSeverity;
  releasedOn?: string;
  rebootRequired?: boolean;
  criticality?: PatchCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type PatchFormValues = Partial<PatchInput>;

export interface PatchUpdateInput extends Partial<PatchInput> {
  expectedVersion?: number;
}

export interface PatchFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  versionId?: string;
  patchId?: string;
  severity?: PatchSeverity[];
  releasedOnFrom?: string;
  releasedOnTo?: string;
  rebootRequired?: boolean;
  criticality?: PatchCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type PatchSort = SortState<keyof Patch & string>;

export interface PatchSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const PATCH_PATH = '/api/v1/patchs';
export const PATCH_TITLE = 'Patch';
export const PATCH_TITLE_PLURAL = 'Patchs';

export const PATCH_DEFAULTS: PatchFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  patchId: '',
  rebootRequired: false,
};
