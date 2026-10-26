import type { LifecycleStatus, SortState } from '@/shared/types';

export const POWER_FEED_FEED_TYPE_VALUES = ['AC', 'DC'] as const;
export type PowerFeedFeedType = (typeof POWER_FEED_FEED_TYPE_VALUES)[number];
export const POWER_FEED_FEED_TYPE_LABELS: Record<PowerFeedFeedType, string> = {
  AC: 'Ac',
  DC: 'Dc',
};
export const POWER_FEED_FEED_TYPE_OPTIONS = POWER_FEED_FEED_TYPE_VALUES.map((value) => ({ value, label: POWER_FEED_FEED_TYPE_LABELS[value] }));

export const POWER_FEED_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PowerFeedCriticality = (typeof POWER_FEED_CRITICALITY_VALUES)[number];
export const POWER_FEED_CRITICALITY_LABELS: Record<PowerFeedCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const POWER_FEED_CRITICALITY_OPTIONS = POWER_FEED_CRITICALITY_VALUES.map((value) => ({ value, label: POWER_FEED_CRITICALITY_LABELS[value] }));

export interface PowerFeed {
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
  roomId: string;
  feedType?: PowerFeedFeedType;
  voltage?: number;
  amperage?: number;
  phase?: string;
  breakerLabel?: string;
  redundancyGroup?: string;
  owner?: string;
  criticality?: PowerFeedCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface PowerFeedInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  roomId: string;
  feedType?: PowerFeedFeedType;
  voltage?: number;
  amperage?: number;
  phase?: string;
  breakerLabel?: string;
  redundancyGroup?: string;
  owner?: string;
  criticality?: PowerFeedCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type PowerFeedFormValues = Partial<PowerFeedInput>;

export interface PowerFeedUpdateInput extends Partial<PowerFeedInput> {
  expectedVersion?: number;
}

export interface PowerFeedFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  roomId?: string;
  feedType?: PowerFeedFeedType[];
  voltageMin?: number;
  voltageMax?: number;
  amperageMin?: number;
  amperageMax?: number;
  phase?: string;
  breakerLabel?: string;
  redundancyGroup?: string;
  owner?: string;
  criticality?: PowerFeedCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type PowerFeedSort = SortState<keyof PowerFeed & string>;

export interface PowerFeedSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const POWERFEED_PATH = '/api/v1/power-feeds';
export const POWERFEED_TITLE = 'Power Feed';
export const POWERFEED_TITLE_PLURAL = 'Power Feeds';

export const POWERFEED_DEFAULTS: PowerFeedFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
