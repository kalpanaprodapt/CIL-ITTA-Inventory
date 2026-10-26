import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreateSoftwareVersions,
  changeSoftwareVersionStatus,
  createSoftwareVersion,
  deleteSoftwareVersion,
  exportSoftwareVersionsCsv,
  getSoftwareVersion,
  getSoftwareVersionStatusStats,
  listSoftwareVersionSummaries,
  searchSoftwareVersions,
  updateSoftwareVersion,
  listSoftwareVersionByImageId,
} from './api';
import type { SoftwareVersionFilter, SoftwareVersionInput, SoftwareVersionUpdateInput } from './types';

export const softwareVersionKeys = {
  all: ['softwareVersion'] as const,
  lists: () => [...softwareVersionKeys.all, 'list'] as const,
  list: (filter: SoftwareVersionFilter, page: number, size: number, sort?: string) =>
    [...softwareVersionKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...softwareVersionKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...softwareVersionKeys.all, 'summaries', status] as const,
  stats: () => [...softwareVersionKeys.all, 'stats'] as const,
};

export function useSoftwareVersionList(filter: SoftwareVersionFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: softwareVersionKeys.list(filter, page, size, sort),
    queryFn: () => searchSoftwareVersions(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function useSoftwareVersion(id: string | undefined) {
  return useQuery({
    queryKey: softwareVersionKeys.detail(id ?? ''),
    queryFn: () => getSoftwareVersion(id as string),
    enabled: Boolean(id),
  });
}

export function useSoftwareVersionSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: softwareVersionKeys.summaries(status),
    queryFn: () => listSoftwareVersionSummaries(status),
    staleTime: 60_000,
  });
}

export function useSoftwareVersionStatusStats() {
  return useQuery({
    queryKey: softwareVersionKeys.stats(),
    queryFn: getSoftwareVersionStatusStats,
    staleTime: 30_000,
  });
}

export function useCreateSoftwareVersion() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SoftwareVersionInput) => createSoftwareVersion(input),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareVersionKeys.all }),
  });
}

export function useUpdateSoftwareVersion(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: SoftwareVersionUpdateInput) => updateSoftwareVersion(id, input),
    onSuccess: (updated) => {
      client.setQueryData(softwareVersionKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: softwareVersionKeys.lists() });
    },
  });
}

export function useChangeSoftwareVersionStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changeSoftwareVersionStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareVersionKeys.all }),
  });
}

export function useDeleteSoftwareVersion() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteSoftwareVersion(id),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareVersionKeys.all }),
  });
}

export function useBulkCreateSoftwareVersions() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: SoftwareVersionInput[]) => bulkCreateSoftwareVersions(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: softwareVersionKeys.all }),
  });
}

export function useExportSoftwareVersions() {
  return useMutation({
    mutationFn: (filter: SoftwareVersionFilter) => exportSoftwareVersionsCsv(filter),
  });
}

export function useSoftwareVersionsByImageId(imageId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...softwareVersionKeys.all, 'by-image_id', imageId, page, size],
    queryFn: () => listSoftwareVersionByImageId(imageId as string, page, size),
    enabled: Boolean(imageId),
  });
}
