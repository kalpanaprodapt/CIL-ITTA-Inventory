import type { LifecycleStatus, SortState } from '@/shared/types';

export const ASSET_TAG_TAG_TYPE_VALUES = ['BARCODE', 'RFID', 'QR'] as const;
export type AssetTagTagType = (typeof ASSET_TAG_TAG_TYPE_VALUES)[number];
export const ASSET_TAG_TAG_TYPE_LABELS: Record<AssetTagTagType, string> = {
  BARCODE: 'Barcode',
  RFID: 'Rfid',
  QR: 'Qr',
};
export const ASSET_TAG_TAG_TYPE_OPTIONS = ASSET_TAG_TAG_TYPE_VALUES.map((value) => ({ value, label: ASSET_TAG_TAG_TYPE_LABELS[value] }));

export const ASSET_TAG_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type AssetTagCriticality = (typeof ASSET_TAG_CRITICALITY_VALUES)[number];
export const ASSET_TAG_CRITICALITY_LABELS: Record<AssetTagCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const ASSET_TAG_CRITICALITY_OPTIONS = ASSET_TAG_CRITICALITY_VALUES.map((value) => ({ value, label: ASSET_TAG_CRITICALITY_LABELS[value] }));

export interface AssetTag {
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
  assetId: string;
  tagValue: string;
  tagType?: AssetTagTagType;
  printedOn?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AssetTagCriticality;
  costCenter?: string;
}

export interface AssetTagInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  assetId: string;
  tagValue: string;
  tagType?: AssetTagTagType;
  printedOn?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AssetTagCriticality;
  costCenter?: string;
}

export type AssetTagFormValues = Partial<AssetTagInput>;

export interface AssetTagUpdateInput extends Partial<AssetTagInput> {
  expectedVersion?: number;
}

export interface AssetTagFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  assetId?: string;
  tagValue?: string;
  tagType?: AssetTagTagType[];
  printedOnFrom?: string;
  printedOnTo?: string;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: AssetTagCriticality[];
  costCenter?: string;
}

export type AssetTagSort = SortState<keyof AssetTag & string>;

export interface AssetTagSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const ASSETTAG_PATH = '/api/v1/asset-tags';
export const ASSETTAG_TITLE = 'Asset Tag';
export const ASSETTAG_TITLE_PLURAL = 'Asset Tags';

export const ASSETTAG_DEFAULTS: AssetTagFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  tagValue: '',
};
