import type { LifecycleStatus, SortState } from '@/shared/types';

export const SERVICE_ENDPOINT_ENDPOINT_ROLE_VALUES = ['HUB', 'SPOKE', 'PEER'] as const;
export type ServiceEndpointEndpointRole = (typeof SERVICE_ENDPOINT_ENDPOINT_ROLE_VALUES)[number];
export const SERVICE_ENDPOINT_ENDPOINT_ROLE_LABELS: Record<ServiceEndpointEndpointRole, string> = {
  HUB: 'Hub',
  SPOKE: 'Spoke',
  PEER: 'Peer',
};
export const SERVICE_ENDPOINT_ENDPOINT_ROLE_OPTIONS = SERVICE_ENDPOINT_ENDPOINT_ROLE_VALUES.map((value) => ({ value, label: SERVICE_ENDPOINT_ENDPOINT_ROLE_LABELS[value] }));

export const SERVICE_ENDPOINT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type ServiceEndpointLifecycleStage = (typeof SERVICE_ENDPOINT_LIFECYCLE_STAGE_VALUES)[number];
export const SERVICE_ENDPOINT_LIFECYCLE_STAGE_LABELS: Record<ServiceEndpointLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const SERVICE_ENDPOINT_LIFECYCLE_STAGE_OPTIONS = SERVICE_ENDPOINT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: SERVICE_ENDPOINT_LIFECYCLE_STAGE_LABELS[value] }));

export interface ServiceEndpoint {
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
  serviceId: string;
  siteId?: string;
  portId?: string;
  endpointRole?: ServiceEndpointEndpointRole;
  handoff?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ServiceEndpointLifecycleStage;
}

export interface ServiceEndpointInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  serviceId: string;
  siteId?: string;
  portId?: string;
  endpointRole?: ServiceEndpointEndpointRole;
  handoff?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: ServiceEndpointLifecycleStage;
}

export type ServiceEndpointFormValues = Partial<ServiceEndpointInput>;

export interface ServiceEndpointUpdateInput extends Partial<ServiceEndpointInput> {
  expectedVersion?: number;
}

export interface ServiceEndpointFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  serviceId?: string;
  siteId?: string;
  portId?: string;
  endpointRole?: ServiceEndpointEndpointRole[];
  handoff?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: ServiceEndpointLifecycleStage[];
}

export type ServiceEndpointSort = SortState<keyof ServiceEndpoint & string>;

export interface ServiceEndpointSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const SERVICEENDPOINT_PATH = '/api/v1/service-endpoints';
export const SERVICEENDPOINT_TITLE = 'Service Endpoint';
export const SERVICEENDPOINT_TITLE_PLURAL = 'Service Endpoints';

export const SERVICEENDPOINT_DEFAULTS: ServiceEndpointFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
};
