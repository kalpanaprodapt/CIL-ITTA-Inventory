import type { LifecycleStatus, SortState } from '@/shared/types';

export interface PseudoWire {
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
  vcId: number;
  aEndId: string;
  zEndId: string;
  encapsulation?: string;
  controlWord?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface PseudoWireInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vcId: number;
  aEndId: string;
  zEndId: string;
  encapsulation?: string;
  controlWord?: boolean;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type PseudoWireFormValues = Partial<PseudoWireInput>;

export interface PseudoWireUpdateInput extends Partial<PseudoWireInput> {
  expectedVersion?: number;
}

export interface PseudoWireFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vcIdMin?: number;
  vcIdMax?: number;
  aEndId?: string;
  zEndId?: string;
  encapsulation?: string;
  controlWord?: boolean;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type PseudoWireSort = SortState<keyof PseudoWire & string>;

export interface PseudoWireSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const PSEUDOWIRE_PATH = '/api/v1/pseudo-wires';
export const PSEUDOWIRE_TITLE = 'Pseudo Wire';
export const PSEUDOWIRE_TITLE_PLURAL = 'Pseudo Wirees';

export const PSEUDOWIRE_DEFAULTS: PseudoWireFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  controlWord: false,
};
