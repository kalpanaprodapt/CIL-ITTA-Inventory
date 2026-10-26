import type { LifecycleStatus, SortState } from '@/shared/types';

export const SITE_SITE_TYPE_VALUES = ['CO', 'POP', 'DATACENTER', 'HUT', 'CELL_SITE', 'CUSTOMER_PREMISE'] as const;
export type SiteSiteType = (typeof SITE_SITE_TYPE_VALUES)[number];
export const SITE_SITE_TYPE_LABELS: Record<SiteSiteType, string> = {
  CO: 'Co',
  POP: 'Pop',
  DATACENTER: 'Datacenter',
  HUT: 'Hut',
  CELL_SITE: 'Cell Site',
  CUSTOMER_PREMISE: 'Customer Premise',
};
export const SITE_SITE_TYPE_OPTIONS = SITE_SITE_TYPE_VALUES.map((value) => ({ value, label: SITE_SITE_TYPE_LABELS[value] }));

export const SITE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type SiteLifecycleStage = (typeof SITE_LIFECYCLE_STAGE_VALUES)[number];
export const SITE_LIFECYCLE_STAGE_LABELS: Record<SiteLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const SITE_LIFECYCLE_STAGE_OPTIONS = SITE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: SITE_LIFECYCLE_STAGE_LABELS[value] }));

export interface Site {
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
  cityId: string;
  siteType?: SiteSiteType;
  address?: string;
  latitude?: number;
  longitude?: number;
  powerKw?: number;
  accessHours?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: SiteLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface SiteInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  cityId: string;
  siteType?: SiteSiteType;
  address?: string;
  latitude?: number;
  longitude?: number;
  powerKw?: number;
  accessHours?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: SiteLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type SiteFormValues = Partial<SiteInput>;

export interface SiteUpdateInput extends Partial<SiteInput> {
  expectedVersion?: number;
}

export interface SiteFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  cityId?: string;
  siteType?: SiteSiteType[];
  address?: string;
  latitudeMin?: number;
  latitudeMax?: number;
  longitudeMin?: number;
  longitudeMax?: number;
  powerKwMin?: number;
  powerKwMax?: number;
  accessHours?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: SiteLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type SiteSort = SortState<keyof Site & string>;

export interface SiteSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SITE_PATH = '/api/v1/sites';
export const SITE_TITLE = 'Site';
export const SITE_TITLE_PLURAL = 'Sitees';

export const SITE_DEFAULTS: SiteFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
