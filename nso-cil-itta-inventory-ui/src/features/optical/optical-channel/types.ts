import type { LifecycleStatus, SortState } from '@/shared/types';

export const OPTICAL_CHANNEL_GRID_TYPE_VALUES = ['FIXED', 'FLEX'] as const;
export type OpticalChannelGridType = (typeof OPTICAL_CHANNEL_GRID_TYPE_VALUES)[number];
export const OPTICAL_CHANNEL_GRID_TYPE_LABELS: Record<OpticalChannelGridType, string> = {
  FIXED: 'Fixed',
  FLEX: 'Flex',
};
export const OPTICAL_CHANNEL_GRID_TYPE_OPTIONS = OPTICAL_CHANNEL_GRID_TYPE_VALUES.map((value) => ({ value, label: OPTICAL_CHANNEL_GRID_TYPE_LABELS[value] }));

export const OPTICAL_CHANNEL_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type OpticalChannelCriticality = (typeof OPTICAL_CHANNEL_CRITICALITY_VALUES)[number];
export const OPTICAL_CHANNEL_CRITICALITY_LABELS: Record<OpticalChannelCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const OPTICAL_CHANNEL_CRITICALITY_OPTIONS = OPTICAL_CHANNEL_CRITICALITY_VALUES.map((value) => ({ value, label: OPTICAL_CHANNEL_CRITICALITY_LABELS[value] }));

export interface OpticalChannel {
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
  wavelengthNm: number;
  channelNumber: number;
  gridType?: OpticalChannelGridType;
  bitrateGbps?: number;
  systemId?: string;
  criticality?: OpticalChannelCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface OpticalChannelInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  wavelengthNm: number;
  channelNumber: number;
  gridType?: OpticalChannelGridType;
  bitrateGbps?: number;
  systemId?: string;
  criticality?: OpticalChannelCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type OpticalChannelFormValues = Partial<OpticalChannelInput>;

export interface OpticalChannelUpdateInput extends Partial<OpticalChannelInput> {
  expectedVersion?: number;
}

export interface OpticalChannelFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  wavelengthNmMin?: number;
  wavelengthNmMax?: number;
  channelNumberMin?: number;
  channelNumberMax?: number;
  gridType?: OpticalChannelGridType[];
  bitrateGbpsMin?: number;
  bitrateGbpsMax?: number;
  systemId?: string;
  criticality?: OpticalChannelCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type OpticalChannelSort = SortState<keyof OpticalChannel & string>;

export interface OpticalChannelSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const OPTICALCHANNEL_PATH = '/api/v1/optical-channels';
export const OPTICALCHANNEL_TITLE = 'Optical Channel';
export const OPTICALCHANNEL_TITLE_PLURAL = 'Optical Channels';

export const OPTICALCHANNEL_DEFAULTS: OpticalChannelFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
