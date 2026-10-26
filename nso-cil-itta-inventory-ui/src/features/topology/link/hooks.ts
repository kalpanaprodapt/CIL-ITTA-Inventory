import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateLinks,
  changeLinkStatus,
  createLink,
  deleteLink,
  exportLinksCsv,
  getLink,
  getLinkStatusStats,
  listLinkSummaries,
  searchLinks,
  updateLink,
  listLinkByAInterfaceId,
  listLinkByZInterfaceId,
} from './api';
import type { LinkFilter, LinkInput, LinkUpdateInput } from './types';

export const linkKeys = {
  all: ['link'] as const,
  lists: () => [...linkKeys.all, 'list'] as const,
  list: (filter: LinkFilter, page: number, size: number, sort?: string) =>
    [...linkKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...linkKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...linkKeys.all, 'summaries', status] as const,
  stats: () => [...linkKeys.all, 'stats'] as const,
};

export function useLinkList(filter: LinkFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: linkKeys.list(filter, page, size, sort),
    queryFn: () => searchLinks(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useLink(id: string | undefined) {
  return useQuery({
    queryKey: linkKeys.detail(id ?? ''),
    queryFn: () => getLink(id as string),
    enabled: Boolean(id),
  });
}

export function useLinkSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: linkKeys.summaries(status),
    queryFn: () => listLinkSummaries(status),
    staleTime: 60_000,
  });
}

export function useLinkStatusStats() {
  return useQuery({
    queryKey: linkKeys.stats(),
    queryFn: getLinkStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LinkInput) => createLink(input),
    onSuccess: () => client.invalidateQueries({ queryKey: linkKeys.all }),
  });
}

export function useUpdateLink(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: LinkUpdateInput) => updateLink(id, input),
    onSuccess: (updated) => {
      client.setQueryData(linkKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: linkKeys.lists() });
    },
  });
}

export function useChangeLinkStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeLinkStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: linkKeys.all }),
  });
}

export function useDeleteLink() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteLink(id),
    onSuccess: () => client.invalidateQueries({ queryKey: linkKeys.all }),
  });
}

export function useBulkCreateLinks() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: LinkInput[]) => bulkCreateLinks(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: linkKeys.all }),
  });
}

export function useExportLinks() {
  return useMutation({
    mutationFn: (filter: LinkFilter) => exportLinksCsv(filter),
  });
}

export function useLinksByAInterfaceId(aInterfaceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...linkKeys.all, 'by-a_interface_id', aInterfaceId, page, size],
    queryFn: () => listLinkByAInterfaceId(aInterfaceId as string, page, size),
    enabled: Boolean(aInterfaceId),
  });
}

export function useLinksByZInterfaceId(zInterfaceId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...linkKeys.all, 'by-z_interface_id', zInterfaceId, page, size],
    queryFn: () => listLinkByZInterfaceId(zInterfaceId as string, page, size),
    enabled: Boolean(zInterfaceId),
  });
}
