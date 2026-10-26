import type { LifecycleStatus, SortState } from '@/shared/types';

export const CIRCUIT_PROVIDER_PROVIDER_TYPE_VALUES = ['TIER1', 'TIER2', 'LOCAL', 'WHOLESALE'] as const;
export type CircuitProviderProviderType = (typeof CIRCUIT_PROVIDER_PROVIDER_TYPE_VALUES)[number];
export const CIRCUIT_PROVIDER_PROVIDER_TYPE_LABELS: Record<CircuitProviderProviderType, string> = {
  TIER1: 'Tier1',
  TIER2: 'Tier2',
  LOCAL: 'Local',
  WHOLESALE: 'Wholesale',
};
export const CIRCUIT_PROVIDER_PROVIDER_TYPE_OPTIONS = CIRCUIT_PROVIDER_PROVIDER_TYPE_VALUES.map((value) => ({ value, label: CIRCUIT_PROVIDER_PROVIDER_TYPE_LABELS[value] }));

export const CIRCUIT_PROVIDER_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type CircuitProviderCriticality = (typeof CIRCUIT_PROVIDER_CRITICALITY_VALUES)[number];
export const CIRCUIT_PROVIDER_CRITICALITY_LABELS: Record<CircuitProviderCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const CIRCUIT_PROVIDER_CRITICALITY_OPTIONS = CIRCUIT_PROVIDER_CRITICALITY_VALUES.map((value) => ({ value, label: CIRCUIT_PROVIDER_CRITICALITY_LABELS[value] }));

export interface CircuitProvider {
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
  providerName: string;
  accountNumber?: string;
  noc?: string;
  escalationEmail?: string;
  providerType?: CircuitProviderProviderType;
  owner?: string;
  criticality?: CircuitProviderCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface CircuitProviderInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  providerName: string;
  accountNumber?: string;
  noc?: string;
  escalationEmail?: string;
  providerType?: CircuitProviderProviderType;
  owner?: string;
  criticality?: CircuitProviderCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type CircuitProviderFormValues = Partial<CircuitProviderInput>;

export interface CircuitProviderUpdateInput extends Partial<CircuitProviderInput> {
  expectedVersion?: number;
}

export interface CircuitProviderFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  providerName?: string;
  accountNumber?: string;
  noc?: string;
  escalationEmail?: string;
  providerType?: CircuitProviderProviderType[];
  owner?: string;
  criticality?: CircuitProviderCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type CircuitProviderSort = SortState<keyof CircuitProvider & string>;

export interface CircuitProviderSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CIRCUITPROVIDER_PATH = '/api/v1/circuit-providers';
export const CIRCUITPROVIDER_TITLE = 'Circuit Provider';
export const CIRCUITPROVIDER_TITLE_PLURAL = 'Circuit Providers';

export const CIRCUITPROVIDER_DEFAULTS: CircuitProviderFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  providerName: '',
};
