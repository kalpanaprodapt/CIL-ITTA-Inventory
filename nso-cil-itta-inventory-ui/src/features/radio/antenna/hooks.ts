import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateAntennas,
  changeAntennaStatus,
  createAntenna,
  deleteAntenna,
  exportAntennasCsv,
  getAntenna,
  getAntennaStatusStats,
  listAntennaSummaries,
  searchAntennas,
  updateAntenna,
  listAntennaBySectorId,
} from './api';
import type { AntennaFilter, AntennaInput, AntennaUpdateInput } from './types';

export const antennaKeys = {
  all: ['antenna'] as const,
  lists: () => [...antennaKeys.all, 'list'] as const,
  list: (filter: AntennaFilter, page: number, size: number, sort?: string) =>
    [...antennaKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...antennaKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...antennaKeys.all, 'summaries', status] as const,
  stats: () => [...antennaKeys.all, 'stats'] as const,
};

export function useAntennaList(filter: AntennaFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: antennaKeys.list(filter, page, size, sort),
    queryFn: () => searchAntennas(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useAntenna(id: string | undefined) {
  return useQuery({
    queryKey: antennaKeys.detail(id ?? ''),
    queryFn: () => getAntenna(id as string),
    enabled: Boolean(id),
  });
}

export function useAntennaSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: antennaKeys.summaries(status),
    queryFn: () => listAntennaSummaries(status),
    staleTime: 60_000,
  });
}

export function useAntennaStatusStats() {
  return useQuery({
    queryKey: antennaKeys.stats(),
    queryFn: getAntennaStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateAntenna() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AntennaInput) => createAntenna(input),
    onSuccess: () => client.invalidateQueries({ queryKey: antennaKeys.all }),
  });
}

export function useUpdateAntenna(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: AntennaUpdateInput) => updateAntenna(id, input),
    onSuccess: (updated) => {
      client.setQueryData(antennaKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: antennaKeys.lists() });
    },
  });
}

export function useChangeAntennaStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeAntennaStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: antennaKeys.all }),
  });
}

export function useDeleteAntenna() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteAntenna(id),
    onSuccess: () => client.invalidateQueries({ queryKey: antennaKeys.all }),
  });
}

export function useBulkCreateAntennas() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: AntennaInput[]) => bulkCreateAntennas(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: antennaKeys.all }),
  });
}

export function useExportAntennas() {
  return useMutation({
    mutationFn: (filter: AntennaFilter) => exportAntennasCsv(filter),
  });
}

export function useAntennasBySectorId(sectorId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...antennaKeys.all, 'by-sector_id', sectorId, page, size],
    queryFn: () => listAntennaBySectorId(sectorId as string, page, size),
    enabled: Boolean(sectorId),
  });
}
