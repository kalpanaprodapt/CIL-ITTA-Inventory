import type { LifecycleStatus } from '@/shared/types';
import type { WorkOrderFilter, WorkOrderWorkState, WorkOrderLifecycleStage, WorkOrderCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toWorkOrderQuery(filter: WorkOrderFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.workOrderNumber) query.workOrderNumber = filter.workOrderNumber;
  if (filter.workType) query.workType = filter.workType;
  if (filter.assignedTechnicianId) query.assignedTechnicianId = filter.assignedTechnicianId;
  if (filter.dueDateFrom) query.dueDateFrom = filter.dueDateFrom;
  if (filter.dueDateTo) query.dueDateTo = filter.dueDateTo;
  if (filter.workState?.length) query.workState = filter.workState.join(',');
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseWorkOrderFilter(params: URLSearchParams): WorkOrderFilter {
  const filter: WorkOrderFilter = {};
  const q = params.get('q');
  if (q) filter.q = q;
  const status = params.get('status');
  if (status) filter.status = status.split(',') as LifecycleStatus[];
  const code = params.get('code');
  if (code) filter.code = code;
  const name = params.get('name');
  if (name) filter.name = name;
  const externalRef = params.get('externalRef');
  if (externalRef) filter.externalRef = externalRef;
  const workOrderNumber = params.get('workOrderNumber');
  if (workOrderNumber) filter.workOrderNumber = workOrderNumber;
  const workType = params.get('workType');
  if (workType) filter.workType = workType;
  const assignedTechnicianId = params.get('assignedTechnicianId');
  if (assignedTechnicianId) filter.assignedTechnicianId = assignedTechnicianId;
  const dueDateFrom = params.get('dueDateFrom');
  if (dueDateFrom) filter.dueDateFrom = dueDateFrom;
  const dueDateTo = params.get('dueDateTo');
  if (dueDateTo) filter.dueDateTo = dueDateTo;
  const workState = params.get('workState');
  if (workState) filter.workState = workState.split(',') as WorkOrderWorkState[];
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as WorkOrderLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as WorkOrderCriticality[];
  return filter;
}

export function serializeWorkOrderFilter(filter: WorkOrderFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.workOrderNumber) params.set('workOrderNumber', filter.workOrderNumber);
  if (filter.workType) params.set('workType', filter.workType);
  if (filter.assignedTechnicianId) params.set('assignedTechnicianId', filter.assignedTechnicianId);
  if (filter.dueDateFrom) params.set('dueDateFrom', filter.dueDateFrom);
  if (filter.dueDateTo) params.set('dueDateTo', filter.dueDateTo);
  if (filter.workState?.length) params.set('workState', filter.workState.join(','));
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countWorkOrderActiveFilters(filter: WorkOrderFilter): number {
  return Array.from(serializeWorkOrderFilter(filter).keys()).length;
}
