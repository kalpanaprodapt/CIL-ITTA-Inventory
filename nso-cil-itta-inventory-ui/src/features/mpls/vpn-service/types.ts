import type { LifecycleStatus, SortState } from '@/shared/types';

export const VPN_SERVICE_VPN_TYPE_VALUES = ['L3VPN', 'L2VPN', 'VPLS', 'EVPN'] as const;
export type VpnServiceVpnType = (typeof VPN_SERVICE_VPN_TYPE_VALUES)[number];
export const VPN_SERVICE_VPN_TYPE_LABELS: Record<VpnServiceVpnType, string> = {
  L3VPN: 'L3Vpn',
  L2VPN: 'L2Vpn',
  VPLS: 'Vpls',
  EVPN: 'Evpn',
};
export const VPN_SERVICE_VPN_TYPE_OPTIONS = VPN_SERVICE_VPN_TYPE_VALUES.map((value) => ({ value, label: VPN_SERVICE_VPN_TYPE_LABELS[value] }));

export const VPN_SERVICE_TOPOLOGY_VALUES = ['MESH', 'HUB_SPOKE', 'POINT_TO_POINT'] as const;
export type VpnServiceTopology = (typeof VPN_SERVICE_TOPOLOGY_VALUES)[number];
export const VPN_SERVICE_TOPOLOGY_LABELS: Record<VpnServiceTopology, string> = {
  MESH: 'Mesh',
  HUB_SPOKE: 'Hub Spoke',
  POINT_TO_POINT: 'Point To Point',
};
export const VPN_SERVICE_TOPOLOGY_OPTIONS = VPN_SERVICE_TOPOLOGY_VALUES.map((value) => ({ value, label: VPN_SERVICE_TOPOLOGY_LABELS[value] }));

export const VPN_SERVICE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type VpnServiceCriticality = (typeof VPN_SERVICE_CRITICALITY_VALUES)[number];
export const VPN_SERVICE_CRITICALITY_LABELS: Record<VpnServiceCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const VPN_SERVICE_CRITICALITY_OPTIONS = VPN_SERVICE_CRITICALITY_VALUES.map((value) => ({ value, label: VPN_SERVICE_CRITICALITY_LABELS[value] }));

export interface VpnService {
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
  vpnName: string;
  vpnType?: VpnServiceVpnType;
  vrfId?: string;
  customerId?: string;
  topology?: VpnServiceTopology;
  owner?: string;
  criticality?: VpnServiceCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface VpnServiceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  vpnName: string;
  vpnType?: VpnServiceVpnType;
  vrfId?: string;
  customerId?: string;
  topology?: VpnServiceTopology;
  owner?: string;
  criticality?: VpnServiceCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type VpnServiceFormValues = Partial<VpnServiceInput>;

export interface VpnServiceUpdateInput extends Partial<VpnServiceInput> {
  expectedVersion?: number;
}

export interface VpnServiceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  vpnName?: string;
  vpnType?: VpnServiceVpnType[];
  vrfId?: string;
  customerId?: string;
  topology?: VpnServiceTopology[];
  owner?: string;
  criticality?: VpnServiceCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type VpnServiceSort = SortState<keyof VpnService & string>;

export interface VpnServiceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const VPNSERVICE_PATH = '/api/v1/vpn-services';
export const VPNSERVICE_TITLE = 'Vpn Service';
export const VPNSERVICE_TITLE_PLURAL = 'Vpn Servicees';

export const VPNSERVICE_DEFAULTS: VpnServiceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  vpnName: '',
};
