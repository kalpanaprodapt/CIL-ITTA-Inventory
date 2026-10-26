import type { LifecycleStatus, SortState } from '@/shared/types';

export const CIRCUIT_SEGMENT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type CircuitSegmentCriticality = (typeof CIRCUIT_SEGMENT_CRITICALITY_VALUES)[number];
export const CIRCUIT_SEGMENT_CRITICALITY_LABELS: Record<CircuitSegmentCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const CIRCUIT_SEGMENT_CRITICALITY_OPTIONS = CIRCUIT_SEGMENT_CRITICALITY_VALUES.map((value) => ({ value, label: CIRCUIT_SEGMENT_CRITICALITY_LABELS[value] }));

export interface CircuitSegment {
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
  circuitId: string;
  segmentOrder: number;
  aTerminationId?: string;
  zTerminationId?: string;
  latencyMs?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: CircuitSegmentCriticality;
  costCenter?: string;
}

export interface CircuitSegmentInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  circuitId: string;
  segmentOrder: number;
  aTerminationId?: string;
  zTerminationId?: string;
  latencyMs?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: CircuitSegmentCriticality;
  costCenter?: string;
}

export type CircuitSegmentFormValues = Partial<CircuitSegmentInput>;

export interface CircuitSegmentUpdateInput extends Partial<CircuitSegmentInput> {
  expectedVersion?: number;
}

export interface CircuitSegmentFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  circuitId?: string;
  segmentOrderMin?: number;
  segmentOrderMax?: number;
  aTerminationId?: string;
  zTerminationId?: string;
  latencyMsMin?: number;
  latencyMsMax?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: CircuitSegmentCriticality[];
  costCenter?: string;
}

export type CircuitSegmentSort = SortState<keyof CircuitSegment & string>;

export interface CircuitSegmentSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const CIRCUITSEGMENT_PATH = '/api/v1/circuit-segments';
export const CIRCUITSEGMENT_TITLE = 'Circuit Segment';
export const CIRCUITSEGMENT_TITLE_PLURAL = 'Circuit Segments';

export const CIRCUITSEGMENT_DEFAULTS: CircuitSegmentFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
