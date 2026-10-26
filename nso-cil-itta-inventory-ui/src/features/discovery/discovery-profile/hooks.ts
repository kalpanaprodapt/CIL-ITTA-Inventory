import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateDiscoveryProfiles,
  changeDiscoveryProfileStatus,
  createDiscoveryProfile,
  deleteDiscoveryProfile,
  exportDiscoveryProfilesCsv,
  getDiscoveryProfile,
  getDiscoveryProfileStatusStats,
  listDiscoveryProfileSummaries,
  searchDiscoveryProfiles,
  updateDiscoveryProfile,

} from './api';
import type { DiscoveryProfileFilter, DiscoveryProfileInput, DiscoveryProfileUpdateInput } from './types';

export const discoveryProfileKeys = {
  all: ['discoveryProfile'] as const,
  lists: () => [...discoveryProfileKeys.all, 'list'] as const,
  list: (filter: DiscoveryProfileFilter, page: number, size: number, sort?: string) =>
    [...discoveryProfileKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...discoveryProfileKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...discoveryProfileKeys.all, 'summaries', status] as const,
  stats: () => [...discoveryProfileKeys.all, 'stats'] as const,
};

export function useDiscoveryProfileList(filter: DiscoveryProfileFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: discoveryProfileKeys.list(filter, page, size, sort),
    queryFn: () => searchDiscoveryProfiles(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useDiscoveryProfile(id: string | undefined) {
  return useQuery({
    queryKey: discoveryProfileKeys.detail(id ?? ''),
    queryFn: () => getDiscoveryProfile(id as string),
    enabled: Boolean(id),
  });
}

export function useDiscoveryProfileSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: discoveryProfileKeys.summaries(status),
    queryFn: () => listDiscoveryProfileSummaries(status),
    staleTime: 60_000,
  });
}

export function useDiscoveryProfileStatusStats() {
  return useQuery({
    queryKey: discoveryProfileKeys.stats(),
    queryFn: getDiscoveryProfileStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateDiscoveryProfile() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DiscoveryProfileInput) => createDiscoveryProfile(input),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryProfileKeys.all }),
  });
}

export function useUpdateDiscoveryProfile(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: DiscoveryProfileUpdateInput) => updateDiscoveryProfile(id, input),
    onSuccess: (updated) => {
      client.setQueryData(discoveryProfileKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: discoveryProfileKeys.lists() });
    },
  });
}

export function useChangeDiscoveryProfileStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeDiscoveryProfileStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryProfileKeys.all }),
  });
}

export function useDeleteDiscoveryProfile() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteDiscoveryProfile(id),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryProfileKeys.all }),
  });
}

export function useBulkCreateDiscoveryProfiles() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: DiscoveryProfileInput[]) => bulkCreateDiscoveryProfiles(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: discoveryProfileKeys.all }),
  });
}

export function useExportDiscoveryProfiles() {
  return useMutation({
    mutationFn: (filter: DiscoveryProfileFilter) => exportDiscoveryProfilesCsv(filter),
  });
}
