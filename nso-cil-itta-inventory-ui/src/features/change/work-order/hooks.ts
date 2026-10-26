import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateWorkOrders,
  changeWorkOrderStatus,
  createWorkOrder,
  deleteWorkOrder,
  exportWorkOrdersCsv,
  getWorkOrder,
  getWorkOrderStatusStats,
  listWorkOrderSummaries,
  searchWorkOrders,
  updateWorkOrder,
  listWorkOrderByAssignedTechnicianId,
} from './api';
import type { WorkOrderFilter, WorkOrderInput, WorkOrderUpdateInput } from './types';

export const workOrderKeys = {
  all: ['workOrder'] as const,
  lists: () => [...workOrderKeys.all, 'list'] as const,
  list: (filter: WorkOrderFilter, page: number, size: number, sort?: string) =>
    [...workOrderKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...workOrderKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...workOrderKeys.all, 'summaries', status] as const,
  stats: () => [...workOrderKeys.all, 'stats'] as const,
};

export function useWorkOrderList(filter: WorkOrderFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: workOrderKeys.list(filter, page, size, sort),
    queryFn: () => searchWorkOrders(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useWorkOrder(id: string | undefined) {
  return useQuery({
    queryKey: workOrderKeys.detail(id ?? ''),
    queryFn: () => getWorkOrder(id as string),
    enabled: Boolean(id),
  });
}

export function useWorkOrderSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: workOrderKeys.summaries(status),
    queryFn: () => listWorkOrderSummaries(status),
    staleTime: 60_000,
  });
}

export function useWorkOrderStatusStats() {
  return useQuery({
    queryKey: workOrderKeys.stats(),
    queryFn: getWorkOrderStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateWorkOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WorkOrderInput) => createWorkOrder(input),
    onSuccess: () => client.invalidateQueries({ queryKey: workOrderKeys.all }),
  });
}

export function useUpdateWorkOrder(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: WorkOrderUpdateInput) => updateWorkOrder(id, input),
    onSuccess: (updated) => {
      client.setQueryData(workOrderKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: workOrderKeys.lists() });
    },
  });
}

export function useChangeWorkOrderStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeWorkOrderStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: workOrderKeys.all }),
  });
}

export function useDeleteWorkOrder() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteWorkOrder(id),
    onSuccess: () => client.invalidateQueries({ queryKey: workOrderKeys.all }),
  });
}

export function useBulkCreateWorkOrders() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: WorkOrderInput[]) => bulkCreateWorkOrders(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: workOrderKeys.all }),
  });
}

export function useExportWorkOrders() {
  return useMutation({
    mutationFn: (filter: WorkOrderFilter) => exportWorkOrdersCsv(filter),
  });
}

export function useWorkOrdersByAssignedTechnicianId(assignedTechnicianId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...workOrderKeys.all, 'by-assigned_technician_id', assignedTechnicianId, page, size],
    queryFn: () => listWorkOrderByAssignedTechnicianId(assignedTechnicianId as string, page, size),
    enabled: Boolean(assignedTechnicianId),
  });
}
