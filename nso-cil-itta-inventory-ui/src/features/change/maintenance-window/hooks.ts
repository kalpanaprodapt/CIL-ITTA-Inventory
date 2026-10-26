import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateMaintenanceWindows,
  changeMaintenanceWindowStatus,
  createMaintenanceWindow,
  deleteMaintenanceWindow,
  exportMaintenanceWindowsCsv,
  getMaintenanceWindow,
  getMaintenanceWindowStatusStats,
  listMaintenanceWindowSummaries,
  searchMaintenanceWindows,
  updateMaintenanceWindow,

} from './api';
import type { MaintenanceWindowFilter, MaintenanceWindowInput, MaintenanceWindowUpdateInput } from './types';

export const maintenanceWindowKeys = {
  all: ['maintenanceWindow'] as const,
  lists: () => [...maintenanceWindowKeys.all, 'list'] as const,
  list: (filter: MaintenanceWindowFilter, page: number, size: number, sort?: string) =>
    [...maintenanceWindowKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...maintenanceWindowKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...maintenanceWindowKeys.all, 'summaries', status] as const,
  stats: () => [...maintenanceWindowKeys.all, 'stats'] as const,
};

export function useMaintenanceWindowList(filter: MaintenanceWindowFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: maintenanceWindowKeys.list(filter, page, size, sort),
    queryFn: () => searchMaintenanceWindows(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useMaintenanceWindow(id: string | undefined) {
  return useQuery({
    queryKey: maintenanceWindowKeys.detail(id ?? ''),
    queryFn: () => getMaintenanceWindow(id as string),
    enabled: Boolean(id),
  });
}

export function useMaintenanceWindowSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: maintenanceWindowKeys.summaries(status),
    queryFn: () => listMaintenanceWindowSummaries(status),
    staleTime: 60_000,
  });
}

export function useMaintenanceWindowStatusStats() {
  return useQuery({
    queryKey: maintenanceWindowKeys.stats(),
    queryFn: getMaintenanceWindowStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateMaintenanceWindow() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: MaintenanceWindowInput) => createMaintenanceWindow(input),
    onSuccess: () => client.invalidateQueries({ queryKey: maintenanceWindowKeys.all }),
  });
}

export function useUpdateMaintenanceWindow(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: MaintenanceWindowUpdateInput) => updateMaintenanceWindow(id, input),
    onSuccess: (updated) => {
      client.setQueryData(maintenanceWindowKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: maintenanceWindowKeys.lists() });
    },
  });
}

export function useChangeMaintenanceWindowStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeMaintenanceWindowStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: maintenanceWindowKeys.all }),
  });
}

export function useDeleteMaintenanceWindow() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteMaintenanceWindow(id),
    onSuccess: () => client.invalidateQueries({ queryKey: maintenanceWindowKeys.all }),
  });
}

export function useBulkCreateMaintenanceWindows() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: MaintenanceWindowInput[]) => bulkCreateMaintenanceWindows(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: maintenanceWindowKeys.all }),
  });
}

export function useExportMaintenanceWindows() {
  return useMutation({
    mutationFn: (filter: MaintenanceWindowFilter) => exportMaintenanceWindowsCsv(filter),
  });
}
