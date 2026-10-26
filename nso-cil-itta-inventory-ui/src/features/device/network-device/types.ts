import type { LifecycleStatus, SortState } from '@/shared/types';

export const NETWORK_DEVICE_DEVICE_ROLE_VALUES = ['CORE', 'EDGE', 'AGG', 'ACCESS', 'CPE', 'FIREWALL', 'LB'] as const;
export type NetworkDeviceDeviceRole = (typeof NETWORK_DEVICE_DEVICE_ROLE_VALUES)[number];
export const NETWORK_DEVICE_DEVICE_ROLE_LABELS: Record<NetworkDeviceDeviceRole, string> = {
  CORE: 'Core',
  EDGE: 'Edge',
  AGG: 'Agg',
  ACCESS: 'Access',
  CPE: 'Cpe',
  FIREWALL: 'Firewall',
  LB: 'Lb',
};
export const NETWORK_DEVICE_DEVICE_ROLE_OPTIONS = NETWORK_DEVICE_DEVICE_ROLE_VALUES.map((value) => ({ value, label: NETWORK_DEVICE_DEVICE_ROLE_LABELS[value] }));

export const NETWORK_DEVICE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type NetworkDeviceLifecycleStage = (typeof NETWORK_DEVICE_LIFECYCLE_STAGE_VALUES)[number];
export const NETWORK_DEVICE_LIFECYCLE_STAGE_LABELS: Record<NetworkDeviceLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const NETWORK_DEVICE_LIFECYCLE_STAGE_OPTIONS = NETWORK_DEVICE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: NETWORK_DEVICE_LIFECYCLE_STAGE_LABELS[value] }));

export interface NetworkDevice {
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
  hostname: string;
  deviceRole?: NetworkDeviceDeviceRole;
  mgmtIp?: string;
  serialNumber?: string;
  modelId?: string;
  rackUnitId?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: NetworkDeviceLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface NetworkDeviceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  hostname: string;
  deviceRole?: NetworkDeviceDeviceRole;
  mgmtIp?: string;
  serialNumber?: string;
  modelId?: string;
  rackUnitId?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: NetworkDeviceLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type NetworkDeviceFormValues = Partial<NetworkDeviceInput>;

export interface NetworkDeviceUpdateInput extends Partial<NetworkDeviceInput> {
  expectedVersion?: number;
}

export interface NetworkDeviceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  hostname?: string;
  deviceRole?: NetworkDeviceDeviceRole[];
  mgmtIp?: string;
  serialNumber?: string;
  modelId?: string;
  rackUnitId?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: NetworkDeviceLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type NetworkDeviceSort = SortState<keyof NetworkDevice & string>;

export interface NetworkDeviceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const NETWORKDEVICE_PATH = '/api/v1/network-devices';
export const NETWORKDEVICE_TITLE = 'Network Device';
export const NETWORKDEVICE_TITLE_PLURAL = 'Network Devicees';

export const NETWORKDEVICE_DEFAULTS: NetworkDeviceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  hostname: '',
};
