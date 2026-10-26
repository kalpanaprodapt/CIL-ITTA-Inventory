import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateCables,
  changeCableStatus,
  createCable,
  deleteCable,
  exportCablesCsv,
  getCable,
  getCableStatusStats,
  listCableSummaries,
  searchCables,
  updateCable,
  listCableByAEndPortId,
  listCableByZEndPortId,
} from './api';
import type { CableFilter, CableInput, CableUpdateInput } from './types';

export const cableKeys = {
  all: ['cable'] as const,
  lists: () => [...cableKeys.all, 'list'] as const,
  list: (filter: CableFilter, page: number, size: number, sort?: string) =>
    [...cableKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...cableKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...cableKeys.all, 'summaries', status] as const,
  stats: () => [...cableKeys.all, 'stats'] as const,
};

export function useCableList(filter: CableFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: cableKeys.list(filter, page, size, sort),
    queryFn: () => searchCables(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useCable(id: string | undefined) {
  return useQuery({
    queryKey: cableKeys.detail(id ?? ''),
    queryFn: () => getCable(id as string),
    enabled: Boolean(id),
  });
}

export function useCableSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: cableKeys.summaries(status),
    queryFn: () => listCableSummaries(status),
    staleTime: 60_000,
  });
}

export function useCableStatusStats() {
  return useQuery({
    queryKey: cableKeys.stats(),
    queryFn: getCableStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateCable() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CableInput) => createCable(input),
    onSuccess: () => client.invalidateQueries({ queryKey: cableKeys.all }),
  });
}

export function useUpdateCable(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: CableUpdateInput) => updateCable(id, input),
    onSuccess: (updated) => {
      client.setQueryData(cableKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: cableKeys.lists() });
    },
  });
}

export function useChangeCableStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeCableStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: cableKeys.all }),
  });
}

export function useDeleteCable() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteCable(id),
    onSuccess: () => client.invalidateQueries({ queryKey: cableKeys.all }),
  });
}

export function useBulkCreateCables() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: CableInput[]) => bulkCreateCables(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: cableKeys.all }),
  });
}

export function useExportCables() {
  return useMutation({
    mutationFn: (filter: CableFilter) => exportCablesCsv(filter),
  });
}

export function useCablesByAEndPortId(aEndPortId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...cableKeys.all, 'by-a_end_port_id', aEndPortId, page, size],
    queryFn: () => listCableByAEndPortId(aEndPortId as string, page, size),
    enabled: Boolean(aEndPortId),
  });
}

export function useCablesByZEndPortId(zEndPortId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...cableKeys.all, 'by-z_end_port_id', zEndPortId, page, size],
    queryFn: () => listCableByZEndPortId(zEndPortId as string, page, size),
    enabled: Boolean(zEndPortId),
  });
}
