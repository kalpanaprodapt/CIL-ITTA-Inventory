import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateOspfAreas,
  changeOspfAreaStatus,
  createOspfArea,
  deleteOspfArea,
  exportOspfAreasCsv,
  getOspfArea,
  getOspfAreaStatusStats,
  listOspfAreaSummaries,
  searchOspfAreas,
  updateOspfArea,

} from './api';
import type { OspfAreaFilter, OspfAreaInput, OspfAreaUpdateInput } from './types';

export const ospfAreaKeys = {
  all: ['ospfArea'] as const,
  lists: () => [...ospfAreaKeys.all, 'list'] as const,
  list: (filter: OspfAreaFilter, page: number, size: number, sort?: string) =>
    [...ospfAreaKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...ospfAreaKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...ospfAreaKeys.all, 'summaries', status] as const,
  stats: () => [...ospfAreaKeys.all, 'stats'] as const,
};

export function useOspfAreaList(filter: OspfAreaFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: ospfAreaKeys.list(filter, page, size, sort),
    queryFn: () => searchOspfAreas(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useOspfArea(id: string | undefined) {
  return useQuery({
    queryKey: ospfAreaKeys.detail(id ?? ''),
    queryFn: () => getOspfArea(id as string),
    enabled: Boolean(id),
  });
}

export function useOspfAreaSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: ospfAreaKeys.summaries(status),
    queryFn: () => listOspfAreaSummaries(status),
    staleTime: 60_000,
  });
}

export function useOspfAreaStatusStats() {
  return useQuery({
    queryKey: ospfAreaKeys.stats(),
    queryFn: getOspfAreaStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateOspfArea() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: OspfAreaInput) => createOspfArea(input),
    onSuccess: () => client.invalidateQueries({ queryKey: ospfAreaKeys.all }),
  });
}

export function useUpdateOspfArea(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: OspfAreaUpdateInput) => updateOspfArea(id, input),
    onSuccess: (updated) => {
      client.setQueryData(ospfAreaKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: ospfAreaKeys.lists() });
    },
  });
}

export function useChangeOspfAreaStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeOspfAreaStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: ospfAreaKeys.all }),
  });
}

export function useDeleteOspfArea() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteOspfArea(id),
    onSuccess: () => client.invalidateQueries({ queryKey: ospfAreaKeys.all }),
  });
}

export function useBulkCreateOspfAreas() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: OspfAreaInput[]) => bulkCreateOspfAreas(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: ospfAreaKeys.all }),
  });
}

export function useExportOspfAreas() {
  return useMutation({
    mutationFn: (filter: OspfAreaFilter) => exportOspfAreasCsv(filter),
  });
}
