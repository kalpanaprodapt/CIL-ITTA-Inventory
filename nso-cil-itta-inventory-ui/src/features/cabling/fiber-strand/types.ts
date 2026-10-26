import type { LifecycleStatus, SortState } from '@/shared/types';

export const FIBER_STRAND_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type FiberStrandCriticality = (typeof FIBER_STRAND_CRITICALITY_VALUES)[number];
export const FIBER_STRAND_CRITICALITY_LABELS: Record<FiberStrandCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const FIBER_STRAND_CRITICALITY_OPTIONS = FIBER_STRAND_CRITICALITY_VALUES.map((value) => ({ value, label: FIBER_STRAND_CRITICALITY_LABELS[value] }));

export interface FiberStrand {
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
  cableId: string;
  strandNumber: number;
  bufferTube?: string;
  tubeColor?: string;
  attenuationDb?: number;
  inUse?: boolean;
  region?: string;
  owner?: string;
  criticality?: FiberStrandCriticality;
  costCenter?: string;
  notes?: string;
}

export interface FiberStrandInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  cableId: string;
  strandNumber: number;
  bufferTube?: string;
  tubeColor?: string;
  attenuationDb?: number;
  inUse?: boolean;
  region?: string;
  owner?: string;
  criticality?: FiberStrandCriticality;
  costCenter?: string;
  notes?: string;
}

export type FiberStrandFormValues = Partial<FiberStrandInput>;

export interface FiberStrandUpdateInput extends Partial<FiberStrandInput> {
  expectedVersion?: number;
}

export interface FiberStrandFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  cableId?: string;
  strandNumberMin?: number;
  strandNumberMax?: number;
  bufferTube?: string;
  tubeColor?: string;
  attenuationDbMin?: number;
  attenuationDbMax?: number;
  inUse?: boolean;
  region?: string;
  owner?: string;
  criticality?: FiberStrandCriticality[];
  costCenter?: string;
}

export type FiberStrandSort = SortState<keyof FiberStrand & string>;

export interface FiberStrandSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const FIBERSTRAND_PATH = '/api/v1/fiber-strands';
export const FIBERSTRAND_TITLE = 'Fiber Strand';
export const FIBERSTRAND_TITLE_PLURAL = 'Fiber Strands';

export const FIBERSTRAND_DEFAULTS: FiberStrandFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  inUse: false,
};
