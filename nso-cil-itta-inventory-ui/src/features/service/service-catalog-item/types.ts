import type { LifecycleStatus, SortState } from '@/shared/types';

export const SERVICE_CATALOG_ITEM_FAMILY_VALUES = ['CONNECTIVITY', 'SECURITY', 'VOICE', 'CLOUD', 'MANAGED'] as const;
export type ServiceCatalogItemFamily = (typeof SERVICE_CATALOG_ITEM_FAMILY_VALUES)[number];
export const SERVICE_CATALOG_ITEM_FAMILY_LABELS: Record<ServiceCatalogItemFamily, string> = {
  CONNECTIVITY: 'Connectivity',
  SECURITY: 'Security',
  VOICE: 'Voice',
  CLOUD: 'Cloud',
  MANAGED: 'Managed',
};
export const SERVICE_CATALOG_ITEM_FAMILY_OPTIONS = SERVICE_CATALOG_ITEM_FAMILY_VALUES.map((value) => ({ value, label: SERVICE_CATALOG_ITEM_FAMILY_LABELS[value] }));

export const SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ServiceCatalogItemLifecycleStage = (typeof SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_VALUES)[number];
export const SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_LABELS: Record<ServiceCatalogItemLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_OPTIONS = SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: SERVICE_CATALOG_ITEM_LIFECYCLE_STAGE_LABELS[value] }));

export interface ServiceCatalogItem {
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
  itemName: string;
  family?: ServiceCatalogItemFamily;
  basePrice?: number;
  billingUnit?: string;
  orderable?: boolean;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ServiceCatalogItemLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export interface ServiceCatalogItemInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  itemName: string;
  family?: ServiceCatalogItemFamily;
  basePrice?: number;
  billingUnit?: string;
  orderable?: boolean;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ServiceCatalogItemLifecycleStage;
  sourceSystem?: string;
  region?: string;
}

export type ServiceCatalogItemFormValues = Partial<ServiceCatalogItemInput>;

export interface ServiceCatalogItemUpdateInput extends Partial<ServiceCatalogItemInput> {
  expectedVersion?: number;
}

export interface ServiceCatalogItemFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  itemName?: string;
  family?: ServiceCatalogItemFamily[];
  basePriceMin?: number;
  basePriceMax?: number;
  billingUnit?: string;
  orderable?: boolean;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: ServiceCatalogItemLifecycleStage[];
  sourceSystem?: string;
  region?: string;
}

export type ServiceCatalogItemSort = SortState<keyof ServiceCatalogItem & string>;

export interface ServiceCatalogItemSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SERVICECATALOGITEM_PATH = '/api/v1/service-catalog-items';
export const SERVICECATALOGITEM_TITLE = 'Service Catalog Item';
export const SERVICECATALOGITEM_TITLE_PLURAL = 'Service Catalog Items';

export const SERVICECATALOGITEM_DEFAULTS: ServiceCatalogItemFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  itemName: '',
  orderable: false,
};
