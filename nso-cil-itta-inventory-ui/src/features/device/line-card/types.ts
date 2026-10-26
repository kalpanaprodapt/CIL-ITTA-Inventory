import type { LifecycleStatus, SortState } from '@/shared/types';

export const LINE_CARD_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type LineCardCriticality = (typeof LINE_CARD_CRITICALITY_VALUES)[number];
export const LINE_CARD_CRITICALITY_LABELS: Record<LineCardCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const LINE_CARD_CRITICALITY_OPTIONS = LINE_CARD_CRITICALITY_VALUES.map((value) => ({ value, label: LINE_CARD_CRITICALITY_LABELS[value] }));

export interface LineCard {
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
  slotNumber: number;
  cardType?: string;
  portCount?: number;
  serialNumber?: string;
  firmware?: string;
  region?: string;
  owner?: string;
  criticality?: LineCardCriticality;
  costCenter?: string;
  notes?: string;
}

export interface LineCardInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  chassisId: string;
  slotNumber: number;
  cardType?: string;
  portCount?: number;
  serialNumber?: string;
  firmware?: string;
  region?: string;
  owner?: string;
  criticality?: LineCardCriticality;
  costCenter?: string;
  notes?: string;
}

export type LineCardFormValues = Partial<LineCardInput>;

export interface LineCardUpdateInput extends Partial<LineCardInput> {
  expectedVersion?: number;
}

export interface LineCardFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  chassisId?: string;
  slotNumberMin?: number;
  slotNumberMax?: number;
  cardType?: string;
  portCountMin?: number;
  portCountMax?: number;
  serialNumber?: string;
  firmware?: string;
  region?: string;
  owner?: string;
  criticality?: LineCardCriticality[];
  costCenter?: string;
}

export type LineCardSort = SortState<keyof LineCard & string>;

export interface LineCardSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const LINECARD_PATH = '/api/v1/line-cards';
export const LINECARD_TITLE = 'Line Card';
export const LINECARD_TITLE_PLURAL = 'Line Cards';

export const LINECARD_DEFAULTS: LineCardFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
