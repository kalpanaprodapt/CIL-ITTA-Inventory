import type { LifecycleStatus, SortState } from '@/shared/types';

export const TECHNICIAN_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type TechnicianCriticality = (typeof TECHNICIAN_CRITICALITY_VALUES)[number];
export const TECHNICIAN_CRITICALITY_LABELS: Record<TechnicianCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const TECHNICIAN_CRITICALITY_OPTIONS = TECHNICIAN_CRITICALITY_VALUES.map((value) => ({ value, label: TECHNICIAN_CRITICALITY_LABELS[value] }));

export interface Technician {
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
  employeeId: string;
  fullName: string;
  skillSet?: string;
  homeSiteId?: string;
  certificationLevel?: number;
  criticality?: TechnicianCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface TechnicianInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  employeeId: string;
  fullName: string;
  skillSet?: string;
  homeSiteId?: string;
  certificationLevel?: number;
  criticality?: TechnicianCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type TechnicianFormValues = Partial<TechnicianInput>;

export interface TechnicianUpdateInput extends Partial<TechnicianInput> {
  expectedVersion?: number;
}

export interface TechnicianFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  employeeId?: string;
  fullName?: string;
  skillSet?: string;
  homeSiteId?: string;
  certificationLevelMin?: number;
  certificationLevelMax?: number;
  criticality?: TechnicianCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type TechnicianSort = SortState<keyof Technician & string>;

export interface TechnicianSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const TECHNICIAN_PATH = '/api/v1/technicians';
export const TECHNICIAN_TITLE = 'Technician';
export const TECHNICIAN_TITLE_PLURAL = 'Technicians';

export const TECHNICIAN_DEFAULTS: TechnicianFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  employeeId: '',
  fullName: '',
};
