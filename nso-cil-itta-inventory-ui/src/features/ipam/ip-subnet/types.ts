import type { LifecycleStatus, SortState } from '@/shared/types';

export const IP_SUBNET_IP_VERSION_VALUES = ['V4', 'V6'] as const;
export type IpSubnetIpVersion = (typeof IP_SUBNET_IP_VERSION_VALUES)[number];
export const IP_SUBNET_IP_VERSION_LABELS: Record<IpSubnetIpVersion, string> = {
  V4: 'V4',
  V6: 'V6',
};
export const IP_SUBNET_IP_VERSION_OPTIONS = IP_SUBNET_IP_VERSION_VALUES.map((value) => ({ value, label: IP_SUBNET_IP_VERSION_LABELS[value] }));

export const IP_SUBNET_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type IpSubnetCriticality = (typeof IP_SUBNET_CRITICALITY_VALUES)[number];
export const IP_SUBNET_CRITICALITY_LABELS: Record<IpSubnetCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const IP_SUBNET_CRITICALITY_OPTIONS = IP_SUBNET_CRITICALITY_VALUES.map((value) => ({ value, label: IP_SUBNET_CRITICALITY_LABELS[value] }));

export interface IpSubnet {
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
  cidr: string;
  ipVersion?: IpSubnetIpVersion;
  vrfId?: string;
  gateway?: string;
  vlanId?: string;
  utilizationPct?: number;
  owner?: string;
  criticality?: IpSubnetCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface IpSubnetInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  cidr: string;
  ipVersion?: IpSubnetIpVersion;
  vrfId?: string;
  gateway?: string;
  vlanId?: string;
  utilizationPct?: number;
  owner?: string;
  criticality?: IpSubnetCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type IpSubnetFormValues = Partial<IpSubnetInput>;

export interface IpSubnetUpdateInput extends Partial<IpSubnetInput> {
  expectedVersion?: number;
}

export interface IpSubnetFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  cidr?: string;
  ipVersion?: IpSubnetIpVersion[];
  vrfId?: string;
  gateway?: string;
  vlanId?: string;
  utilizationPctMin?: number;
  utilizationPctMax?: number;
  owner?: string;
  criticality?: IpSubnetCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type IpSubnetSort = SortState<keyof IpSubnet & string>;

export interface IpSubnetSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const IPSUBNET_PATH = '/api/v1/ip-subnets';
export const IPSUBNET_TITLE = 'Ip Subnet';
export const IPSUBNET_TITLE_PLURAL = 'Ip Subnets';

export const IPSUBNET_DEFAULTS: IpSubnetFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  cidr: '',
};
