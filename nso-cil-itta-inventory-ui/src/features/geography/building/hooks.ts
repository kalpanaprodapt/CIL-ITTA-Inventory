import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateBuildings,
  changeBuildingStatus,
  createBuilding,
  deleteBuilding,
  exportBuildingsCsv,
  getBuilding,
  getBuildingStatusStats,
  listBuildingSummaries,
  searchBuildings,
  updateBuilding,
  listBuildingBySiteId,
} from './api';
import type { BuildingFilter, BuildingInput, BuildingUpdateInput } from './types';

export const buildingKeys = {
  all: ['building'] as const,
  lists: () => [...buildingKeys.all, 'list'] as const,
  list: (filter: BuildingFilter, page: number, size: number, sort?: string) =>
    [...buildingKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...buildingKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...buildingKeys.all, 'summaries', status] as const,
  stats: () => [...buildingKeys.all, 'stats'] as const,
};

export function useBuildingList(filter: BuildingFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: buildingKeys.list(filter, page, size, sort),
    queryFn: () => searchBuildings(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useBuilding(id: string | undefined) {
  return useQuery({
    queryKey: buildingKeys.detail(id ?? ''),
    queryFn: () => getBuilding(id as string),
    enabled: Boolean(id),
  });
}

export function useBuildingSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: buildingKeys.summaries(status),
    queryFn: () => listBuildingSummaries(status),
    staleTime: 60_000,
  });
}

export function useBuildingStatusStats() {
  return useQuery({
    queryKey: buildingKeys.stats(),
    queryFn: getBuildingStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateBuilding() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BuildingInput) => createBuilding(input),
    onSuccess: () => client.invalidateQueries({ queryKey: buildingKeys.all }),
  });
}

export function useUpdateBuilding(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BuildingUpdateInput) => updateBuilding(id, input),
    onSuccess: (updated) => {
      client.setQueryData(buildingKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: buildingKeys.lists() });
    },
  });
}

export function useChangeBuildingStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeBuildingStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: buildingKeys.all }),
  });
}

export function useDeleteBuilding() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteBuilding(id),
    onSuccess: () => client.invalidateQueries({ queryKey: buildingKeys.all }),
  });
}

export function useBulkCreateBuildings() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: BuildingInput[]) => bulkCreateBuildings(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: buildingKeys.all }),
  });
}

export function useExportBuildings() {
  return useMutation({
    mutationFn: (filter: BuildingFilter) => exportBuildingsCsv(filter),
  });
}

export function useBuildingsBySiteId(siteId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...buildingKeys.all, 'by-site_id', siteId, page, size],
    queryFn: () => listBuildingBySiteId(siteId as string, page, size),
    enabled: Boolean(siteId),
  });
}
