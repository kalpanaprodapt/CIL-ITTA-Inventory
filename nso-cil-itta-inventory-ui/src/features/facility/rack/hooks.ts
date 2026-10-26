import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateRacks,
  changeRackStatus,
  createRack,
  deleteRack,
  exportRacksCsv,
  getRack,
  getRackStatusStats,
  listRackSummaries,
  searchRacks,
  updateRack,
  listRackByRoomId,
} from './api';
import type { RackFilter, RackInput, RackUpdateInput } from './types';

export const rackKeys = {
  all: ['rack'] as const,
  lists: () => [...rackKeys.all, 'list'] as const,
  list: (filter: RackFilter, page: number, size: number, sort?: string) =>
    [...rackKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...rackKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...rackKeys.all, 'summaries', status] as const,
  stats: () => [...rackKeys.all, 'stats'] as const,
};

export function useRackList(filter: RackFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: rackKeys.list(filter, page, size, sort),
    queryFn: () => searchRacks(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useRack(id: string | undefined) {
  return useQuery({
    queryKey: rackKeys.detail(id ?? ''),
    queryFn: () => getRack(id as string),
    enabled: Boolean(id),
  });
}

export function useRackSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: rackKeys.summaries(status),
    queryFn: () => listRackSummaries(status),
    staleTime: 60_000,
  });
}

export function useRackStatusStats() {
  return useQuery({
    queryKey: rackKeys.stats(),
    queryFn: getRackStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateRack() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RackInput) => createRack(input),
    onSuccess: () => client.invalidateQueries({ queryKey: rackKeys.all }),
  });
}

export function useUpdateRack(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: RackUpdateInput) => updateRack(id, input),
    onSuccess: (updated) => {
      client.setQueryData(rackKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: rackKeys.lists() });
    },
  });
}

export function useChangeRackStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeRackStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: rackKeys.all }),
  });
}

export function useDeleteRack() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteRack(id),
    onSuccess: () => client.invalidateQueries({ queryKey: rackKeys.all }),
  });
}

export function useBulkCreateRacks() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: RackInput[]) => bulkCreateRacks(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: rackKeys.all }),
  });
}

export function useExportRacks() {
  return useMutation({
    mutationFn: (filter: RackFilter) => exportRacksCsv(filter),
  });
}

export function useRacksByRoomId(roomId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...rackKeys.all, 'by-room_id', roomId, page, size],
    queryFn: () => listRackByRoomId(roomId as string, page, size),
    enabled: Boolean(roomId),
  });
}
