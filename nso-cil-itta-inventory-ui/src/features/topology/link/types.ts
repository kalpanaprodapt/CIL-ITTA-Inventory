import type { LifecycleStatus, SortState } from '@/shared/types';

export const LINK_LINK_TYPE_VALUES = ['PHYSICAL', 'LOGICAL', 'VIRTUAL'] as const;
export type LinkLinkType = (typeof LINK_LINK_TYPE_VALUES)[number];
export const LINK_LINK_TYPE_LABELS: Record<LinkLinkType, string> = {
  PHYSICAL: 'Physical',
  LOGICAL: 'Logical',
  VIRTUAL: 'Virtual',
};
export const LINK_LINK_TYPE_OPTIONS = LINK_LINK_TYPE_VALUES.map((value) => ({ value, label: LINK_LINK_TYPE_LABELS[value] }));

export const LINK_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type LinkCriticality = (typeof LINK_CRITICALITY_VALUES)[number];
export const LINK_CRITICALITY_LABELS: Record<LinkCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const LINK_CRITICALITY_OPTIONS = LINK_CRITICALITY_VALUES.map((value) => ({ value, label: LINK_CRITICALITY_LABELS[value] }));

export interface Link {
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
  aInterfaceId: string;
  zInterfaceId: string;
  linkType?: LinkLinkType;
  speedMbps?: number;
  discoveredBy?: string;
  region?: string;
  owner?: string;
  criticality?: LinkCriticality;
  costCenter?: string;
  notes?: string;
}

export interface LinkInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  aInterfaceId: string;
  zInterfaceId: string;
  linkType?: LinkLinkType;
  speedMbps?: number;
  discoveredBy?: string;
  region?: string;
  owner?: string;
  criticality?: LinkCriticality;
  costCenter?: string;
  notes?: string;
}

export type LinkFormValues = Partial<LinkInput>;

export interface LinkUpdateInput extends Partial<LinkInput> {
  expectedVersion?: number;
}

export interface LinkFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  aInterfaceId?: string;
  zInterfaceId?: string;
  linkType?: LinkLinkType[];
  speedMbpsMin?: number;
  speedMbpsMax?: number;
  discoveredBy?: string;
  region?: string;
  owner?: string;
  criticality?: LinkCriticality[];
  costCenter?: string;
}

export type LinkSort = SortState<keyof Link & string>;

export interface LinkSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const LINK_PATH = '/api/v1/links';
export const LINK_TITLE = 'Link';
export const LINK_TITLE_PLURAL = 'Links';

export const LINK_DEFAULTS: LinkFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
