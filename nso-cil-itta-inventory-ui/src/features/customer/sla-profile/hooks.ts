import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSlaProfiles,
  changeSlaProfileStatus,
  createSlaProfile,
  deleteSlaProfile,
  exportSlaProfilesCsv,
  getSlaProfile,
  getSlaProfileStatusStats,
  listSlaProfileSummaries,
  searchSlaProfiles,
  updateSlaProfile,

} from './api';
import type { SlaProfileFilter, SlaProfileInput, SlaProfileUpdateInput } from './types';

export const slaProfileKeys = {
  all: ['slaProfile'] as const,
  lists: () => [...slaProfileKeys.all, 'list'] as const,
  list: (filter: SlaProfileFilter, page: number, size: number, sort?: string) =>
    [...slaProfileKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...slaProfileKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...slaProfileKeys.all, 'summaries', status] as const,
  stats: () => [...slaProfileKeys.all, 'stats'] as const,
};

export function useSlaProfileList(filter: SlaProfileFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: slaProfileKeys.list(filter, page, size, sort),
    queryFn: () => searchSlaProfiles(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSlaProfile(id: string | undefined) {
  return useQuery({
    queryKey: slaProfileKeys.detail(id ?? ''),
    queryFn: () => getSlaProfile(id as string),
    enabled: Boolean(id),
  });
}

export function useSlaProfileSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: slaProfileKeys.summaries(status),
    queryFn: () => listSlaProfileSummaries(status),
    staleTime: 60_000,
  });
}

export function useSlaProfileStatusStats() {
  return useQuery({
    queryKey: slaProfileKeys.stats(),
    queryFn: getSlaProfileStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSlaProfile() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SlaProfileInput) => createSlaProfile(input),
    onSuccess: () => client.invalidateQueries({ queryKey: slaProfileKeys.all }),
  });
}

export function useUpdateSlaProfile(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SlaProfileUpdateInput) => updateSlaProfile(id, input),
    onSuccess: (updated) => {
      client.setQueryData(slaProfileKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: slaProfileKeys.lists() });
    },
  });
}

export function useChangeSlaProfileStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSlaProfileStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: slaProfileKeys.all }),
  });
}

export function useDeleteSlaProfile() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSlaProfile(id),
    onSuccess: () => client.invalidateQueries({ queryKey: slaProfileKeys.all }),
  });
}

export function useBulkCreateSlaProfiles() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SlaProfileInput[]) => bulkCreateSlaProfiles(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: slaProfileKeys.all }),
  });
}

export function useExportSlaProfiles() {
  return useMutation({
    mutationFn: (filter: SlaProfileFilter) => exportSlaProfilesCsv(filter),
  });
}
