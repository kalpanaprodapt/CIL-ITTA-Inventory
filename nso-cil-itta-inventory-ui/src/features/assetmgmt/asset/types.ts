import type { LifecycleStatus, SortState } from '@/shared/types';

export const ASSET_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type AssetLifecycleStage = (typeof ASSET_LIFECYCLE_STAGE_VALUES)[number];
export const ASSET_LIFECYCLE_STAGE_LABELS: Record<AssetLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const ASSET_LIFECYCLE_STAGE_OPTIONS = ASSET_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: ASSET_LIFECYCLE_STAGE_LABELS[value] }));

export interface Asset {
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
  assetTag: string;
  assetClass: string;
  acquiredOn?: string;
  purchasePrice?: number;
  currentValue?: number;
  custodian?: string;
  labels?: string;
  lifecycleStage?: AssetLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface AssetInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  assetTag: string;
  assetClass: string;
  acquiredOn?: string;
  purchasePrice?: number;
  currentValue?: number;
  custodian?: string;
  labels?: string;
  lifecycleStage?: AssetLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type AssetFormValues = Partial<AssetInput>;

export interface AssetUpdateInput extends Partial<AssetInput> {
  expectedVersion?: number;
}

export interface AssetFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  assetTag?: string;
  assetClass?: string;
  acquiredOnFrom?: string;
  acquiredOnTo?: string;
  purchasePriceMin?: number;
  purchasePriceMax?: number;
  currentValueMin?: number;
  currentValueMax?: number;
  custodian?: string;
  labels?: string;
  lifecycleStage?: AssetLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type AssetSort = SortState<keyof Asset & string>;

export interface AssetSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ASSET_PATH = '/api/v1/assets';
export const ASSET_TITLE = 'Asset';
export const ASSET_TITLE_PLURAL = 'Assets';

export const ASSET_DEFAULTS: AssetFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  assetTag: '',
  assetClass: '',
};
