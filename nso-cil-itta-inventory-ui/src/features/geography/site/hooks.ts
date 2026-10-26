import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSites,
  changeSiteStatus,
  createSite,
  deleteSite,
  exportSitesCsv,
  getSite,
  getSiteStatusStats,
  listSiteSummaries,
  searchSites,
  updateSite,
  listSiteByCityId,
} from './api';
import type { SiteFilter, SiteInput, SiteUpdateInput } from './types';

export const siteKeys = {
  all: ['site'] as const,
  lists: () => [...siteKeys.all, 'list'] as const,
  list: (filter: SiteFilter, page: number, size: number, sort?: string) =>
    [...siteKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...siteKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...siteKeys.all, 'summaries', status] as const,
  stats: () => [...siteKeys.all, 'stats'] as const,
};

export function useSiteList(filter: SiteFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: siteKeys.list(filter, page, size, sort),
    queryFn: () => searchSites(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSite(id: string | undefined) {
  return useQuery({
    queryKey: siteKeys.detail(id ?? ''),
    queryFn: () => getSite(id as string),
    enabled: Boolean(id),
  });
}

export function useSiteSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: siteKeys.summaries(status),
    queryFn: () => listSiteSummaries(status),
    staleTime: 60_000,
  });
}

export function useSiteStatusStats() {
  return useQuery({
    queryKey: siteKeys.stats(),
    queryFn: getSiteStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSite() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SiteInput) => createSite(input),
    onSuccess: () => client.invalidateQueries({ queryKey: siteKeys.all }),
  });
}

export function useUpdateSite(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SiteUpdateInput) => updateSite(id, input),
    onSuccess: (updated) => {
      client.setQueryData(siteKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: siteKeys.lists() });
    },
  });
}

export function useChangeSiteStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSiteStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: siteKeys.all }),
  });
}

export function useDeleteSite() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSite(id),
    onSuccess: () => client.invalidateQueries({ queryKey: siteKeys.all }),
  });
}

export function useBulkCreateSites() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SiteInput[]) => bulkCreateSites(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: siteKeys.all }),
  });
}

export function useExportSites() {
  return useMutation({
    mutationFn: (filter: SiteFilter) => exportSitesCsv(filter),
  });
}

export function useSitesByCityId(cityId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...siteKeys.all, 'by-city_id', cityId, page, size],
    queryFn: () => listSiteByCityId(cityId as string, page, size),
    enabled: Boolean(cityId),
  });
}
