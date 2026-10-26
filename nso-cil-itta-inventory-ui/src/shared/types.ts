export const LIFECYCLE_STATUSES = ['PLANNED', 'ACTIVE', 'MAINTENANCE', 'DECOMMISSIONED'] as const;
export type LifecycleStatus = (typeof LIFECYCLE_STATUSES)[number];

export const LIFECYCLE_LABELS: Record<LifecycleStatus, string> = {
  PLANNED: 'Planned',
  ACTIVE: 'Active',
  MAINTENANCE: 'Maintenance',
  DECOMMISSIONED: 'Decommissioned',
};

export const LIFECYCLE_OPTIONS = LIFECYCLE_STATUSES.map((value) => ({ value, label: LIFECYCLE_LABELS[value] }));

export interface Page<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface SortState<K extends string = string> {
  field: K;
  direction: 'asc' | 'desc';
}

export interface FieldMeta {
  name: string;
  label: string;
  type: string;
  required: boolean;
  maxLength?: number;
  enumValues: string[];
  filterable: boolean;
  sortable: boolean;
}

export interface EntityMeta {
  entity: string;
  title: string;
  domain: string;
  path: string;
  fields: FieldMeta[];
}
