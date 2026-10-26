import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateFloors,
  changeFloorStatus,
  createFloor,
  deleteFloor,
  exportFloorsCsv,
  getFloor,
  getFloorStatusStats,
  listFloorSummaries,
  searchFloors,
  updateFloor,
  listFloorByBuildingId,
} from './api';
import type { FloorFilter, FloorInput, FloorUpdateInput } from './types';

export const floorKeys = {
  all: ['floor'] as const,
  lists: () => [...floorKeys.all, 'list'] as const,
  list: (filter: FloorFilter, page: number, size: number, sort?: string) =>
    [...floorKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...floorKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...floorKeys.all, 'summaries', status] as const,
  stats: () => [...floorKeys.all, 'stats'] as const,
};

export function useFloorList(filter: FloorFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: floorKeys.list(filter, page, size, sort),
    queryFn: () => searchFloors(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useFloor(id: string | undefined) {
  return useQuery({
    queryKey: floorKeys.detail(id ?? ''),
    queryFn: () => getFloor(id as string),
    enabled: Boolean(id),
  });
}

export function useFloorSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: floorKeys.summaries(status),
    queryFn: () => listFloorSummaries(status),
    staleTime: 60_000,
  });
}

export function useFloorStatusStats() {
  return useQuery({
    queryKey: floorKeys.stats(),
    queryFn: getFloorStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateFloor() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FloorInput) => createFloor(input),
    onSuccess: () => client.invalidateQueries({ queryKey: floorKeys.all }),
  });
}

export function useUpdateFloor(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: FloorUpdateInput) => updateFloor(id, input),
    onSuccess: (updated) => {
      client.setQueryData(floorKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: floorKeys.lists() });
    },
  });
}

export function useChangeFloorStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeFloorStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: floorKeys.all }),
  });
}

export function useDeleteFloor() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteFloor(id),
    onSuccess: () => client.invalidateQueries({ queryKey: floorKeys.all }),
  });
}

export function useBulkCreateFloors() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: FloorInput[]) => bulkCreateFloors(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: floorKeys.all }),
  });
}

export function useExportFloors() {
  return useMutation({
    mutationFn: (filter: FloorFilter) => exportFloorsCsv(filter),
  });
}

export function useFloorsByBuildingId(buildingId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...floorKeys.all, 'by-building_id', buildingId, page, size],
    queryFn: () => listFloorByBuildingId(buildingId as string, page, size),
    enabled: Boolean(buildingId),
  });
}
