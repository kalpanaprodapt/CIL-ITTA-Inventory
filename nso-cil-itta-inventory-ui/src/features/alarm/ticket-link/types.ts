import type { LifecycleStatus, SortState } from '@/shared/types';

export const TICKET_LINK_TICKET_SYSTEM_VALUES = ['SERVICENOW', 'JIRA', 'REMEDY'] as const;
export type TicketLinkTicketSystem = (typeof TICKET_LINK_TICKET_SYSTEM_VALUES)[number];
export const TICKET_LINK_TICKET_SYSTEM_LABELS: Record<TicketLinkTicketSystem, string> = {
  SERVICENOW: 'Servicenow',
  JIRA: 'Jira',
  REMEDY: 'Remedy',
};
export const TICKET_LINK_TICKET_SYSTEM_OPTIONS = TICKET_LINK_TICKET_SYSTEM_VALUES.map((value) => ({ value, label: TICKET_LINK_TICKET_SYSTEM_LABELS[value] }));

export const TICKET_LINK_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type TicketLinkCriticality = (typeof TICKET_LINK_CRITICALITY_VALUES)[number];
export const TICKET_LINK_CRITICALITY_LABELS: Record<TicketLinkCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const TICKET_LINK_CRITICALITY_OPTIONS = TICKET_LINK_CRITICALITY_VALUES.map((value) => ({ value, label: TICKET_LINK_CRITICALITY_LABELS[value] }));

export interface TicketLink {
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
  alarmId: string;
  ticketSystem?: TicketLinkTicketSystem;
  ticketNumber: string;
  linkedAt?: string;
  owner?: string;
  criticality?: TicketLinkCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export interface TicketLinkInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  alarmId: string;
  ticketSystem?: TicketLinkTicketSystem;
  ticketNumber: string;
  linkedAt?: string;
  owner?: string;
  criticality?: TicketLinkCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
}

export type TicketLinkFormValues = Partial<TicketLinkInput>;

export interface TicketLinkUpdateInput extends Partial<TicketLinkInput> {
  expectedVersion?: number;
}

export interface TicketLinkFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  alarmId?: string;
  ticketSystem?: TicketLinkTicketSystem[];
  ticketNumber?: string;
  linkedAtFrom?: string;
  linkedAtTo?: string;
  owner?: string;
  criticality?: TicketLinkCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
}

export type TicketLinkSort = SortState<keyof TicketLink & string>;

export interface TicketLinkSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const TICKETLINK_PATH = '/api/v1/ticket-links';
export const TICKETLINK_TITLE = 'Ticket Link';
export const TICKETLINK_TITLE_PLURAL = 'Ticket Links';

export const TICKETLINK_DEFAULTS: TicketLinkFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  ticketNumber: '',
};
