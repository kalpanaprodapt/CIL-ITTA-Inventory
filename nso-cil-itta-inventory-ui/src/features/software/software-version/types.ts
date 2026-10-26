import type { LifecycleStatus, SortState } from '@/shared/types';

export const SOFTWARE_VERSION_RELEASE_TRAIN_VALUES = ['GA', 'MAINTENANCE', 'EARLY', 'DEFERRED'] as const;
export type SoftwareVersionReleaseTrain = (typeof SOFTWARE_VERSION_RELEASE_TRAIN_VALUES)[number];
export const SOFTWARE_VERSION_RELEASE_TRAIN_LABELS: Record<SoftwareVersionReleaseTrain, string> = {
  GA: 'Ga',
  MAINTENANCE: 'Maintenance',
  EARLY: 'Early',
  DEFERRED: 'Deferred',
};
export const SOFTWARE_VERSION_RELEASE_TRAIN_OPTIONS = SOFTWARE_VERSION_RELEASE_TRAIN_VALUES.map((value) => ({ value, label: SOFTWARE_VERSION_RELEASE_TRAIN_LABELS[value] }));

export const SOFTWARE_VERSION_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type SoftwareVersionCriticality = (typeof SOFTWARE_VERSION_CRITICALITY_VALUES)[number];
export const SOFTWARE_VERSION_CRITICALITY_LABELS: Record<SoftwareVersionCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const SOFTWARE_VERSION_CRITICALITY_OPTIONS = SOFTWARE_VERSION_CRITICALITY_VALUES.map((value) => ({ value, label: SOFTWARE_VERSION_CRITICALITY_LABELS[value] }));

export interface SoftwareVersion {
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
  imageId: string;
  versionString: string;
  releaseDate?: string;
  endOfSupport?: string;
  releaseTrain?: SoftwareVersionReleaseTrain;
  cveCount?: number;
  region?: string;
  owner?: string;
  criticality?: SoftwareVersionCriticality;
  costCenter?: string;
  notes?: string;
}

export interface SoftwareVersionInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  imageId: string;
  versionString: string;
  releaseDate?: string;
  endOfSupport?: string;
  releaseTrain?: SoftwareVersionReleaseTrain;
  cveCount?: number;
  region?: string;
  owner?: string;
  criticality?: SoftwareVersionCriticality;
  costCenter?: string;
  notes?: string;
}

export type SoftwareVersionFormValues = Partial<SoftwareVersionInput>;

export interface SoftwareVersionUpdateInput extends Partial<SoftwareVersionInput> {
  expectedVersion?: number;
}

export interface SoftwareVersionFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  imageId?: string;
  versionString?: string;
  releaseDateFrom?: string;
  releaseDateTo?: string;
  endOfSupportFrom?: string;
  endOfSupportTo?: string;
  releaseTrain?: SoftwareVersionReleaseTrain[];
  cveCountMin?: number;
  cveCountMax?: number;
  region?: string;
  owner?: string;
  criticality?: SoftwareVersionCriticality[];
  costCenter?: string;
}

export type SoftwareVersionSort = SortState<keyof SoftwareVersion & string>;

export interface SoftwareVersionSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SOFTWAREVERSION_PATH = '/api/v1/software-versions';
export const SOFTWAREVERSION_TITLE = 'Software Version';
export const SOFTWAREVERSION_TITLE_PLURAL = 'Software Versions';

export const SOFTWAREVERSION_DEFAULTS: SoftwareVersionFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  versionString: '',
};
