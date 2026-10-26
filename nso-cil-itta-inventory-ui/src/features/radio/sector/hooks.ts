import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSectors,
  changeSectorStatus,
  createSector,
  deleteSector,
  exportSectorsCsv,
  getSector,
  getSectorStatusStats,
  listSectorSummaries,
  searchSectors,
  updateSector,
  listSectorByBaseStationId,
} from './api';
import type { SectorFilter, SectorInput, SectorUpdateInput } from './types';

export const sectorKeys = {
  all: ['sector'] as const,
  lists: () => [...sectorKeys.all, 'list'] as const,
  list: (filter: SectorFilter, page: number, size: number, sort?: string) =>
    [...sectorKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...sectorKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...sectorKeys.all, 'summaries', status] as const,
  stats: () => [...sectorKeys.all, 'stats'] as const,
};

export function useSectorList(filter: SectorFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: sectorKeys.list(filter, page, size, sort),
    queryFn: () => searchSectors(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSector(id: string | undefined) {
  return useQuery({
    queryKey: sectorKeys.detail(id ?? ''),
    queryFn: () => getSector(id as string),
    enabled: Boolean(id),
  });
}

export function useSectorSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: sectorKeys.summaries(status),
    queryFn: () => listSectorSummaries(status),
    staleTime: 60_000,
  });
}

export function useSectorStatusStats() {
  return useQuery({
    queryKey: sectorKeys.stats(),
    queryFn: getSectorStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSector() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SectorInput) => createSector(input),
    onSuccess: () => client.invalidateQueries({ queryKey: sectorKeys.all }),
  });
}

export function useUpdateSector(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SectorUpdateInput) => updateSector(id, input),
    onSuccess: (updated) => {
      client.setQueryData(sectorKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: sectorKeys.lists() });
    },
  });
}

export function useChangeSectorStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSectorStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: sectorKeys.all }),
  });
}

export function useDeleteSector() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSector(id),
    onSuccess: () => client.invalidateQueries({ queryKey: sectorKeys.all }),
  });
}

export function useBulkCreateSectors() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SectorInput[]) => bulkCreateSectors(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: sectorKeys.all }),
  });
}

export function useExportSectors() {
  return useMutation({
    mutationFn: (filter: SectorFilter) => exportSectorsCsv(filter),
  });
}

export function useSectorsByBaseStationId(baseStationId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...sectorKeys.all, 'by-base_station_id', baseStationId, page, size],
    queryFn: () => listSectorByBaseStationId(baseStationId as string, page, size),
    enabled: Boolean(baseStationId),
  });
}
