import type { LifecycleStatus, SortState } from '@/shared/types';

export const DISCOVERY_PROFILE_PROTOCOL_VALUES = ['SNMP', 'SSH', 'NETCONF', 'GNMI', 'REST'] as const;
export type DiscoveryProfileProtocol = (typeof DISCOVERY_PROFILE_PROTOCOL_VALUES)[number];
export const DISCOVERY_PROFILE_PROTOCOL_LABELS: Record<DiscoveryProfileProtocol, string> = {
  SNMP: 'Snmp',
  SSH: 'Ssh',
  NETCONF: 'Netconf',
  GNMI: 'Gnmi',
  REST: 'Rest',
};
export const DISCOVERY_PROFILE_PROTOCOL_OPTIONS = DISCOVERY_PROFILE_PROTOCOL_VALUES.map((value) => ({ value, label: DISCOVERY_PROFILE_PROTOCOL_LABELS[value] }));

export interface DiscoveryProfile {
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
  profileName: string;
  protocol?: DiscoveryProfileProtocol;
  credentialRef?: string;
  scopeCidr?: string;
  scheduleCron?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export interface DiscoveryProfileInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  profileName: string;
  protocol?: DiscoveryProfileProtocol;
  credentialRef?: string;
  scopeCidr?: string;
  scheduleCron?: string;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
  labels?: string;
}

export type DiscoveryProfileFormValues = Partial<DiscoveryProfileInput>;

export interface DiscoveryProfileUpdateInput extends Partial<DiscoveryProfileInput> {
  expectedVersion?: number;
}

export interface DiscoveryProfileFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  profileName?: string;
  protocol?: DiscoveryProfileProtocol[];
  credentialRef?: string;
  scopeCidr?: string;
  scheduleCron?: string;
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
  labels?: string;
}

export type DiscoveryProfileSort = SortState<keyof DiscoveryProfile & string>;

export interface DiscoveryProfileSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const DISCOVERYPROFILE_PATH = '/api/v1/discovery-profiles';
export const DISCOVERYPROFILE_TITLE = 'Discovery Profile';
export const DISCOVERYPROFILE_TITLE_PLURAL = 'Discovery Profilees';

export const DISCOVERYPROFILE_DEFAULTS: DiscoveryProfileFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  profileName: '',
};
