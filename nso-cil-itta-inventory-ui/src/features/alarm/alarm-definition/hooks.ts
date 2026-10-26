import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateAlarmDefinitions,
  changeAlarmDefinitionStatus,
  createAlarmDefinition,
  deleteAlarmDefinition,
  exportAlarmDefinitionsCsv,
  getAlarmDefinition,
  getAlarmDefinitionStatusStats,
  listAlarmDefinitionSummaries,
  searchAlarmDefinitions,
  updateAlarmDefinition,

} from './api';
import type { AlarmDefinitionFilter, AlarmDefinitionInput, AlarmDefinitionUpdateInput } from './types';

export const alarmDefinitionKeys = {
  all: ['alarmDefinition'] as const,
  lists: () => [...alarmDefinitionKeys.all, 'list'] as const,
  list: (filter: AlarmDefinitionFilter, page: number, size: number, sort?: string) =>
    [...alarmDefinitionKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...alarmDefinitionKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...alarmDefinitionKeys.all, 'summaries', status] as const,
  stats: () => [...alarmDefinitionKeys.all, 'stats'] as const,
};

export function useAlarmDefinitionList(filter: AlarmDefinitionFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: alarmDefinitionKeys.list(filter, page, size, sort),
    queryFn: () => searchAlarmDefinitions(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useAlarmDefinition(id: string | undefined) {
  return useQuery({
    queryKey: alarmDefinitionKeys.detail(id ?? ''),
    queryFn: () => getAlarmDefinition(id as string),
    enabled: Boolean(id),
  });
}

export function useAlarmDefinitionSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: alarmDefinitionKeys.summaries(status),
    queryFn: () => listAlarmDefinitionSummaries(status),
    staleTime: 60_000,
  });
}

export function useAlarmDefinitionStatusStats() {
  return useQuery({
    queryKey: alarmDefinitionKeys.stats(),
    queryFn: getAlarmDefinitionStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateAlarmDefinition() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AlarmDefinitionInput) => createAlarmDefinition(input),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmDefinitionKeys.all }),
  });
}

export function useUpdateAlarmDefinition(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AlarmDefinitionUpdateInput) => updateAlarmDefinition(id, input),
    onSuccess: (updated) => {
      client.setQueryData(alarmDefinitionKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: alarmDefinitionKeys.lists() });
    },
  });
}

export function useChangeAlarmDefinitionStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeAlarmDefinitionStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmDefinitionKeys.all }),
  });
}

export function useDeleteAlarmDefinition() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteAlarmDefinition(id),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmDefinitionKeys.all }),
  });
}

export function useBulkCreateAlarmDefinitions() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: AlarmDefinitionInput[]) => bulkCreateAlarmDefinitions(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: alarmDefinitionKeys.all }),
  });
}

export function useExportAlarmDefinitions() {
  return useMutation({
    mutationFn: (filter: AlarmDefinitionFilter) => exportAlarmDefinitionsCsv(filter),
  });
}
