import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateAlarmInstances,
  changeAlarmInstanceStatus,
  createAlarmInstance,
  deleteAlarmInstance,
  exportAlarmInstancesCsv,
  getAlarmInstance,
  getAlarmInstanceStatusStats,
  listAlarmInstanceSummaries,
  searchAlarmInstances,
  updateAlarmInstance,
  listAlarmInstanceByDefinitionId,
} from './api';
import type { AlarmInstanceFilter, AlarmInstanceInput, AlarmInstanceUpdateInput } from './types';

export const alarmInstanceKeys = {
  all: ['alarmInstance'] as const,
  lists: () => [...alarmInstanceKeys.all, 'list'] as const,
  list: (filter: AlarmInstanceFilter, page: number, size: number, sort?: string) =>
    [...alarmInstanceKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...alarmInstanceKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...alarmInstanceKeys.all, 'summaries', status] as const,
  stats: () => [...alarmInstanceKeys.all, 'stats'] as const,
};

export function useAlarmInstanceList(filter: AlarmInstanceFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: alarmInstanceKeys.list(filter, page, size, sort),
    queryFn: () => searchAlarmInstances(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useAlarmInstance(id: string | undefined) {
  return useQuery({
    queryKey: alarmInstanceKeys.detail(id ?? ''),
    queryFn: () => getAlarmInstance(id as string),
    enabled: Boolean(id),
  });
}

export function useAlarmInstanceSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: alarmInstanceKeys.summaries(status),
    queryFn: () => listAlarmInstanceSummaries(status),
    staleTime: 60_000,
  });
}

export function useAlarmInstanceStatusStats() {
  return useQuery({
    queryKey: alarmInstanceKeys.stats(),
    queryFn: getAlarmInstanceStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateAlarmInstance() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AlarmInstanceInput) => createAlarmInstance(input),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmInstanceKeys.all }),
  });
}

export function useUpdateAlarmInstance(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AlarmInstanceUpdateInput) => updateAlarmInstance(id, input),
    onSuccess: (updated) => {
      client.setQueryData(alarmInstanceKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: alarmInstanceKeys.lists() });
    },
  });
}

export function useChangeAlarmInstanceStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeAlarmInstanceStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmInstanceKeys.all }),
  });
}

export function useDeleteAlarmInstance() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteAlarmInstance(id),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmInstanceKeys.all }),
  });
}

export function useBulkCreateAlarmInstances() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: AlarmInstanceInput[]) => bulkCreateAlarmInstances(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmInstanceKeys.all }),
  });
}

export function useExportAlarmInstances() {
  return useMutation({
    mutationFn: (filter: AlarmInstanceFilter) => exportAlarmInstancesCsv(filter),
  });
}

export function useAlarmInstancesByDefinitionId(definitionId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...alarmInstanceKeys.all, 'by-definition_id', definitionId, page, size],
    queryFn: () => listAlarmInstanceByDefinitionId(definitionId as string, page, size),
    enabled: Boolean(definitionId),
  });
}
