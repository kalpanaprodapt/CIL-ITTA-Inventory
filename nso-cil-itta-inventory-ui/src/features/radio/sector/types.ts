import type { LifecycleStatus, SortState } from '@/shared/types';

export const SECTOR_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SectorCriticality = (typeof SECTOR_CRITICALITY_VALUES)[number];
export const SECTOR_CRITICALITY_LABELS: Record<SectorCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SECTOR_CRITICALITY_OPTIONS = SECTOR_CRITICALITY_VALUES.map((value) => ({ value, label: SECTOR_CRITICALITY_LABELS[value] }));

export interface Sector {
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
  sectorNumber: number;
  pci?: number;
  frequencyMhz?: number;
  bandwidthMhz?: number;
  owner?: string;
  criticality?: SectorCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface SectorInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  baseStationId: string;
  sectorNumber: number;
  pci?: number;
  frequencyMhz?: number;
  bandwidthMhz?: number;
  owner?: string;
  criticality?: SectorCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type SectorFormValues = Partial<SectorInput>;

export interface SectorUpdateInput extends Partial<SectorInput> {
  expectedVersion?: number;
}

export interface SectorFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  baseStationId?: string;
  sectorNumberMin?: number;
  sectorNumberMax?: number;
  pciMin?: number;
  pciMax?: number;
  frequencyMhzMin?: number;
  frequencyMhzMax?: number;
  bandwidthMhzMin?: number;
  bandwidthMhzMax?: number;
  owner?: string;
  criticality?: SectorCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type SectorSort = SortState<keyof Sector & string>;

export interface SectorSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SECTOR_PATH = '/api/v1/sectors';
export const SECTOR_TITLE = 'Sector';
export const SECTOR_TITLE_PLURAL = 'Sectors';

export const SECTOR_DEFAULTS: SectorFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
