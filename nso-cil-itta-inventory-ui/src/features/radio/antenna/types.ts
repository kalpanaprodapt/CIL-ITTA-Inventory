import type { LifecycleStatus, SortState } from '@/shared/types';

export const ANTENNA_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type AntennaCriticality = (typeof ANTENNA_CRITICALITY_VALUES)[number];
export const ANTENNA_CRITICALITY_LABELS: Record<AntennaCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const ANTENNA_CRITICALITY_OPTIONS = ANTENNA_CRITICALITY_VALUES.map((value) => ({ value, label: ANTENNA_CRITICALITY_LABELS[value] }));

export interface Antenna {
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
  sectorId: string;
  model?: string;
  heightM?: number;
  azimuthDeg?: number;
  tiltDeg?: number;
  gainDbi?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AntennaCriticality;
  costCenter?: string;
}

export interface AntennaInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  sectorId: string;
  model?: string;
  heightM?: number;
  azimuthDeg?: number;
  tiltDeg?: number;
  gainDbi?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AntennaCriticality;
  costCenter?: string;
}

export type AntennaFormValues = Partial<AntennaInput>;

export interface AntennaUpdateInput extends Partial<AntennaInput> {
  expectedVersion?: number;
}

export interface AntennaFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  sectorId?: string;
  model?: string;
  heightMMin?: number;
  heightMMax?: number;
  azimuthDegMin?: number;
  azimuthDegMax?: number;
  tiltDegMin?: number;
  tiltDegMax?: number;
  gainDbiMin?: number;
  gainDbiMax?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AntennaCriticality[];
  costCenter?: string;
}

export type AntennaSort = SortState<keyof Antenna & string>;

export interface AntennaSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ANTENNA_PATH = '/api/v1/antennas';
export const ANTENNA_TITLE = 'Antenna';
export const ANTENNA_TITLE_PLURAL = 'Antennas';

export const ANTENNA_DEFAULTS: AntennaFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
