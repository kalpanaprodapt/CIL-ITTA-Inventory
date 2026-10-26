import type { LifecycleStatus, SortState } from '@/shared/types';

export const SLA_PROFILE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SlaProfileCriticality = (typeof SLA_PROFILE_CRITICALITY_VALUES)[number];
export const SLA_PROFILE_CRITICALITY_LABELS: Record<SlaProfileCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SLA_PROFILE_CRITICALITY_OPTIONS = SLA_PROFILE_CRITICALITY_VALUES.map((value) => ({ value, label: SLA_PROFILE_CRITICALITY_LABELS[value] }));

export interface SlaProfile {
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
  profileName: string;
  availabilityPct: number;
  mttrMinutes?: number;
  latencyMs?: number;
  jitterMs?: number;
  creditPolicy?: string;
  owner?: string;
  criticality?: SlaProfileCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface SlaProfileInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  profileName: string;
  availabilityPct: number;
  mttrMinutes?: number;
  latencyMs?: number;
  jitterMs?: number;
  creditPolicy?: string;
  owner?: string;
  criticality?: SlaProfileCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type SlaProfileFormValues = Partial<SlaProfileInput>;

export interface SlaProfileUpdateInput extends Partial<SlaProfileInput> {
  expectedVersion?: number;
}

export interface SlaProfileFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  profileName?: string;
  availabilityPctMin?: number;
  availabilityPctMax?: number;
  mttrMinutesMin?: number;
  mttrMinutesMax?: number;
  latencyMsMin?: number;
  latencyMsMax?: number;
  jitterMsMin?: number;
  jitterMsMax?: number;
  owner?: string;
  criticality?: SlaProfileCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type SlaProfileSort = SortState<keyof SlaProfile & string>;

export interface SlaProfileSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SLAPROFILE_PATH = '/api/v1/sla-profiles';
export const SLAPROFILE_TITLE = 'Sla Profile';
export const SLAPROFILE_TITLE_PLURAL = 'Sla Profilees';

export const SLAPROFILE_DEFAULTS: SlaProfileFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  profileName: '',
};
