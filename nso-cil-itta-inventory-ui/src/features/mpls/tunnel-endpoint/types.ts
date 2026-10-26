import type { LifecycleStatus, SortState } from '@/shared/types';

export const TUNNEL_ENDPOINT_TUNNEL_TYPE_VALUES = ['GRE', 'IPSEC', 'VXLAN', 'SRV6'] as const;
export type TunnelEndpointTunnelType = (typeof TUNNEL_ENDPOINT_TUNNEL_TYPE_VALUES)[number];
export const TUNNEL_ENDPOINT_TUNNEL_TYPE_LABELS: Record<TunnelEndpointTunnelType, string> = {
  GRE: 'Gre',
  IPSEC: 'Ipsec',
  VXLAN: 'Vxlan',
  SRV6: 'Srv6',
};
export const TUNNEL_ENDPOINT_TUNNEL_TYPE_OPTIONS = TUNNEL_ENDPOINT_TUNNEL_TYPE_VALUES.map((value) => ({ value, label: TUNNEL_ENDPOINT_TUNNEL_TYPE_LABELS[value] }));

export const TUNNEL_ENDPOINT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type TunnelEndpointLifecycleStage = (typeof TUNNEL_ENDPOINT_LIFECYCLE_STAGE_VALUES)[number];
export const TUNNEL_ENDPOINT_LIFECYCLE_STAGE_LABELS: Record<TunnelEndpointLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const TUNNEL_ENDPOINT_LIFECYCLE_STAGE_OPTIONS = TUNNEL_ENDPOINT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: TUNNEL_ENDPOINT_LIFECYCLE_STAGE_LABELS[value] }));

export interface TunnelEndpoint {
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
  deviceId: string;
  tunnelName: string;
  tunnelType?: TunnelEndpointTunnelType;
  sourceIp?: string;
  destinationIp?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: TunnelEndpointLifecycleStage;
  sourceSystem?: string;
}

export interface TunnelEndpointInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  deviceId: string;
  tunnelName: string;
  tunnelType?: TunnelEndpointTunnelType;
  sourceIp?: string;
  destinationIp?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
  lifecycleStage?: TunnelEndpointLifecycleStage;
  sourceSystem?: string;
}

export type TunnelEndpointFormValues = Partial<TunnelEndpointInput>;

export interface TunnelEndpointUpdateInput extends Partial<TunnelEndpointInput> {
  expectedVersion?: number;
}

export interface TunnelEndpointFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  deviceId?: string;
  tunnelName?: string;
  tunnelType?: TunnelEndpointTunnelType[];
  sourceIp?: string;
  destinationIp?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
  lifecycleStage?: TunnelEndpointLifecycleStage[];
  sourceSystem?: string;
}

export type TunnelEndpointSort = SortState<keyof TunnelEndpoint & string>;

export interface TunnelEndpointSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const TUNNELENDPOINT_PATH = '/api/v1/tunnel-endpoints';
export const TUNNELENDPOINT_TITLE = 'Tunnel Endpoint';
export const TUNNELENDPOINT_TITLE_PLURAL = 'Tunnel Endpoints';

export const TUNNELENDPOINT_DEFAULTS: TunnelEndpointFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  tunnelName: '',
};
