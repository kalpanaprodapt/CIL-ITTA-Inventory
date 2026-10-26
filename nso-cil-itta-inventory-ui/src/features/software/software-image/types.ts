import type { LifecycleStatus, SortState } from '@/shared/types';

export const SOFTWARE_IMAGE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type SoftwareImageLifecycleStage = (typeof SOFTWARE_IMAGE_LIFECYCLE_STAGE_VALUES)[number];
export const SOFTWARE_IMAGE_LIFECYCLE_STAGE_LABELS: Record<SoftwareImageLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const SOFTWARE_IMAGE_LIFECYCLE_STAGE_OPTIONS = SOFTWARE_IMAGE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: SOFTWARE_IMAGE_LIFECYCLE_STAGE_LABELS[value] }));

export const SOFTWARE_IMAGE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SoftwareImageCriticality = (typeof SOFTWARE_IMAGE_CRITICALITY_VALUES)[number];
export const SOFTWARE_IMAGE_CRITICALITY_LABELS: Record<SoftwareImageCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SOFTWARE_IMAGE_CRITICALITY_OPTIONS = SOFTWARE_IMAGE_CRITICALITY_VALUES.map((value) => ({ value, label: SOFTWARE_IMAGE_CRITICALITY_LABELS[value] }));

export interface SoftwareImage {
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
  vendorId: string;
  imageName: string;
  sizeMb?: number;
  checksum?: string;
  platform?: string;
  storagePath?: string;
  lifecycleStage?: SoftwareImageLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SoftwareImageCriticality;
}

export interface SoftwareImageInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vendorId: string;
  imageName: string;
  sizeMb?: number;
  checksum?: string;
  platform?: string;
  storagePath?: string;
  lifecycleStage?: SoftwareImageLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SoftwareImageCriticality;
}

export type SoftwareImageFormValues = Partial<SoftwareImageInput>;

export interface SoftwareImageUpdateInput extends Partial<SoftwareImageInput> {
  expectedVersion?: number;
}

export interface SoftwareImageFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vendorId?: string;
  imageName?: string;
  sizeMbMin?: number;
  sizeMbMax?: number;
  checksum?: string;
  platform?: string;
  storagePath?: string;
  lifecycleStage?: SoftwareImageLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: SoftwareImageCriticality[];
}

export type SoftwareImageSort = SortState<keyof SoftwareImage & string>;

export interface SoftwareImageSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SOFTWAREIMAGE_PATH = '/api/v1/software-images';
export const SOFTWAREIMAGE_TITLE = 'Software Image';
export const SOFTWAREIMAGE_TITLE_PLURAL = 'Software Imagees';

export const SOFTWAREIMAGE_DEFAULTS: SoftwareImageFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  imageName: '',
};
