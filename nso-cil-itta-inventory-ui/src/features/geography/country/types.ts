import type { LifecycleStatus, SortState } from '@/shared/types';

export const COUNTRY_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type CountryCriticality = (typeof COUNTRY_CRITICALITY_VALUES)[number];
export const COUNTRY_CRITICALITY_LABELS: Record<CountryCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const COUNTRY_CRITICALITY_OPTIONS = COUNTRY_CRITICALITY_VALUES.map((value) => ({ value, label: COUNTRY_CRITICALITY_LABELS[value] }));

export interface Country {
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
  isoCode: string;
  isoNumeric?: number;
  dialCode?: string;
  currency?: string;
  regulatoryBody?: string;
  timezone?: string;
  region?: string;
  owner?: string;
  criticality?: CountryCriticality;
  costCenter?: string;
  notes?: string;
}

export interface CountryInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  isoCode: string;
  isoNumeric?: number;
  dialCode?: string;
  currency?: string;
  regulatoryBody?: string;
  timezone?: string;
  region?: string;
  owner?: string;
  criticality?: CountryCriticality;
  costCenter?: string;
  notes?: string;
}

export type CountryFormValues = Partial<CountryInput>;

export interface CountryUpdateInput extends Partial<CountryInput> {
  expectedVersion?: number;
}

export interface CountryFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  isoCode?: string;
  isoNumericMin?: number;
  isoNumericMax?: number;
  dialCode?: string;
  currency?: string;
  regulatoryBody?: string;
  timezone?: string;
  region?: string;
  owner?: string;
  criticality?: CountryCriticality[];
  costCenter?: string;
}

export type CountrySort = SortState<keyof Country & string>;

export interface CountrySummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const COUNTRY_PATH = '/api/v1/countries';
export const COUNTRY_TITLE = 'Country';
export const COUNTRY_TITLE_PLURAL = 'Countryes';

export const COUNTRY_DEFAULTS: CountryFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  isoCode: '',
};
