import type { LifecycleStatus, SortState } from '@/shared/types';

export const PATCH_PORT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type PatchPortLifecycleStage = (typeof PATCH_PORT_LIFECYCLE_STAGE_VALUES)[number];
export const PATCH_PORT_LIFECYCLE_STAGE_LABELS: Record<PatchPortLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const PATCH_PORT_LIFECYCLE_STAGE_OPTIONS = PATCH_PORT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: PATCH_PORT_LIFECYCLE_STAGE_LABELS[value] }));

export interface PatchPort {
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
  panelId: string;
  portNumber: number;
  connectedCableId?: string;
  polarity?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: PatchPortLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface PatchPortInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  panelId: string;
  portNumber: number;
  connectedCableId?: string;
  polarity?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: PatchPortLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type PatchPortFormValues = Partial<PatchPortInput>;

export interface PatchPortUpdateInput extends Partial<PatchPortInput> {
  expectedVersion?: number;
}

export interface PatchPortFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  panelId?: string;
  portNumberMin?: number;
  portNumberMax?: number;
  connectedCableId?: string;
  polarity?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: PatchPortLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type PatchPortSort = SortState<keyof PatchPort & string>;

export interface PatchPortSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const PATCHPORT_PATH = '/api/v1/patch-ports';
export const PATCHPORT_TITLE = 'Patch Port';
export const PATCHPORT_TITLE_PLURAL = 'Patch Ports';

export const PATCHPORT_DEFAULTS: PatchPortFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
