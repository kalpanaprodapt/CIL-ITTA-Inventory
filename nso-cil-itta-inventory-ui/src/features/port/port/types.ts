import type { LifecycleStatus, SortState } from '@/shared/types';

export const PORT_MEDIA_TYPE_VALUES = ['COPPER', 'FIBER', 'DAC', 'WIRELESS'] as const;
export type PortMediaType = (typeof PORT_MEDIA_TYPE_VALUES)[number];
export const PORT_MEDIA_TYPE_LABELS: Record<PortMediaType, string> = {
  COPPER: 'Copper',
  FIBER: 'Fiber',
  DAC: 'Dac',
  WIRELESS: 'Wireless',
};
export const PORT_MEDIA_TYPE_OPTIONS = PORT_MEDIA_TYPE_VALUES.map((value) => ({ value, label: PORT_MEDIA_TYPE_LABELS[value] }));

export const PORT_ADMIN_STATE_VALUES = ['UP', 'DOWN'] as const;
export type PortAdminState = (typeof PORT_ADMIN_STATE_VALUES)[number];
export const PORT_ADMIN_STATE_LABELS: Record<PortAdminState, string> = {
  UP: 'Up',
  DOWN: 'Down',
};
export const PORT_ADMIN_STATE_OPTIONS = PORT_ADMIN_STATE_VALUES.map((value) => ({ value, label: PORT_ADMIN_STATE_LABELS[value] }));

export const PORT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PortCriticality = (typeof PORT_CRITICALITY_VALUES)[number];
export const PORT_CRITICALITY_LABELS: Record<PortCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const PORT_CRITICALITY_OPTIONS = PORT_CRITICALITY_VALUES.map((value) => ({ value, label: PORT_CRITICALITY_LABELS[value] }));

export interface Port {
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
  deviceId: string;
  cardId?: string;
  portNumber: string;
  speedMbps?: number;
  mediaType?: PortMediaType;
  adminState?: PortAdminState;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: PortCriticality;
  costCenter?: string;
}

export interface PortInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  deviceId: string;
  cardId?: string;
  portNumber: string;
  speedMbps?: number;
  mediaType?: PortMediaType;
  adminState?: PortAdminState;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: PortCriticality;
  costCenter?: string;
}

export type PortFormValues = Partial<PortInput>;

export interface PortUpdateInput extends Partial<PortInput> {
  expectedVersion?: number;
}

export interface PortFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  deviceId?: string;
  cardId?: string;
  portNumber?: string;
  speedMbpsMin?: number;
  speedMbpsMax?: number;
  mediaType?: PortMediaType[];
  adminState?: PortAdminState[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: PortCriticality[];
  costCenter?: string;
}

export type PortSort = SortState<keyof Port & string>;

export interface PortSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const PORT_PATH = '/api/v1/ports';
export const PORT_TITLE = 'Port';
export const PORT_TITLE_PLURAL = 'Ports';

export const PORT_DEFAULTS: PortFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  portNumber: '',
};
