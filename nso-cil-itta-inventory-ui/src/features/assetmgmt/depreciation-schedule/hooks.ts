import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateDepreciationSchedules,
  changeDepreciationScheduleStatus,
  createDepreciationSchedule,
  deleteDepreciationSchedule,
  exportDepreciationSchedulesCsv,
  getDepreciationSchedule,
  getDepreciationScheduleStatusStats,
  listDepreciationScheduleSummaries,
  searchDepreciationSchedules,
  updateDepreciationSchedule,
  listDepreciationScheduleByAssetId,
} from './api';
import type { DepreciationScheduleFilter, DepreciationScheduleInput, DepreciationScheduleUpdateInput } from './types';

export const depreciationScheduleKeys = {
  all: ['depreciationSchedule'] as const,
  lists: () => [...depreciationScheduleKeys.all, 'list'] as const,
  list: (filter: DepreciationScheduleFilter, page: number, size: number, sort?: string) =>
    [...depreciationScheduleKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...depreciationScheduleKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...depreciationScheduleKeys.all, 'summaries', status] as const,
  stats: () => [...depreciationScheduleKeys.all, 'stats'] as const,
};

export function useDepreciationScheduleList(filter: DepreciationScheduleFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: depreciationScheduleKeys.list(filter, page, size, sort),
    queryFn: () => searchDepreciationSchedules(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useDepreciationSchedule(id: string | undefined) {
  return useQuery({
    queryKey: depreciationScheduleKeys.detail(id ?? ''),
    queryFn: () => getDepreciationSchedule(id as string),
    enabled: Boolean(id),
  });
}

export function useDepreciationScheduleSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: depreciationScheduleKeys.summaries(status),
    queryFn: () => listDepreciationScheduleSummaries(status),
    staleTime: 60_000,
  });
}

export function useDepreciationScheduleStatusStats() {
  return useQuery({
    queryKey: depreciationScheduleKeys.stats(),
    queryFn: getDepreciationScheduleStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateDepreciationSchedule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DepreciationScheduleInput) => createDepreciationSchedule(input),
    onSuccess: () => client.invalidateQueries({ queryKey: depreciationScheduleKeys.all }),
  });
}

export function useUpdateDepreciationSchedule(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DepreciationScheduleUpdateInput) => updateDepreciationSchedule(id, input),
    onSuccess: (updated) => {
      client.setQueryData(depreciationScheduleKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: depreciationScheduleKeys.lists() });
    },
  });
}

export function useChangeDepreciationScheduleStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeDepreciationScheduleStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: depreciationScheduleKeys.all }),
  });
}

export function useDeleteDepreciationSchedule() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteDepreciationSchedule(id),
    onSuccess: () => client.invalidateQueries({ queryKey: depreciationScheduleKeys.all }),
  });
}

export function useBulkCreateDepreciationSchedules() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: DepreciationScheduleInput[]) => bulkCreateDepreciationSchedules(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: depreciationScheduleKeys.all }),
  });
}

export function useExportDepreciationSchedules() {
  return useMutation({
    mutationFn: (filter: DepreciationScheduleFilter) => exportDepreciationSchedulesCsv(filter),
  });
}

export function useDepreciationSchedulesByAssetId(assetId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...depreciationScheduleKeys.all, 'by-asset_id', assetId, page, size],
    queryFn: () => listDepreciationScheduleByAssetId(assetId as string, page, size),
    enabled: Boolean(assetId),
  });
}
