import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateFieldTasks,
  changeFieldTaskStatus,
  createFieldTask,
  deleteFieldTask,
  exportFieldTasksCsv,
  getFieldTask,
  getFieldTaskStatusStats,
  listFieldTaskSummaries,
  searchFieldTasks,
  updateFieldTask,
  listFieldTaskByWorkOrderId,
} from './api';
import type { FieldTaskFilter, FieldTaskInput, FieldTaskUpdateInput } from './types';

export const fieldTaskKeys = {
  all: ['fieldTask'] as const,
  lists: () => [...fieldTaskKeys.all, 'list'] as const,
  list: (filter: FieldTaskFilter, page: number, size: number, sort?: string) =>
    [...fieldTaskKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...fieldTaskKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...fieldTaskKeys.all, 'summaries', status] as const,
  stats: () => [...fieldTaskKeys.all, 'stats'] as const,
};

export function useFieldTaskList(filter: FieldTaskFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: fieldTaskKeys.list(filter, page, size, sort),
    queryFn: () => searchFieldTasks(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useFieldTask(id: string | undefined) {
  return useQuery({
    queryKey: fieldTaskKeys.detail(id ?? ''),
    queryFn: () => getFieldTask(id as string),
    enabled: Boolean(id),
  });
}

export function useFieldTaskSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: fieldTaskKeys.summaries(status),
    queryFn: () => listFieldTaskSummaries(status),
    staleTime: 60_000,
  });
}

export function useFieldTaskStatusStats() {
  return useQuery({
    queryKey: fieldTaskKeys.stats(),
    queryFn: getFieldTaskStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateFieldTask() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FieldTaskInput) => createFieldTask(input),
    onSuccess: () => client.invalidateQueries({ queryKey: fieldTaskKeys.all }),
  });
}

export function useUpdateFieldTask(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FieldTaskUpdateInput) => updateFieldTask(id, input),
    onSuccess: (updated) => {
      client.setQueryData(fieldTaskKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: fieldTaskKeys.lists() });
    },
  });
}

export function useChangeFieldTaskStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeFieldTaskStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: fieldTaskKeys.all }),
  });
}

export function useDeleteFieldTask() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteFieldTask(id),
    onSuccess: () => client.invalidateQueries({ queryKey: fieldTaskKeys.all }),
  });
}

export function useBulkCreateFieldTasks() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: FieldTaskInput[]) => bulkCreateFieldTasks(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: fieldTaskKeys.all }),
  });
}

export function useExportFieldTasks() {
  return useMutation({
    mutationFn: (filter: FieldTaskFilter) => exportFieldTasksCsv(filter),
  });
}

export function useFieldTasksByWorkOrderId(workOrderId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...fieldTaskKeys.all, 'by-work_order_id', workOrderId, page, size],
    queryFn: () => listFieldTaskByWorkOrderId(workOrderId as string, page, size),
    enabled: Boolean(workOrderId),
  });
}
