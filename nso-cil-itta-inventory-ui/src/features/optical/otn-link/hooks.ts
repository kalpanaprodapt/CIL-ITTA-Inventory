import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateOtnLinks,
  changeOtnLinkStatus,
  createOtnLink,
  deleteOtnLink,
  exportOtnLinksCsv,
  getOtnLink,
  getOtnLinkStatusStats,
  listOtnLinkSummaries,
  searchOtnLinks,
  updateOtnLink,
  listOtnLinkByANodeId,
  listOtnLinkByZNodeId,
} from './api';
import type { OtnLinkFilter, OtnLinkInput, OtnLinkUpdateInput } from './types';

export const otnLinkKeys = {
  all: ['otnLink'] as const,
  lists: () => [...otnLinkKeys.all, 'list'] as const,
  list: (filter: OtnLinkFilter, page: number, size: number, sort?: string) =>
    [...otnLinkKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...otnLinkKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...otnLinkKeys.all, 'summaries', status] as const,
  stats: () => [...otnLinkKeys.all, 'stats'] as const,
};

export function useOtnLinkList(filter: OtnLinkFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: otnLinkKeys.list(filter, page, size, sort),
    queryFn: () => searchOtnLinks(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useOtnLink(id: string | undefined) {
  return useQuery({
    queryKey: otnLinkKeys.detail(id ?? ''),
    queryFn: () => getOtnLink(id as string),
    enabled: Boolean(id),
  });
}

export function useOtnLinkSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: otnLinkKeys.summaries(status),
    queryFn: () => listOtnLinkSummaries(status),
    staleTime: 60_000,
  });
}

export function useOtnLinkStatusStats() {
  return useQuery({
    queryKey: otnLinkKeys.stats(),
    queryFn: getOtnLinkStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateOtnLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: OtnLinkInput) => createOtnLink(input),
    onSuccess: () => client.invalidateQueries({ queryKey: otnLinkKeys.all }),
  });
}

export function useUpdateOtnLink(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: OtnLinkUpdateInput) => updateOtnLink(id, input),
    onSuccess: (updated) => {
      client.setQueryData(otnLinkKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: otnLinkKeys.lists() });
    },
  });
}

export function useChangeOtnLinkStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeOtnLinkStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: otnLinkKeys.all }),
  });
}

export function useDeleteOtnLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteOtnLink(id),
    onSuccess: () => client.invalidateQueries({ queryKey: otnLinkKeys.all }),
  });
}

export function useBulkCreateOtnLinks() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: OtnLinkInput[]) => bulkCreateOtnLinks(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: otnLinkKeys.all }),
  });
}

export function useExportOtnLinks() {
  return useMutation({
    mutationFn: (filter: OtnLinkFilter) => exportOtnLinksCsv(filter),
  });
}

export function useOtnLinksByANodeId(aNodeId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...otnLinkKeys.all, 'by-a_node_id', aNodeId, page, size],
    queryFn: () => listOtnLinkByANodeId(aNodeId as string, page, size),
    enabled: Boolean(aNodeId),
  });
}

export function useOtnLinksByZNodeId(zNodeId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...otnLinkKeys.all, 'by-z_node_id', zNodeId, page, size],
    queryFn: () => listOtnLinkByZNodeId(zNodeId as string, page, size),
    enabled: Boolean(zNodeId),
  });
}
