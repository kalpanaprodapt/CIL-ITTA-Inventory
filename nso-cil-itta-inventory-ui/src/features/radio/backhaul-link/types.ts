import type { LifecycleStatus, SortState } from '@/shared/types';

export const BACKHAUL_LINK_LINK_MEDIUM_VALUES = ['FIBER', 'MICROWAVE', 'SATELLITE'] as const;
export type BackhaulLinkLinkMedium = (typeof BACKHAUL_LINK_LINK_MEDIUM_VALUES)[number];
export const BACKHAUL_LINK_LINK_MEDIUM_LABELS: Record<BackhaulLinkLinkMedium, string> = {
  FIBER: 'Fiber',
  MICROWAVE: 'Microwave',
  SATELLITE: 'Satellite',
};
export const BACKHAUL_LINK_LINK_MEDIUM_OPTIONS = BACKHAUL_LINK_LINK_MEDIUM_VALUES.map((value) => ({ value, label: BACKHAUL_LINK_LINK_MEDIUM_LABELS[value] }));

export interface BackhaulLink {
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
  baseStationId: string;
  linkMedium?: BackhaulLinkLinkMedium;
  capacityMbps: number;
  latencyMs?: number;
  circuitRef?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface BackhaulLinkInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  baseStationId: string;
  linkMedium?: BackhaulLinkLinkMedium;
  capacityMbps: number;
  latencyMs?: number;
  circuitRef?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type BackhaulLinkFormValues = Partial<BackhaulLinkInput>;

export interface BackhaulLinkUpdateInput extends Partial<BackhaulLinkInput> {
  expectedVersion?: number;
}

export interface BackhaulLinkFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  baseStationId?: string;
  linkMedium?: BackhaulLinkLinkMedium[];
  capacityMbpsMin?: number;
  capacityMbpsMax?: number;
  latencyMsMin?: number;
  latencyMsMax?: number;
  circuitRef?: string;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type BackhaulLinkSort = SortState<keyof BackhaulLink & string>;

export interface BackhaulLinkSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const BACKHAULLINK_PATH = '/api/v1/backhaul-links';
export const BACKHAULLINK_TITLE = 'Backhaul Link';
export const BACKHAULLINK_TITLE_PLURAL = 'Backhaul Links';

export const BACKHAULLINK_DEFAULTS: BackhaulLinkFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
