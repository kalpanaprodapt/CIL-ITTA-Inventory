import type { LifecycleStatus, SortState } from '@/shared/types';

export const EVENT_SOURCE_PROTOCOL_VALUES = ['SNMP_TRAP', 'SYSLOG', 'KAFKA', 'WEBHOOK'] as const;
export type EventSourceProtocol = (typeof EVENT_SOURCE_PROTOCOL_VALUES)[number];
export const EVENT_SOURCE_PROTOCOL_LABELS: Record<EventSourceProtocol, string> = {
  SNMP_TRAP: 'Snmp Trap',
  SYSLOG: 'Syslog',
  KAFKA: 'Kafka',
  WEBHOOK: 'Webhook',
};
export const EVENT_SOURCE_PROTOCOL_OPTIONS = EVENT_SOURCE_PROTOCOL_VALUES.map((value) => ({ value, label: EVENT_SOURCE_PROTOCOL_LABELS[value] }));

export const EVENT_SOURCE_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type EventSourceLifecycleStage = (typeof EVENT_SOURCE_LIFECYCLE_STAGE_VALUES)[number];
export const EVENT_SOURCE_LIFECYCLE_STAGE_LABELS: Record<EventSourceLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const EVENT_SOURCE_LIFECYCLE_STAGE_OPTIONS = EVENT_SOURCE_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: EVENT_SOURCE_LIFECYCLE_STAGE_LABELS[value] }));

export interface EventSource {
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
  sourceName: string;
  protocol?: EventSourceProtocol;
  endpoint?: string;
  heartbeatSeconds?: number;
  labels?: string;
  lifecycleStage?: EventSourceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export interface EventSourceInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  sourceName: string;
  protocol?: EventSourceProtocol;
  endpoint?: string;
  heartbeatSeconds?: number;
  labels?: string;
  lifecycleStage?: EventSourceLifecycleStage;
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type EventSourceFormValues = Partial<EventSourceInput>;

export interface EventSourceUpdateInput extends Partial<EventSourceInput> {
  expectedVersion?: number;
}

export interface EventSourceFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  sourceName?: string;
  protocol?: EventSourceProtocol[];
  endpoint?: string;
  heartbeatSecondsMin?: number;
  heartbeatSecondsMax?: number;
  labels?: string;
  lifecycleStage?: EventSourceLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  owner?: string;
}

export type EventSourceSort = SortState<keyof EventSource & string>;

export interface EventSourceSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const EVENTSOURCE_PATH = '/api/v1/event-sources';
export const EVENTSOURCE_TITLE = 'Event Source';
export const EVENTSOURCE_TITLE_PLURAL = 'Event Sourcees';

export const EVENTSOURCE_DEFAULTS: EventSourceFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  sourceName: '',
};
