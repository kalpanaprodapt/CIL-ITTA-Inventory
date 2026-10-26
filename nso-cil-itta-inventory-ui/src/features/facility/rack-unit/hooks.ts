import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateRackUnits,
  changeRackUnitStatus,
  createRackUnit,
  deleteRackUnit,
  exportRackUnitsCsv,
  getRackUnit,
  getRackUnitStatusStats,
  listRackUnitSummaries,
  searchRackUnits,
  updateRackUnit,
  listRackUnitByRackId,
  listRackUnitByMountedDeviceId,
} from './api';
import type { RackUnitFilter, RackUnitInput, RackUnitUpdateInput } from './types';

export const rackUnitKeys = {
  all: ['rackUnit'] as const,
  lists: () => [...rackUnitKeys.all, 'list'] as const,
  list: (filter: RackUnitFilter, page: number, size: number, sort?: string) =>
    [...rackUnitKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...rackUnitKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...rackUnitKeys.all, 'summaries', status] as const,
  stats: () => [...rackUnitKeys.all, 'stats'] as const,
};

export function useRackUnitList(filter: RackUnitFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: rackUnitKeys.list(filter, page, size, sort),
    queryFn: () => searchRackUnits(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useRackUnit(id: string | undefined) {
  return useQuery({
    queryKey: rackUnitKeys.detail(id ?? ''),
    queryFn: () => getRackUnit(id as string),
    enabled: Boolean(id),
  });
}

export function useRackUnitSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: rackUnitKeys.summaries(status),
    queryFn: () => listRackUnitSummaries(status),
    staleTime: 60_000,
  });
}

export function useRackUnitStatusStats() {
  return useQuery({
    queryKey: rackUnitKeys.stats(),
    queryFn: getRackUnitStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateRackUnit() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RackUnitInput) => createRackUnit(input),
    onSuccess: () => client.invalidateQueries({ queryKey: rackUnitKeys.all }),
  });
}

export function useUpdateRackUnit(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RackUnitUpdateInput) => updateRackUnit(id, input),
    onSuccess: (updated) => {
      client.setQueryData(rackUnitKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: rackUnitKeys.lists() });
    },
  });
}

export function useChangeRackUnitStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeRackUnitStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: rackUnitKeys.all }),
  });
}

export function useDeleteRackUnit() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteRackUnit(id),
    onSuccess: () => client.invalidateQueries({ queryKey: rackUnitKeys.all }),
  });
}

export function useBulkCreateRackUnits() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: RackUnitInput[]) => bulkCreateRackUnits(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: rackUnitKeys.all }),
  });
}

export function useExportRackUnits() {
  return useMutation({
    mutationFn: (filter: RackUnitFilter) => exportRackUnitsCsv(filter),
  });
}

export function useRackUnitsByRackId(rackId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...rackUnitKeys.all, 'by-rack_id', rackId, page, size],
    queryFn: () => listRackUnitByRackId(rackId as string, page, size),
    enabled: Boolean(rackId),
  });
}

export function useRackUnitsByMountedDeviceId(mountedDeviceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...rackUnitKeys.all, 'by-mounted_device_id', mountedDeviceId, page, size],
    queryFn: () => listRackUnitByMountedDeviceId(mountedDeviceId as string, page, size),
    enabled: Boolean(mountedDeviceId),
  });
}
