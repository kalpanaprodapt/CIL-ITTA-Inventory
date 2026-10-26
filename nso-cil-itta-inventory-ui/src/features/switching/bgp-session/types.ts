import type { LifecycleStatus, SortState } from '@/shared/types';

export const BGP_SESSION_SESSION_STATE_VALUES = ['IDLE', 'CONNECT', 'ACTIVE', 'OPENSENT', 'ESTABLISHED'] as const;
export type BgpSessionSessionState = (typeof BGP_SESSION_SESSION_STATE_VALUES)[number];
export const BGP_SESSION_SESSION_STATE_LABELS: Record<BgpSessionSessionState, string> = {
  IDLE: 'Idle',
  CONNECT: 'Connect',
  ACTIVE: 'Active',
  OPENSENT: 'Opensent',
  ESTABLISHED: 'Established',
};
export const BGP_SESSION_SESSION_STATE_OPTIONS = BGP_SESSION_SESSION_STATE_VALUES.map((value) => ({ value, label: BGP_SESSION_SESSION_STATE_LABELS[value] }));

export const BGP_SESSION_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type BgpSessionCriticality = (typeof BGP_SESSION_CRITICALITY_VALUES)[number];
export const BGP_SESSION_CRITICALITY_LABELS: Record<BgpSessionCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const BGP_SESSION_CRITICALITY_OPTIONS = BGP_SESSION_CRITICALITY_VALUES.map((value) => ({ value, label: BGP_SESSION_CRITICALITY_LABELS[value] }));

export interface BgpSession {
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
  localDeviceId: string;
  peerAddress: string;
  localAsn: number;
  remoteAsn: number;
  sessionState?: BgpSessionSessionState;
  prefixLimit?: number;
  region?: string;
  owner?: string;
  criticality?: BgpSessionCriticality;
  costCenter?: string;
  notes?: string;
}

export interface BgpSessionInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  localDeviceId: string;
  peerAddress: string;
  localAsn: number;
  remoteAsn: number;
  sessionState?: BgpSessionSessionState;
  prefixLimit?: number;
  region?: string;
  owner?: string;
  criticality?: BgpSessionCriticality;
  costCenter?: string;
  notes?: string;
}

export type BgpSessionFormValues = Partial<BgpSessionInput>;

export interface BgpSessionUpdateInput extends Partial<BgpSessionInput> {
  expectedVersion?: number;
}

export interface BgpSessionFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  localDeviceId?: string;
  peerAddress?: string;
  localAsnMin?: number;
  localAsnMax?: number;
  remoteAsnMin?: number;
  remoteAsnMax?: number;
  sessionState?: BgpSessionSessionState[];
  prefixLimitMin?: number;
  prefixLimitMax?: number;
  region?: string;
  owner?: string;
  criticality?: BgpSessionCriticality[];
  costCenter?: string;
}

export type BgpSessionSort = SortState<keyof BgpSession & string>;

export interface BgpSessionSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const BGPSESSION_PATH = '/api/v1/bgp-sessions';
export const BGPSESSION_TITLE = 'Bgp Session';
export const BGPSESSION_TITLE_PLURAL = 'Bgp Sessions';

export const BGPSESSION_DEFAULTS: BgpSessionFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  peerAddress: '',
};
