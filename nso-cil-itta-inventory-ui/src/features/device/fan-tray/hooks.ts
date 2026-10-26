import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateFanTrays,
  changeFanTrayStatus,
  createFanTray,
  deleteFanTray,
  exportFanTraysCsv,
  getFanTray,
  getFanTrayStatusStats,
  listFanTraySummaries,
  searchFanTrays,
  updateFanTray,
  listFanTrayByChassisId,
} from './api';
import type { FanTrayFilter, FanTrayInput, FanTrayUpdateInput } from './types';

export const fanTrayKeys = {
  all: ['fanTray'] as const,
  lists: () => [...fanTrayKeys.all, 'list'] as const,
  list: (filter: FanTrayFilter, page: number, size: number, sort?: string) =>
    [...fanTrayKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...fanTrayKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...fanTrayKeys.all, 'summaries', status] as const,
  stats: () => [...fanTrayKeys.all, 'stats'] as const,
};

export function useFanTrayList(filter: FanTrayFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: fanTrayKeys.list(filter, page, size, sort),
    queryFn: () => searchFanTrays(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useFanTray(id: string | undefined) {
  return useQuery({
    queryKey: fanTrayKeys.detail(id ?? ''),
    queryFn: () => getFanTray(id as string),
    enabled: Boolean(id),
  });
}

export function useFanTraySummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: fanTrayKeys.summaries(status),
    queryFn: () => listFanTraySummaries(status),
    staleTime: 60_000,
  });
}

export function useFanTrayStatusStats() {
  return useQuery({
    queryKey: fanTrayKeys.stats(),
    queryFn: getFanTrayStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateFanTray() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FanTrayInput) => createFanTray(input),
    onSuccess: () => client.invalidateQueries({ queryKey: fanTrayKeys.all }),
  });
}

export function useUpdateFanTray(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FanTrayUpdateInput) => updateFanTray(id, input),
    onSuccess: (updated) => {
      client.setQueryData(fanTrayKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: fanTrayKeys.lists() });
    },
  });
}

export function useChangeFanTrayStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeFanTrayStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: fanTrayKeys.all }),
  });
}

export function useDeleteFanTray() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteFanTray(id),
    onSuccess: () => client.invalidateQueries({ queryKey: fanTrayKeys.all }),
  });
}

export function useBulkCreateFanTrays() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: FanTrayInput[]) => bulkCreateFanTrays(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: fanTrayKeys.all }),
  });
}

export function useExportFanTrays() {
  return useMutation({
    mutationFn: (filter: FanTrayFilter) => exportFanTraysCsv(filter),
  });
}

export function useFanTraysByChassisId(chassisId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...fanTrayKeys.all, 'by-chassis_id', chassisId, page, size],
    queryFn: () => listFanTrayByChassisId(chassisId as string, page, size),
    enabled: Boolean(chassisId),
  });
}
