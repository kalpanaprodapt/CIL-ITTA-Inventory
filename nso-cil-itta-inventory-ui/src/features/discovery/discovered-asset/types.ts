import type { LifecycleStatus, SortState } from '@/shared/types';

export const DISCOVERED_ASSET_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type DiscoveredAssetLifecycleStage = (typeof DISCOVERED_ASSET_LIFECYCLE_STAGE_VALUES)[number];
export const DISCOVERED_ASSET_LIFECYCLE_STAGE_LABELS: Record<DiscoveredAssetLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const DISCOVERED_ASSET_LIFECYCLE_STAGE_OPTIONS = DISCOVERED_ASSET_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: DISCOVERED_ASSET_LIFECYCLE_STAGE_LABELS[value] }));

export interface DiscoveredAsset {
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
  jobId: string;
  assetType: string;
  fingerprint: string;
  rawPayload?: string;
  matchedAssetId?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: DiscoveredAssetLifecycleStage;
  sourceSystem?: string;
}

export interface DiscoveredAssetInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  jobId: string;
  assetType: string;
  fingerprint: string;
  rawPayload?: string;
  matchedAssetId?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: DiscoveredAssetLifecycleStage;
  sourceSystem?: string;
}

export type DiscoveredAssetFormValues = Partial<DiscoveredAssetInput>;

export interface DiscoveredAssetUpdateInput extends Partial<DiscoveredAssetInput> {
  expectedVersion?: number;
}

export interface DiscoveredAssetFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  jobId?: string;
  assetType?: string;
  fingerprint?: string;
  matchedAssetId?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: DiscoveredAssetLifecycleStage[];
  sourceSystem?: string;
}

export type DiscoveredAssetSort = SortState<keyof DiscoveredAsset & string>;

export interface DiscoveredAssetSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const DISCOVEREDASSET_PATH = '/api/v1/discovered-assets';
export const DISCOVEREDASSET_TITLE = 'Discovered Asset';
export const DISCOVEREDASSET_TITLE_PLURAL = 'Discovered Assets';

export const DISCOVEREDASSET_DEFAULTS: DiscoveredAssetFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  assetType: '',
  fingerprint: '',
};
