import type { LifecycleStatus, SortState } from '@/shared/types';

export const MAINTENANCE_WINDOW_IMPACT_LEVEL_VALUES = ['NONE', 'DEGRADED', 'OUTAGE'] as const;
export type MaintenanceWindowImpactLevel = (typeof MAINTENANCE_WINDOW_IMPACT_LEVEL_VALUES)[number];
export const MAINTENANCE_WINDOW_IMPACT_LEVEL_LABELS: Record<MaintenanceWindowImpactLevel, string> = {
  NONE: 'None',
  DEGRADED: 'Degraded',
  OUTAGE: 'Outage',
};
export const MAINTENANCE_WINDOW_IMPACT_LEVEL_OPTIONS = MAINTENANCE_WINDOW_IMPACT_LEVEL_VALUES.map((value) => ({ value, label: MAINTENANCE_WINDOW_IMPACT_LEVEL_LABELS[value] }));

export const MAINTENANCE_WINDOW_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type MaintenanceWindowLifecycleStage = (typeof MAINTENANCE_WINDOW_LIFECYCLE_STAGE_VALUES)[number];
export const MAINTENANCE_WINDOW_LIFECYCLE_STAGE_LABELS: Record<MaintenanceWindowLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const MAINTENANCE_WINDOW_LIFECYCLE_STAGE_OPTIONS = MAINTENANCE_WINDOW_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: MAINTENANCE_WINDOW_LIFECYCLE_STAGE_LABELS[value] }));

export interface MaintenanceWindow {
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
  windowName: string;
  startsAt: string;
  endsAt: string;
  scope?: string;
  impactLevel?: MaintenanceWindowImpactLevel;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: MaintenanceWindowLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface MaintenanceWindowInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  windowName: string;
  startsAt: string;
  endsAt: string;
  scope?: string;
  impactLevel?: MaintenanceWindowImpactLevel;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: MaintenanceWindowLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type MaintenanceWindowFormValues = Partial<MaintenanceWindowInput>;

export interface MaintenanceWindowUpdateInput extends Partial<MaintenanceWindowInput> {
  expectedVersion?: number;
}

export interface MaintenanceWindowFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  windowName?: string;
  startsAtFrom?: string;
  startsAtTo?: string;
  endsAtFrom?: string;
  endsAtTo?: string;
  scope?: string;
  impactLevel?: MaintenanceWindowImpactLevel[];
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: MaintenanceWindowLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type MaintenanceWindowSort = SortState<keyof MaintenanceWindow & string>;

export interface MaintenanceWindowSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const MAINTENANCEWINDOW_PATH = '/api/v1/maintenance-windows';
export const MAINTENANCEWINDOW_TITLE = 'Maintenance Window';
export const MAINTENANCEWINDOW_TITLE_PLURAL = 'Maintenance Windows';

export const MAINTENANCEWINDOW_DEFAULTS: MaintenanceWindowFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  windowName: '',
};
