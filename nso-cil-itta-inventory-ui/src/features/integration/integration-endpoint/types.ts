import type { LifecycleStatus, SortState } from '@/shared/types';

export const INTEGRATION_ENDPOINT_AUTH_TYPE_VALUES = ['BASIC', 'OAUTH2', 'MTLS', 'API_KEY'] as const;
export type IntegrationEndpointAuthType = (typeof INTEGRATION_ENDPOINT_AUTH_TYPE_VALUES)[number];
export const INTEGRATION_ENDPOINT_AUTH_TYPE_LABELS: Record<IntegrationEndpointAuthType, string> = {
  BASIC: 'Basic',
  OAUTH2: 'Oauth2',
  MTLS: 'Mtls',
  API_KEY: 'Api Key',
};
export const INTEGRATION_ENDPOINT_AUTH_TYPE_OPTIONS = INTEGRATION_ENDPOINT_AUTH_TYPE_VALUES.map((value) => ({ value, label: INTEGRATION_ENDPOINT_AUTH_TYPE_LABELS[value] }));

export const INTEGRATION_ENDPOINT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type IntegrationEndpointCriticality = (typeof INTEGRATION_ENDPOINT_CRITICALITY_VALUES)[number];
export const INTEGRATION_ENDPOINT_CRITICALITY_LABELS: Record<IntegrationEndpointCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const INTEGRATION_ENDPOINT_CRITICALITY_OPTIONS = INTEGRATION_ENDPOINT_CRITICALITY_VALUES.map((value) => ({ value, label: INTEGRATION_ENDPOINT_CRITICALITY_LABELS[value] }));

export interface IntegrationEndpoint {
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
  endpointName: string;
  targetSystem: string;
  baseUrl: string;
  authType?: IntegrationEndpointAuthType;
  timeoutMs?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: IntegrationEndpointCriticality;
  costCenter?: string;
}

export interface IntegrationEndpointInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  endpointName: string;
  targetSystem: string;
  baseUrl: string;
  authType?: IntegrationEndpointAuthType;
  timeoutMs?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: IntegrationEndpointCriticality;
  costCenter?: string;
}

export type IntegrationEndpointFormValues = Partial<IntegrationEndpointInput>;

export interface IntegrationEndpointUpdateInput extends Partial<IntegrationEndpointInput> {
  expectedVersion?: number;
}

export interface IntegrationEndpointFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  endpointName?: string;
  targetSystem?: string;
  baseUrl?: string;
  authType?: IntegrationEndpointAuthType[];
  timeoutMsMin?: number;
  timeoutMsMax?: number;
  sourceSystem?: string;
  region?: string;
  owner?: string;
  criticality?: IntegrationEndpointCriticality[];
  costCenter?: string;
}

export type IntegrationEndpointSort = SortState<keyof IntegrationEndpoint & string>;

export interface IntegrationEndpointSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const INTEGRATIONENDPOINT_PATH = '/api/v1/integration-endpoints';
export const INTEGRATIONENDPOINT_TITLE = 'Integration Endpoint';
export const INTEGRATIONENDPOINT_TITLE_PLURAL = 'Integration Endpoints';

export const INTEGRATIONENDPOINT_DEFAULTS: IntegrationEndpointFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  endpointName: '',
  targetSystem: '',
  baseUrl: '',
};
