import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateBaseStations,
  changeBaseStationStatus,
  createBaseStation,
  deleteBaseStation,
  exportBaseStationsCsv,
  getBaseStation,
  getBaseStationStatusStats,
  listBaseStationSummaries,
  searchBaseStations,
  updateBaseStation,
  listBaseStationBySiteId,
} from './api';
import type { BaseStationFilter, BaseStationInput, BaseStationUpdateInput } from './types';

export const baseStationKeys = {
  all: ['baseStation'] as const,
  lists: () => [...baseStationKeys.all, 'list'] as const,
  list: (filter: BaseStationFilter, page: number, size: number, sort?: string) =>
    [...baseStationKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...baseStationKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...baseStationKeys.all, 'summaries', status] as const,
  stats: () => [...baseStationKeys.all, 'stats'] as const,
};

export function useBaseStationList(filter: BaseStationFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: baseStationKeys.list(filter, page, size, sort),
    queryFn: () => searchBaseStations(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useBaseStation(id: string | undefined) {
  return useQuery({
    queryKey: baseStationKeys.detail(id ?? ''),
    queryFn: () => getBaseStation(id as string),
    enabled: Boolean(id),
  });
}

export function useBaseStationSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: baseStationKeys.summaries(status),
    queryFn: () => listBaseStationSummaries(status),
    staleTime: 60_000,
  });
}

export function useBaseStationStatusStats() {
  return useQuery({
    queryKey: baseStationKeys.stats(),
    queryFn: getBaseStationStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateBaseStation() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BaseStationInput) => createBaseStation(input),
    onSuccess: () => client.invalidateQueries({ queryKey: baseStationKeys.all }),
  });
}

export function useUpdateBaseStation(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BaseStationUpdateInput) => updateBaseStation(id, input),
    onSuccess: (updated) => {
      client.setQueryData(baseStationKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: baseStationKeys.lists() });
    },
  });
}

export function useChangeBaseStationStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeBaseStationStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: baseStationKeys.all }),
  });
}

export function useDeleteBaseStation() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteBaseStation(id),
    onSuccess: () => client.invalidateQueries({ queryKey: baseStationKeys.all }),
  });
}

export function useBulkCreateBaseStations() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: BaseStationInput[]) => bulkCreateBaseStations(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: baseStationKeys.all }),
  });
}

export function useExportBaseStations() {
  return useMutation({
    mutationFn: (filter: BaseStationFilter) => exportBaseStationsCsv(filter),
  });
}

export function useBaseStationsBySiteId(siteId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...baseStationKeys.all, 'by-site_id', siteId, page, size],
    queryFn: () => listBaseStationBySiteId(siteId as string, page, size),
    enabled: Boolean(siteId),
  });
}
