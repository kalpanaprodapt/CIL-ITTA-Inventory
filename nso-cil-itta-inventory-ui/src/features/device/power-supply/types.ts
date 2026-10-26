import type { LifecycleStatus, SortState } from '@/shared/types';

export const POWER_SUPPLY_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PowerSupplyCriticality = (typeof POWER_SUPPLY_CRITICALITY_VALUES)[number];
export const POWER_SUPPLY_CRITICALITY_LABELS: Record<PowerSupplyCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const POWER_SUPPLY_CRITICALITY_OPTIONS = POWER_SUPPLY_CRITICALITY_VALUES.map((value) => ({ value, label: POWER_SUPPLY_CRITICALITY_LABELS[value] }));

export interface PowerSupply {
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
  slotNumber?: number;
  wattage?: number;
  feedId?: string;
  serialNumber?: string;
  efficiency?: number;
  criticality?: PowerSupplyCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface PowerSupplyInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  chassisId: string;
  slotNumber?: number;
  wattage?: number;
  feedId?: string;
  serialNumber?: string;
  efficiency?: number;
  criticality?: PowerSupplyCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type PowerSupplyFormValues = Partial<PowerSupplyInput>;

export interface PowerSupplyUpdateInput extends Partial<PowerSupplyInput> {
  expectedVersion?: number;
}

export interface PowerSupplyFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  chassisId?: string;
  slotNumberMin?: number;
  slotNumberMax?: number;
  wattageMin?: number;
  wattageMax?: number;
  feedId?: string;
  serialNumber?: string;
  efficiencyMin?: number;
  efficiencyMax?: number;
  criticality?: PowerSupplyCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type PowerSupplySort = SortState<keyof PowerSupply & string>;

export interface PowerSupplySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const POWERSUPPLY_PATH = '/api/v1/power-supplies';
export const POWERSUPPLY_TITLE = 'Power Supply';
export const POWERSUPPLY_TITLE_PLURAL = 'Power Supplyes';

export const POWERSUPPLY_DEFAULTS: PowerSupplyFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
