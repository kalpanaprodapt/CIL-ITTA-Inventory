import type { LifecycleStatus, SortState } from '@/shared/types';

export const FAN_TRAY_AIRFLOW_VALUES = ['FRONT_TO_BACK', 'BACK_TO_FRONT'] as const;
export type FanTrayAirflow = (typeof FAN_TRAY_AIRFLOW_VALUES)[number];
export const FAN_TRAY_AIRFLOW_LABELS: Record<FanTrayAirflow, string> = {
  FRONT_TO_BACK: 'Front To Back',
  BACK_TO_FRONT: 'Back To Front',
};
export const FAN_TRAY_AIRFLOW_OPTIONS = FAN_TRAY_AIRFLOW_VALUES.map((value) => ({ value, label: FAN_TRAY_AIRFLOW_LABELS[value] }));

export const FAN_TRAY_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type FanTrayLifecycleStage = (typeof FAN_TRAY_LIFECYCLE_STAGE_VALUES)[number];
export const FAN_TRAY_LIFECYCLE_STAGE_LABELS: Record<FanTrayLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const FAN_TRAY_LIFECYCLE_STAGE_OPTIONS = FAN_TRAY_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: FAN_TRAY_LIFECYCLE_STAGE_LABELS[value] }));

export interface FanTray {
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
  chassisId: string;
  slotNumber?: number;
  fanCount?: number;
  airflow?: FanTrayAirflow;
  serialNumber?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: FanTrayLifecycleStage;
}

export interface FanTrayInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  chassisId: string;
  slotNumber?: number;
  fanCount?: number;
  airflow?: FanTrayAirflow;
  serialNumber?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: FanTrayLifecycleStage;
}

export type FanTrayFormValues = Partial<FanTrayInput>;

export interface FanTrayUpdateInput extends Partial<FanTrayInput> {
  expectedVersion?: number;
}

export interface FanTrayFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  chassisId?: string;
  slotNumberMin?: number;
  slotNumberMax?: number;
  fanCountMin?: number;
  fanCountMax?: number;
  airflow?: FanTrayAirflow[];
  serialNumber?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: FanTrayLifecycleStage[];
}

export type FanTraySort = SortState<keyof FanTray & string>;

export interface FanTraySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const FANTRAY_PATH = '/api/v1/fan-traies';
export const FANTRAY_TITLE = 'Fan Tray';
export const FANTRAY_TITLE_PLURAL = 'Fan Trayes';

export const FANTRAY_DEFAULTS: FanTrayFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
