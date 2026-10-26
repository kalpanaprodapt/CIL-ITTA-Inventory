import type { LifecycleStatus, SortState } from '@/shared/types';

export const IP_ADDRESS_ASSIGNMENT_TYPE_VALUES = ['STATIC', 'DHCP', 'RESERVED', 'ANYCAST'] as const;
export type IpAddressAssignmentType = (typeof IP_ADDRESS_ASSIGNMENT_TYPE_VALUES)[number];
export const IP_ADDRESS_ASSIGNMENT_TYPE_LABELS: Record<IpAddressAssignmentType, string> = {
  STATIC: 'Static',
  DHCP: 'Dhcp',
  RESERVED: 'Reserved',
  ANYCAST: 'Anycast',
};
export const IP_ADDRESS_ASSIGNMENT_TYPE_OPTIONS = IP_ADDRESS_ASSIGNMENT_TYPE_VALUES.map((value) => ({ value, label: IP_ADDRESS_ASSIGNMENT_TYPE_LABELS[value] }));

export interface IpAddress {
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
  subnetId: string;
  address: string;
  assignedToId?: string;
  assignmentType?: IpAddressAssignmentType;
  dnsName?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface IpAddressInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  subnetId: string;
  address: string;
  assignedToId?: string;
  assignmentType?: IpAddressAssignmentType;
  dnsName?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type IpAddressFormValues = Partial<IpAddressInput>;

export interface IpAddressUpdateInput extends Partial<IpAddressInput> {
  expectedVersion?: number;
}

export interface IpAddressFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  subnetId?: string;
  address?: string;
  assignedToId?: string;
  assignmentType?: IpAddressAssignmentType[];
  dnsName?: string;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type IpAddressSort = SortState<keyof IpAddress & string>;

export interface IpAddressSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const IPADDRESS_PATH = '/api/v1/ip-addresses';
export const IPADDRESS_TITLE = 'Ip Address';
export const IPADDRESS_TITLE_PLURAL = 'Ip Addresses';

export const IPADDRESS_DEFAULTS: IpAddressFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  address: '',
};
