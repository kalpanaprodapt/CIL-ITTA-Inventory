import { useMutation, useQuery, useQueryClient, keepPreviousData } from '@tanstack/react-query';
import type { LifecycleStatus } from '@/shared/types';
import {
  bulkCreatePatchs,
  changePatchStatus,
  createPatch,
  deletePatch,
  exportPatchsCsv,
  getPatch,
  getPatchStatusStats,
  listPatchSummaries,
  searchPatchs,
  updatePatch,
  listPatchByVersionId,
} from './api';
import type { PatchFilter, PatchInput, PatchUpdateInput } from './types';

export const patchKeys = {
  all: ['patch'] as const,
  lists: () => [...patchKeys.all, 'list'] as const,
  list: (filter: PatchFilter, page: number, size: number, sort?: string) =>
    [...patchKeys.lists(), filter, page, size, sort] as const,
  detail: (id: string) => [...patchKeys.all, 'detail', id] as const,
  summaries: (status?: LifecycleStatus) => [...patchKeys.all, 'summaries', status] as const,
  stats: () => [...patchKeys.all, 'stats'] as const,
};

export function usePatchList(filter: PatchFilter, page: number, size: number, sort?: string) {
  return useQuery({
    queryKey: patchKeys.list(filter, page, size, sort),
    queryFn: () => searchPatchs(filter, page, size, sort),
    placeholderData: keepPreviousData,
  });
}

export function usePatch(id: string | undefined) {
  return useQuery({
    queryKey: patchKeys.detail(id ?? ''),
    queryFn: () => getPatch(id as string),
    enabled: Boolean(id),
  });
}

export function usePatchSummaries(status?: LifecycleStatus) {
  return useQuery({
    queryKey: patchKeys.summaries(status),
    queryFn: () => listPatchSummaries(status),
    staleTime: 60_000,
  });
}

export function usePatchStatusStats() {
  return useQuery({
    queryKey: patchKeys.stats(),
    queryFn: getPatchStatusStats,
    staleTime: 30_000,
  });
}

export function useCreatePatch() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PatchInput) => createPatch(input),
    onSuccess: () => client.invalidateQueries({ queryKey: patchKeys.all }),
  });
}

export function useUpdatePatch(id: string) {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (input: PatchUpdateInput) => updatePatch(id, input),
    onSuccess: (updated) => {
      client.setQueryData(patchKeys.detail(id), updated);
      return client.invalidateQueries({ queryKey: patchKeys.lists() });
    },
  });
}

export function useChangePatchStatus() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: ({ id, status }: { id: string; status: LifecycleStatus }) => changePatchStatus(id, status),
    onSuccess: () => client.invalidateQueries({ queryKey: patchKeys.all }),
  });
}

export function useDeletePatch() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deletePatch(id),
    onSuccess: () => client.invalidateQueries({ queryKey: patchKeys.all }),
  });
}

export function useBulkCreatePatchs() {
  const client = useQueryClient();
  return useMutation({
    mutationFn: (inputs: PatchInput[]) => bulkCreatePatchs(inputs),
    onSuccess: () => client.invalidateQueries({ queryKey: patchKeys.all }),
  });
}

export function useExportPatchs() {
  return useMutation({
    mutationFn: (filter: PatchFilter) => exportPatchsCsv(filter),
  });
}

export function usePatchsByVersionId(versionId: string | undefined, page = 0, size = 20) {
  return useQuery({
    queryKey: [...patchKeys.all, 'by-version_id', versionId, page, size],
    queryFn: () => listPatchByVersionId(versionId as string, page, size),
    enabled: Boolean(versionId),
  });
}
