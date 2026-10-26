import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateRegions,
  changeRegionStatus,
  createRegion,
  deleteRegion,
  exportRegionsCsv,
  getRegion,
  getRegionStatusStats,
  listRegionSummaries,
  searchRegions,
  updateRegion,
  listRegionByCountryId,
} from './api';
import type { RegionFilter, RegionInput, RegionUpdateInput } from './types';

export const regionKeys = {
  all: ['region'] as const,
  lists: () => [...regionKeys.all, 'list'] as const,
  list: (filter: RegionFilter, page: number, size: number, sort?: string) =>
    [...regionKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...regionKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...regionKeys.all, 'summaries', status] as const,
  stats: () => [...regionKeys.all, 'stats'] as const,
};

export function useRegionList(filter: RegionFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: regionKeys.list(filter, page, size, sort),
    queryFn: () => searchRegions(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useRegion(id: string | undefined) {
  return useQuery({
    queryKey: regionKeys.detail(id ?? ''),
    queryFn: () => getRegion(id as string),
    enabled: Boolean(id),
  });
}

export function useRegionSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: regionKeys.summaries(status),
    queryFn: () => listRegionSummaries(status),
    staleTime: 60_000,
  });
}

export function useRegionStatusStats() {
  return useQuery({
    queryKey: regionKeys.stats(),
    queryFn: getRegionStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateRegion() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RegionInput) => createRegion(input),
    onSuccess: () => client.invalidateQueries({ queryKey: regionKeys.all }),
  });
}

export function useUpdateRegion(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RegionUpdateInput) => updateRegion(id, input),
    onSuccess: (updated) => {
      client.setQueryData(regionKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: regionKeys.lists() });
    },
  });
}

export function useChangeRegionStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeRegionStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: regionKeys.all }),
  });
}

export function useDeleteRegion() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteRegion(id),
    onSuccess: () => client.invalidateQueries({ queryKey: regionKeys.all }),
  });
}

export function useBulkCreateRegions() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: RegionInput[]) => bulkCreateRegions(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: regionKeys.all }),
  });
}

export function useExportRegions() {
  return useMutation({
    mutationFn: (filter: RegionFilter) => exportRegionsCsv(filter),
  });
}

export function useRegionsByCountryId(countryId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...regionKeys.all, 'by-country_id', countryId, page, size],
    queryFn: () => listRegionByCountryId(countryId as string, page, size),
    enabled: Boolean(countryId),
  });
}
