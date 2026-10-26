import type { LifecycleStatus, SortState } from '@/shared/types';

export const AMPLIFIER_AMP_TYPE_VALUES = ['EDFA', 'RAMAN', 'HYBRID'] as const;
export type AmplifierAmpType = (typeof AMPLIFIER_AMP_TYPE_VALUES)[number];
export const AMPLIFIER_AMP_TYPE_LABELS: Record<AmplifierAmpType, string> = {
  EDFA: 'Edfa',
  RAMAN: 'Raman',
  HYBRID: 'Hybrid',
};
export const AMPLIFIER_AMP_TYPE_OPTIONS = AMPLIFIER_AMP_TYPE_VALUES.map((value) => ({ value, label: AMPLIFIER_AMP_TYPE_LABELS[value] }));

export const AMPLIFIER_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type AmplifierLifecycleStage = (typeof AMPLIFIER_LIFECYCLE_STAGE_VALUES)[number];
export const AMPLIFIER_LIFECYCLE_STAGE_LABELS: Record<AmplifierLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const AMPLIFIER_LIFECYCLE_STAGE_OPTIONS = AMPLIFIER_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: AMPLIFIER_LIFECYCLE_STAGE_LABELS[value] }));

export const AMPLIFIER_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type AmplifierCriticality = (typeof AMPLIFIER_CRITICALITY_VALUES)[number];
export const AMPLIFIER_CRITICALITY_LABELS: Record<AmplifierCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const AMPLIFIER_CRITICALITY_OPTIONS = AMPLIFIER_CRITICALITY_VALUES.map((value) => ({ value, label: AMPLIFIER_CRITICALITY_LABELS[value] }));

export interface Amplifier {
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
  systemId: string;
  ampType?: AmplifierAmpType;
  gainDb: number;
  outputPowerDbm?: number;
  noiseFigureDb?: number;
  lifecycleStage?: AmplifierLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AmplifierCriticality;
}

export interface AmplifierInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  systemId: string;
  ampType?: AmplifierAmpType;
  gainDb: number;
  outputPowerDbm?: number;
  noiseFigureDb?: number;
  lifecycleStage?: AmplifierLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AmplifierCriticality;
}

export type AmplifierFormValues = Partial<AmplifierInput>;

export interface AmplifierUpdateInput extends Partial<AmplifierInput> {
  expectedVersion?: number;
}

export interface AmplifierFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  systemId?: string;
  ampType?: AmplifierAmpType[];
  gainDbMin?: number;
  gainDbMax?: number;
  outputPowerDbmMin?: number;
  outputPowerDbmMax?: number;
  noiseFigureDbMin?: number;
  noiseFigureDbMax?: number;
  lifecycleStage?: AmplifierLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AmplifierCriticality[];
}

export type AmplifierSort = SortState<keyof Amplifier & string>;

export interface AmplifierSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const AMPLIFIER_PATH = '/api/v1/amplifiers';
export const AMPLIFIER_TITLE = 'Amplifier';
export const AMPLIFIER_TITLE_PLURAL = 'Amplifiers';

export const AMPLIFIER_DEFAULTS: AmplifierFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
