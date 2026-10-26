import type { LifecycleStatus, SortState } from '@/shared/types';

export const PATCH_PANEL_PANEL_TYPE_VALUES = ['COPPER', 'FIBER'] as const;
export type PatchPanelPanelType = (typeof PATCH_PANEL_PANEL_TYPE_VALUES)[number];
export const PATCH_PANEL_PANEL_TYPE_LABELS: Record<PatchPanelPanelType, string> = {
  COPPER: 'Copper',
  FIBER: 'Fiber',
};
export const PATCH_PANEL_PANEL_TYPE_OPTIONS = PATCH_PANEL_PANEL_TYPE_VALUES.map((value) => ({ value, label: PATCH_PANEL_PANEL_TYPE_LABELS[value] }));

export const PATCH_PANEL_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type PatchPanelLifecycleStage = (typeof PATCH_PANEL_LIFECYCLE_STAGE_VALUES)[number];
export const PATCH_PANEL_LIFECYCLE_STAGE_LABELS: Record<PatchPanelLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const PATCH_PANEL_LIFECYCLE_STAGE_OPTIONS = PATCH_PANEL_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: PATCH_PANEL_LIFECYCLE_STAGE_LABELS[value] }));

export interface PatchPanel {
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
  roomId: string;
  rackId?: string;
  panelType?: PatchPanelPanelType;
  portCount: number;
  label?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: PatchPanelLifecycleStage;
}

export interface PatchPanelInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  roomId: string;
  rackId?: string;
  panelType?: PatchPanelPanelType;
  portCount: number;
  label?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: PatchPanelLifecycleStage;
}

export type PatchPanelFormValues = Partial<PatchPanelInput>;

export interface PatchPanelUpdateInput extends Partial<PatchPanelInput> {
  expectedVersion?: number;
}

export interface PatchPanelFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  roomId?: string;
  rackId?: string;
  panelType?: PatchPanelPanelType[];
  portCountMin?: number;
  portCountMax?: number;
  label?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: PatchPanelLifecycleStage[];
}

export type PatchPanelSort = SortState<keyof PatchPanel & string>;

export interface PatchPanelSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const PATCHPANEL_PATH = '/api/v1/patch-panels';
export const PATCHPANEL_TITLE = 'Patch Panel';
export const PATCHPANEL_TITLE_PLURAL = 'Patch Panels';

export const PATCHPANEL_DEFAULTS: PatchPanelFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
