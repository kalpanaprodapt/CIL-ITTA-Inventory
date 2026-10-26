import type { LifecycleStatus, SortState } from '@/shared/types';

export const OTN_LINK_ODU_VALUES = ['ODU0', 'ODU1', 'ODU2', 'ODU4', 'ODUFLEX'] as const;
export type OtnLinkOdu = (typeof OTN_LINK_ODU_VALUES)[number];
export const OTN_LINK_ODU_LABELS: Record<OtnLinkOdu, string> = {
  ODU0: 'Odu0',
  ODU1: 'Odu1',
  ODU2: 'Odu2',
  ODU4: 'Odu4',
  ODUFLEX: 'Oduflex',
};
export const OTN_LINK_ODU_OPTIONS = OTN_LINK_ODU_VALUES.map((value) => ({ value, label: OTN_LINK_ODU_LABELS[value] }));

export const OTN_LINK_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type OtnLinkLifecycleStage = (typeof OTN_LINK_LIFECYCLE_STAGE_VALUES)[number];
export const OTN_LINK_LIFECYCLE_STAGE_LABELS: Record<OtnLinkLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const OTN_LINK_LIFECYCLE_STAGE_OPTIONS = OTN_LINK_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: OTN_LINK_LIFECYCLE_STAGE_LABELS[value] }));

export interface OtnLink {
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
  aNodeId: string;
  zNodeId: string;
  odu?: OtnLinkOdu;
  latencyUs?: number;
  protection?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: OtnLinkLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface OtnLinkInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  aNodeId: string;
  zNodeId: string;
  odu?: OtnLinkOdu;
  latencyUs?: number;
  protection?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: OtnLinkLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type OtnLinkFormValues = Partial<OtnLinkInput>;

export interface OtnLinkUpdateInput extends Partial<OtnLinkInput> {
  expectedVersion?: number;
}

export interface OtnLinkFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  aNodeId?: string;
  zNodeId?: string;
  odu?: OtnLinkOdu[];
  latencyUsMin?: number;
  latencyUsMax?: number;
  protection?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: OtnLinkLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type OtnLinkSort = SortState<keyof OtnLink & string>;

export interface OtnLinkSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const OTNLINK_PATH = '/api/v1/otn-links';
export const OTNLINK_TITLE = 'Otn Link';
export const OTNLINK_TITLE_PLURAL = 'Otn Links';

export const OTNLINK_DEFAULTS: OtnLinkFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
