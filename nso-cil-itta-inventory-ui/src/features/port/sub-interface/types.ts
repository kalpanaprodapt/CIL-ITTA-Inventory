import type { LifecycleStatus, SortState } from '@/shared/types';

export interface SubInterface {
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
  interfaceId: string;
  vlanTag: number;
  encapsulation?: string;
  ipv4Address?: string;
  ipv6Address?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface SubInterfaceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  interfaceId: string;
  vlanTag: number;
  encapsulation?: string;
  ipv4Address?: string;
  ipv6Address?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type SubInterfaceFormValues = Partial<SubInterfaceInput>;

export interface SubInterfaceUpdateInput extends Partial<SubInterfaceInput> {
  expectedVersion?: number;
}

export interface SubInterfaceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  interfaceId?: string;
  vlanTagMin?: number;
  vlanTagMax?: number;
  encapsulation?: string;
  ipv4Address?: string;
  ipv6Address?: string;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type SubInterfaceSort = SortState<keyof SubInterface & string>;

export interface SubInterfaceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SUBINTERFACE_PATH = '/api/v1/sub-interfaces';
export const SUBINTERFACE_TITLE = 'Sub Interface';
export const SUBINTERFACE_TITLE_PLURAL = 'Sub Interfacees';

export const SUBINTERFACE_DEFAULTS: SubInterfaceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
