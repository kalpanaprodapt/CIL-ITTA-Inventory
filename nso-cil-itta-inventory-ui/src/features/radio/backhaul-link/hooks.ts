import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateBackhaulLinks,
  changeBackhaulLinkStatus,
  createBackhaulLink,
  deleteBackhaulLink,
  exportBackhaulLinksCsv,
  getBackhaulLink,
  getBackhaulLinkStatusStats,
  listBackhaulLinkSummaries,
  searchBackhaulLinks,
  updateBackhaulLink,
  listBackhaulLinkByBaseStationId,
} from './api';
import type { BackhaulLinkFilter, BackhaulLinkInput, BackhaulLinkUpdateInput } from './types';

export const backhaulLinkKeys = {
  all: ['backhaulLink'] as const,
  lists: () => [...backhaulLinkKeys.all, 'list'] as const,
  list: (filter: BackhaulLinkFilter, page: number, size: number, sort?: string) =>
    [...backhaulLinkKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...backhaulLinkKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...backhaulLinkKeys.all, 'summaries', status] as const,
  stats: () => [...backhaulLinkKeys.all, 'stats'] as const,
};

export function useBackhaulLinkList(filter: BackhaulLinkFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: backhaulLinkKeys.list(filter, page, size, sort),
    queryFn: () => searchBackhaulLinks(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useBackhaulLink(id: string | undefined) {
  return useQuery({
    queryKey: backhaulLinkKeys.detail(id ?? ''),
    queryFn: () => getBackhaulLink(id as string),
    enabled: Boolean(id),
  });
}

export function useBackhaulLinkSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: backhaulLinkKeys.summaries(status),
    queryFn: () => listBackhaulLinkSummaries(status),
    staleTime: 60_000,
  });
}

export function useBackhaulLinkStatusStats() {
  return useQuery({
    queryKey: backhaulLinkKeys.stats(),
    queryFn: getBackhaulLinkStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateBackhaulLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BackhaulLinkInput) => createBackhaulLink(input),
    onSuccess: () => client.invalidateQueries({ queryKey: backhaulLinkKeys.all }),
  });
}

export function useUpdateBackhaulLink(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: BackhaulLinkUpdateInput) => updateBackhaulLink(id, input),
    onSuccess: (updated) => {
      client.setQueryData(backhaulLinkKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: backhaulLinkKeys.lists() });
    },
  });
}

export function useChangeBackhaulLinkStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeBackhaulLinkStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: backhaulLinkKeys.all }),
  });
}

export function useDeleteBackhaulLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteBackhaulLink(id),
    onSuccess: () => client.invalidateQueries({ queryKey: backhaulLinkKeys.all }),
  });
}

export function useBulkCreateBackhaulLinks() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: BackhaulLinkInput[]) => bulkCreateBackhaulLinks(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: backhaulLinkKeys.all }),
  });
}

export function useExportBackhaulLinks() {
  return useMutation({
    mutationFn: (filter: BackhaulLinkFilter) => exportBackhaulLinksCsv(filter),
  });
}

export function useBackhaulLinksByBaseStationId(baseStationId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...backhaulLinkKeys.all, 'by-base_station_id', baseStationId, page, size],
    queryFn: () => listBackhaulLinkByBaseStationId(baseStationId as string, page, size),
    enabled: Boolean(baseStationId),
  });
}
